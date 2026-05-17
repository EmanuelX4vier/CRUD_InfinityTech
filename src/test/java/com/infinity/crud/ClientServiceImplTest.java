package com.infinity.crud;
import com.infinity.crud.dto.clientdto.ClientRequestDTO;
import com.infinity.crud.dto.clientdto.ClientResponseDTO;
import com.infinity.crud.dto.clientdto.ClientUpdateDTO;
import com.infinity.crud.entity.Client;
import com.infinity.crud.exception.ClientNotFoundException;
import com.infinity.crud.mapper.ClientMapper;
import com.infinity.crud.repository.ClientRepository;
import com.infinity.crud.service.client.ClientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    // -------------------------------------------------------------------------
    // createClient
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("createClient deve salvar e retornar o DTO do cliente criado")
    void createClient_deveSalvarERetornarDTO() {
        ClientRequestDTO request = new ClientRequestDTO("Empresa X", "83999999999", "Rua A, 123");
        Client entity = buildClient(1L, "Empresa X");
        ClientResponseDTO response = buildResponseDTO(1L, "Empresa X");

        when(clientMapper.toEntity(request)).thenReturn(entity);
        when(clientRepository.save(entity)).thenReturn(entity);
        when(clientMapper.toResponseDTO(entity)).thenReturn(response);

        ClientResponseDTO result = clientService.createClient(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Empresa X", result.getNome());
        verify(clientRepository, times(1)).save(entity);
    }

    // -------------------------------------------------------------------------
    // searchClient
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("searchClient deve retornar o DTO quando cliente existe")
    void searchClient_deveRetornarDTOQuandoExiste() {
        Client entity = buildClient(1L, "Empresa X");
        ClientResponseDTO response = buildResponseDTO(1L, "Empresa X");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(clientMapper.toResponseDTO(entity)).thenReturn(response);

        ClientResponseDTO result = clientService.searchClient(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("searchClient deve lançar ClientNotFoundException quando cliente não existe")
    void searchClient_deveLancarExcecaoQuandoNaoExiste() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.searchClient(99L));
    }

    // -------------------------------------------------------------------------
    // updateClient
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("updateClient deve atualizar apenas os campos não nulos")
    void updateClient_deveAtualizarApenasFieldsNaoNulos() {
        Client entity = buildClient(1L, "Empresa X");
        entity.setTelefone("83988888888");
        entity.setEndereco("Rua Antiga");

        // Só envia o nome — telefone e endereco ficam null no DTO
        ClientUpdateDTO dto = new ClientUpdateDTO("Empresa Y", null, null);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(clientRepository.save(any())).thenReturn(entity);
        when(clientMapper.toResponseDTO(any())).thenReturn(buildResponseDTO(1L, "Empresa Y"));

        clientService.updateClient(1L, dto);

        // Nome foi alterado
        assertEquals("Empresa Y", entity.getNome());
        // Telefone e endereço não foram tocados
        assertEquals("83988888888", entity.getTelefone());
        assertEquals("Rua Antiga", entity.getEndereco());
    }

    @Test
    @DisplayName("updateClient deve lançar ClientNotFoundException quando cliente não existe")
    void updateClient_deveLancarExcecaoQuandoNaoExiste() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientService.updateClient(99L, new ClientUpdateDTO()));
    }

    // -------------------------------------------------------------------------
    // deleteClient
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("deleteClient deve deletar quando cliente existe")
    void deleteClient_deveDeletarQuandoExiste() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        clientService.deleteClient(1L);

        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteClient deve lançar ClientNotFoundException quando cliente não existe")
    void deleteClient_deveLancarExcecaoQuandoNaoExiste() {
        when(clientRepository.existsById(99L)).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> clientService.deleteClient(99L));
        verify(clientRepository, never()).deleteById(any());
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private Client buildClient(Long id, String nome) {
        return Client.builder()
                .id(id)
                .nome(nome)
                .telefone("83999999999")
                .endereco("Rua A, 123")
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    private ClientResponseDTO buildResponseDTO(Long id, String nome) {
        return new ClientResponseDTO(id, nome, "83999999999", "Rua A, 123", List.of(), LocalDateTime.now());
    }
}
