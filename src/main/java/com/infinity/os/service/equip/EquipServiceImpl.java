package com.infinity.os.service.equip;

import com.infinity.os.dto.equip.EquipRequestDTO;
import com.infinity.os.dto.equip.EquipResponseDTO;
import com.infinity.os.dto.equip.EquipUpdateDTO;
import com.infinity.os.entity.Client;
import com.infinity.os.entity.Equip;
import com.infinity.os.exception.ClientNotFoundException;
import com.infinity.os.exception.EquipNotFoundException;
import com.infinity.os.mapper.EquipMapper;
import com.infinity.os.repository.ClientRepository;
import com.infinity.os.repository.EquipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipServiceImpl implements EquipService {

    private final ClientRepository clientRepository;
    private final EquipRepository equipRepository;
    private final EquipMapper equipMapper;

    @Override
    public EquipResponseDTO createEquip(EquipRequestDTO equipDTO, Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(ClientNotFoundException::new);

        Equip equip = equipMapper.toEntity(equipDTO, client);

        Equip savedEquip = equipRepository.save(equip);

        return equipMapper.toResponseDTO(savedEquip);
    }

    @Override
    public EquipResponseDTO searchEquip(String serial) {

        Equip equip = equipRepository.findById(serial)
                .orElseThrow(EquipNotFoundException::new);

        return equipMapper.toResponseDTO(equip);
    }

    @Override
    public EquipResponseDTO updateEquip(String serial, EquipUpdateDTO dto) {

        Equip equip = equipRepository.findById(serial)
                .orElseThrow(EquipNotFoundException::new);

        equip.setDescricao(dto.getDescricao());
        equip.setStatus(dto.getStatus());

        Equip updatedEquip = equipRepository.save(equip);

        return equipMapper.toResponseDTO(updatedEquip);
    }

    @Override
    public void deleteEquip(String serial) {

        if (!equipRepository.existsById(serial)) {
            throw new EquipNotFoundException();
        }

        equipRepository.deleteById(serial);
    }
}