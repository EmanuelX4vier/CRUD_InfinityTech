package com.infinity.crud.controller;

import com.infinity.crud.dto.equip.EquipRequestDTO;
import com.infinity.crud.dto.equip.EquipResponseDTO;
import com.infinity.crud.dto.equip.EquipUpdateDTO;
import com.infinity.crud.service.equip.EquipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equips")
@RequiredArgsConstructor
public class EquipController {

    private final EquipService equipService;

    @PostMapping
    public EquipResponseDTO createEquip(@RequestBody @Valid EquipRequestDTO dto,
                                        @RequestBody Long clientId) {
        return equipService.createEquip(dto, clientId);
    }

    @GetMapping("/{serial}")
    public EquipResponseDTO searchEquip(@PathVariable String serial) {
        return equipService.searchEquip(serial);
    }

    @PatchMapping("/{serial}")
    public EquipResponseDTO updateEquip(@PathVariable String serial,
                                        @RequestBody @Valid EquipUpdateDTO dto) {
        return equipService.updateEquip(serial, dto);
    }

    @DeleteMapping("/{serial}")
    public ResponseEntity<Void> deleteEquip(@PathVariable String serial) {
        equipService.deleteEquip(serial);
        return ResponseEntity.noContent().build();
    }
}