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

    public Set<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(Set<Filme> filmes) {
        this.filmes = filmes;
    }
}
