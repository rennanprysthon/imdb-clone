package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "TB_FILME")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TXT_TITULO")
    private String titulo;
    @Column(name = "QT_DURACAO")
    private long duracao;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_LANCAMENTO")
    private Date dataLancamento;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
        name = "TB_FILMES_GENEROS",
        joinColumns = {
            @JoinColumn(name = "ID_FILME")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "ID_GENERO")
        }
    )
    private List<Genero> generos;
    @OneToOne(mappedBy = "filme", cascade = CascadeType.ALL)
    private Premiacao premiacao;
    @OneToMany(
        cascade = CascadeType.ALL,
        mappedBy = "filme",
        orphanRemoval = true
    )
    private Set<Avaliacao> avaliacoes;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_DIRETOR", referencedColumnName = "ID")
    private Diretor diretor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "TB_FILME_ATORES",
        joinColumns = {
            @JoinColumn(name = "ID_FILME")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "ID_ATOR")

        }
    )
    private Set<Ator> atores;
    @Lob
    private byte[] poster;
    @ElementCollection
    @CollectionTable(name = "TB_CREDITO_NOME",
            joinColumns = @JoinColumn(name = "ID_FILME"))
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getDuracao() {
        return duracao;
    }

    public void setDuracao(long duracao) {
        this.duracao = duracao;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public List<Genero> getGenero() {
        return generos;
    }

    public Premiacao getPremiacao() {
        return premiacao;
    }

    public void setPremiacao(Premiacao premiacao) {
        this.premiacao = premiacao;
        this.premiacao.setFilme(this);
    }

    public Set<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Set<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public void removeAvaliacao(Avaliacao avaliacao) {
        if (this.avaliacoes != null) {
            this.avaliacoes.remove(avaliacao);
        }
    }

    public void addAvaliacao(Avaliacao avaliacao) {
        if (this.avaliacoes == null) {
            this.avaliacoes = new HashSet<>();
        }

        this.avaliacoes.add(avaliacao);
    }

    public Diretor getDiretor() {
        return diretor;
    }

    public void setDiretor(Diretor diretor) {
        this.diretor = diretor;
    }

    public Set<Ator> getAtores() {
        return atores;
    }

    public void addAtor(Ator ator) {
        if (this.atores == null)
            this.atores = new HashSet<>();

        this.atores.add(ator);
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public Collection<String> getNomesCreditos() {
        return nomesCreditos;
    }

    public void setNomesCreditos(Collection<String> nomesCreditos) {
        this.nomesCreditos = nomesCreditos;
    }

    public void addGenero(Genero genero) {
        if (this.generos == null || this.generos.isEmpty()) {
            this.generos = new ArrayList<>();
        }

        this.generos.add(genero);
    }
}
