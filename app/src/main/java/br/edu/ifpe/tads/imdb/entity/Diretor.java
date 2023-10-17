package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="TB_DIRETOR")
@DiscriminatorValue(value = "D")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Diretor extends Usuario implements Serializable {
    @OneToMany
    private Set<Filme> filmes;
    @Column(name = "TXT_NOME")
    private String nome;
    public Set<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(Set<Filme> filmes) {
        this.filmes = filmes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
