package com.infinity.crud.dto.clientdto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "Nome do cliente é obrigatório!")
    private String nome;

    @NotBlank(message = "Telefone do cliente é obrigatório!")
    private String telefone;

    @NotBlank(message = "Endereço do cliente é obrigatório!")
    private String endereco;

}
