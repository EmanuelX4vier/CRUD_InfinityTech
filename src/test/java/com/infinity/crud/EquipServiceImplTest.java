package com.infinity.crud;

import com.infinity.crud.dto.equipdto.EquipRequestDTO;
import com.infinity.crud.dto.equipdto.EquipResponseDTO;
import com.infinity.crud.dto.equipdto.EquipUpdateDTO;
import com.infinity.crud.entity.Client;
import com.infinity.crud.entity.Equip;
import com.infinity.crud.enums.Status;
import com.infinity.crud.exception.ClientNotFoundException;
import com.infinity.crud.exception.EquipNotFoundException;
import com.infinity.crud.mapper.EquipMapper;
import com.infinity.crud.repository.ClientRepository;
import com.infinity.crud.repository.EquipRepository;
import com.infinity.crud.service.equip.EquipServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private EquipRepository equipRepository;

    @Mock
    private EquipMapper equipMapper;

    @InjectMocks
    private EquipServiceImpl equipService;

    // -------------------------------------------------------------------------
    // createEquip
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("createEquip deve salvar e retornar o DTO do equipamento criado")
    void createEquip_deveSalvarERetornarDTO() {
        Client client = buildClient(1L);
        EquipRequestDTO request = new EquipRequestDTO(1L, "SN-001", "Notebook Dell", Status.SEM_SERVICO);
        Equip entity = buildEquip("SN-001", client);
        EquipResponseDTO response = buildResponseDTO("SN-001", 1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(equipMapper.toEntity(request, client)).thenReturn(entity);
        when(equipRepository.save(entity)).thenReturn(entity);
        when(equipMapper.toResponseDTO(entity)).thenReturn(response);

        EquipResponseDTO result = equipService.createEquip(request, 1L);

        assertNotNull(result);
        assertEquals("SN-001", result.getSerial());
        verify(equipRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("createEquip deve lançar ClientNotFoundException quando cliente não existe")
    void createEquip_deveLancarExcecaoQuandoClienteNaoExiste() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        EquipRequestDTO request = new EquipRequestDTO(99L, "SN-001", "Notebook", Status.SEM_SERVICO);

        assertThrows(ClientNotFoundException.class,
                () -> equipService.createEquip(request, 99L));

        verify(equipRepository, never()).save(any());
    }

    // -------------------------------------------------------------------------
    // searchEquip
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("searchEquip deve retornar o DTO quando equipamento existe")
    void searchEquip_deveRetornarDTOQuandoExiste() {
        Client client = buildClient(1L);
        Equip entity = buildEquip("SN-001", client);
        EquipResponseDTO response = buildResponseDTO("SN-001", 1L);

        when(equipRepository.findById("SN-001")).thenReturn(Optional.of(entity));
        when(equipMapper.toResponseDTO(entity)).thenReturn(response);

        EquipResponseDTO result = equipService.searchEquip("SN-001");

        assertNotNull(result);
        assertEquals("SN-001", result.getSerial());
    }

    @Test
    @DisplayName("searchEquip deve lançar EquipNotFoundException quando equipamento não existe")
    void searchEquip_deveLancarExcecaoQuandoNaoExiste() {
        when(equipRepository.findById("INEXISTENTE")).thenReturn(Optional.empty());

        assertThrows(EquipNotFoundException.class,
                () -> equipService.searchEquip("INEXISTENTE"));
    }

    // -------------------------------------------------------------------------
    // updateEquip
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("updateEquip deve atualizar descricao e status")
    void updateEquip_deveAtualizarDescricaoEStatus() {
        Client client = buildClient(1L);
        Equip entity = buildEquip("SN-001", client);
        EquipUpdateDTO dto = new EquipUpdateDTO(null, null, "Notebook Atualizado", Status.CONCLUIDO);

        when(equipRepository.findById("SN-001")).thenReturn(Optional.of(entity));
        when(equipRepository.save(any())).thenReturn(entity);
        when(equipMapper.toResponseDTO(any())).thenReturn(buildResponseDTO("SN-001", 1L));

        equipService.updateEquip("SN-001", dto);

        assertEquals("Notebook Atualizado", entity.getDescricao());
        assertEquals(Status.CONCLUIDO, entity.getStatus());
    }

    @Test
    @DisplayName("updateEquip deve lançar EquipNotFoundException quando equipamento não existe")
    void updateEquip_deveLancarExcecaoQuandoNaoExiste() {
        when(equipRepository.findById("INEXISTENTE")).thenReturn(Optional.empty());

        assertThrows(EquipNotFoundException.class,
                () -> equipService.updateEquip("INEXISTENTE", new EquipUpdateDTO()));
    }

    // -------------------------------------------------------------------------
    // deleteEquip
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("deleteEquip deve deletar quando equipamento existe")
    void deleteEquip_deveDeletarQuandoExiste() {
        when(equipRepository.existsById("SN-001")).thenReturn(true);

        equipService.deleteEquip("SN-001");

        verify(equipRepository, times(1)).deleteById("SN-001");
    }

    @Test
    @DisplayName("deleteEquip deve lançar EquipNotFoundException quando equipamento não existe")
    void deleteEquip_deveLancarExcecaoQuandoNaoExiste() {
        when(equipRepository.existsById("INEXISTENTE")).thenReturn(false);

        assertThrows(EquipNotFoundException.class,
                () -> equipService.deleteEquip("INEXISTENTE"));
        verify(equipRepository, never()).deleteById(any());
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private Client buildClient(Long id) {
        return Client.builder()
                .id(id)
                .nome("Empresa X")
                .telefone("83999999999")
                .endereco("Rua A, 123")
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    private Equip buildEquip(String serial, Client client) {
        return Equip.builder()
                .serial(serial)
                .descricao("Notebook Dell")
                .status(Status.SEM_SERVICO)
                .client(client)
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    private EquipResponseDTO buildResponseDTO(String serial, Long clientId) {
        return new EquipResponseDTO(clientId, serial, "Notebook Dell", Status.SEM_SERVICO, LocalDateTime.now());
    }
}
