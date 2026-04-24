package com.infinity.os.service.equip;

import com.infinity.os.dto.clientdto.ClientRequestDTO;
import com.infinity.os.dto.equip.EquipRequestDTO;
import com.infinity.os.dto.equip.EquipResponseDTO;
import com.infinity.os.dto.equip.EquipUpdateDTO;

public interface EquipService {
    EquipResponseDTO createEquip(EquipRequestDTO equipRequestDTO, Long clientId);
    EquipResponseDTO searchEquip (String serial);
    EquipResponseDTO updateEquip(String serial, EquipUpdateDTO dto);
    void deleteEquip(String serial);
}
