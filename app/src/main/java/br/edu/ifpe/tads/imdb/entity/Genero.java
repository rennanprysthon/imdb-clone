package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_GENERO")
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_NOME")
    private String nome;
}
