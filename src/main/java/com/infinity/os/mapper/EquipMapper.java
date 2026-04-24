package com.infinity.os.mapper;

import com.infinity.os.dto.equip.EquipRequestDTO;
import com.infinity.os.dto.equip.EquipResponseDTO;
import com.infinity.os.entity.Client;
import com.infinity.os.entity.Equip;
import org.springframework.stereotype.Component;

@Component
public class EquipMapper {

    public Equip toEntity (EquipRequestDTO equipDTO, Client client){

        return Equip.builder().client(client).serial(equipDTO.getSerial()).descricao(equipDTO.getDescricao()).status(equipDTO.getStatus()).build();
    }

    public EquipResponseDTO toResponseDTO(Equip entity){
        return new EquipResponseDTO(entity.getClient().getId(), entity.getSerial(), entity.getDescricao(), entity.getStatus(), entity.getDataCadastro());
    }

}
