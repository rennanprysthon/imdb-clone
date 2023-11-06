package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "TB_AVALIACAO")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_FILME", referencedColumnName = "ID")
    private Filme filme;
    @Lob
    @Column(name = "TXT_RESENHA")
    private String resenha;
    @Column(name = "NM_NOTA")
    private Double nota;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_AVALIACAO")
    private Date dataAvaliacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Avaliacao avaliacao = (Avaliacao) o;

        return id.equals(avaliacao.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

