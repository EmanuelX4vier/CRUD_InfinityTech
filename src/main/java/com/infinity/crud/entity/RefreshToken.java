package com.infinity.crud.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Diz ao banco que esse ID deve ser gerado automaticamente.
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Instant  expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    private void prePersist(){
        this.createdAt = Instant.now();
    }
}
