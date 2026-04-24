package com.infinity.os.dto.userdto;

import com.infinity.os.types.Functions;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "O nome do funcionário é obrigatório!")
    private String nome;

    @NotNull(message = "A função do funcionário é obrigatória!")
    private Functions funcao;

}
