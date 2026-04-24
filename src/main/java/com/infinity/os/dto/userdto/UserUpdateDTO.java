package com.infinity.os.dto.userdto;

import com.infinity.os.types.Functions;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private String nome;
    private Functions funcao;

}
