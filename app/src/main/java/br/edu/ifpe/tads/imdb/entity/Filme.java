package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TB_FILME")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_TITULO")
    private String titulo;
    @Column(name = "QT_DURACAO")
    private Long duracao;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_LANCAMENTO")
    private Date dataLancamento;
    @ManyToMany(cascade = CascadeType.DETACH)
    private List<Genero> genero;
    @OneToOne
    private Premiacao premiacao;
    @OneToMany(
        mappedBy = "filme",
        orphanRemoval = true
    )
    private Set<Avaliacao> avaliacoes;
    @ManyToOne
    private Diretor diretor;
    @Lob
    private byte[] poster;
    @ElementCollection
    @CollectionTable(name = "TB_CREDITO_NOME",
            joinColumns = @JoinColumn(name = "ID_USUARIO"))
    @Column(name = "TXT_NOME_CREDITO")
    private Collection<String> nomesCreditos;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filme filme = (Filme) o;

        return id.equals(filme.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
