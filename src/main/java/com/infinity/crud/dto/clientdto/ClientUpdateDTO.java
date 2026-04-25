package com.infinity.crud.dto.clientdto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateDTO {

    private String nome;
    private String telefone;
    private String endereco;

}
