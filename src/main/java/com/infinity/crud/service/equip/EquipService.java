package com.infinity.crud.service.equip;

import com.infinity.crud.dto.equip.EquipRequestDTO;
import com.infinity.crud.dto.equip.EquipResponseDTO;
import com.infinity.crud.dto.equip.EquipUpdateDTO;

public interface EquipService {
    EquipResponseDTO createEquip(EquipRequestDTO equipRequestDTO, Long clientId);
    EquipResponseDTO searchEquip (String serial);
    EquipResponseDTO updateEquip(String serial, EquipUpdateDTO dto);
    void deleteEquip(String serial);
}
