package br.edu.ifpe.tads.imdb.entity;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "TB_USUARIO")
@DiscriminatorColumn(
    name = "DISC_USUARIO",
    discriminatorType = DiscriminatorType.STRING,
    length = 1
)
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "TXT_LOGIN")
    protected String login;
    @Column(name = "TXT_SENHA")
    protected String senha;
    @Column(name = "TXT_EMAIL", unique = true)
    protected String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_CRIACAO")
    private LocalDate dataCriacao;
}
