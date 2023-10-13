package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="TB_DIRETOR")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Diretor extends Usuario {
    @OneToMany
    private Set<Filme> filmes;
    @Column(name = "TXT_NOME")
    private String nome;
}
