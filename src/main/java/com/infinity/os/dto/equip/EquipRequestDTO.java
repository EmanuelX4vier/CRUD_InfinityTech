package com.infinity.os.dto.equip;

import com.infinity.os.types.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipRequestDTO {

    @NotNull(message = "Id do cliente é obrigatório!")
    private Long clientId;

    @NotBlank(message = "Serial do equipamento é obrigatório!")
    private String serial;

    @NotBlank(message = "Descrição do equipamento é obrigatório!")
    private String descricao;

    @NotNull(message = "Status é obrigatório!")
    private Status status;

}
