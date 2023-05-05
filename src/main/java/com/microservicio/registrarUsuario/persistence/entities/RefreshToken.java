package com.microservicio.registrarUsuario.persistence.entities;


import com.microservicio.registrarUsuario.persistence.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class RefreshToken {
    @Id
    private Long id;

    @MapsId //sincroniza id RefreshToken con el valor del id de User. Es decir, el id de User vendrá al id de RefresToken
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NaturalId //no debe repetirse el token
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant  expiryDate;


    private LocalDateTime createdAt;
    @PrePersist
    private void setCreationDate(){
        this.createdAt = LocalDateTime.now();

    }
}
