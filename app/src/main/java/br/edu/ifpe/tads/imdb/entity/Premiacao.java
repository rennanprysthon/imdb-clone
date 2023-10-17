package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_PREMIACAO")
public class Premiacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_PREMIACAO")
    private TipoPremiacao tipoPremiacao;
    @OneToOne
    private Filme filme;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Premiacao premiacao = (Premiacao) o;

        return id.equals(premiacao.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Column(name = "TXT_CATEGORIA")
    private String categoria;
}
