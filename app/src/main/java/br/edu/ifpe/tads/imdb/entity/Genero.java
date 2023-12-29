package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "TB_GENERO")
@NamedQueries(
    {
        @NamedQuery(
            name = "Genero.FilmesPorNome",
            query = "SELECT g.filmes FROM Genero g WHERE g.nome LIKE :nome ORDER BY g.nome"
        )
    }
)
public class Genero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_NOME")
    @NotBlank
    private String nome;
    @ManyToMany(mappedBy = "generos")
    private Set<Filme> filmes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genero genero = (Genero) o;

        return id.equals(genero.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFilmes(Set<Filme> filmes) {
        this.filmes = filmes;
    }

    public Set<Filme> getFilmes() {
        return filmes;
    }
}
