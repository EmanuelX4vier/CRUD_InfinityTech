package com.infinity.os.dto.equip;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infinity.os.types.Status;
import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipResponseDTO {

    private Long clientId;
    private String serial;
    private String descricao;
    private Status status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

}
