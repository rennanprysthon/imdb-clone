package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_FILME")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_TITULO")
    private String titulo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "NM_DURACAO")
    private Timestamp duracao;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_LANCAMENTO")
    private LocalDate dataLancamento;
    @ManyToMany
    private List<Genero> genero;
    @ManyToOne
    private Diretor diretor;
    @Lob
    private byte[] poster;
}
