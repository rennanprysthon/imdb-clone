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
}
