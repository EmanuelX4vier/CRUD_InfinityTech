package com.infinity.crud.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity //Diz ao banco que isto é uma tabela.
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id //Define que isto é a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Diz ao banco que esse ID deve ser gerado automaticamente.
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "client")
    private List<Equip> equips;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @PrePersist //Ele roda antes do hibernate no banco.
    private void registrarCadastro(){
        this.dataCadastro = LocalDateTime.now();
    }

}
