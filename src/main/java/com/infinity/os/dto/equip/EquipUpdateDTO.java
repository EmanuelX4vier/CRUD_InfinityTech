package com.infinity.os.dto.equip;

import com.infinity.os.types.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipUpdateDTO {

    private Long clientId;
    private String serial;
    private String descricao;
    private Status status;

}
