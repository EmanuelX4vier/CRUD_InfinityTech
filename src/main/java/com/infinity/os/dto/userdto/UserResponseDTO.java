package com.infinity.os.dto.userdto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infinity.os.types.Functions;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDTO {

    private Long id;
    private String nome;
    private Functions funcao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCadastro;

}
