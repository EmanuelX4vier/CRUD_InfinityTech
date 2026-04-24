package com.infinity.os.mapper;

import com.infinity.os.dto.clientdto.ClientRequestDTO;
import com.infinity.os.dto.clientdto.ClientResponseDTO;
import com.infinity.os.dto.equip.EquipResponseDTO;
import com.infinity.os.entity.Client;
import lombok.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final EquipMapper equipMapper;

    public Client toEntity (ClientRequestDTO dto){
        return Client.builder().nome(dto.getNome()).telefone(dto.getTelefone()).endereco(dto.getEndereco()).build();
    }

    public ClientResponseDTO toResponseDTO(Client entity){
        List<EquipResponseDTO> equipsDTO = entity.getEquips().stream().map(equipMapper::toResponseDTO).toList();
        return new ClientResponseDTO(entity.getId(), entity.getNome(), entity.getTelefone(), entity.getEndereco(), equipsDTO, entity.getDataCadastro());
    }
}
