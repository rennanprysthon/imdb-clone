package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_AVALIACAO")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Filme filme;
    @Lob
    @Column(name = "TXT_RESENHA")
    private String resenha;
    @Column(name = "NM_NOTA")
    private Double nota;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_AVALIACAO")
    private LocalDate dataAvaliacao;
}

