package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TB_PREMIACAO")
public class Premiacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_PREMIACAO")
    @NotNull
    private TipoPremiacao tipoPremiacao;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "ID_FILME", referencedColumnName = "ID")
    private Filme filme;

    @Column(name = "TXT_CATEGORIA")
    @NotBlank
    private String categoria;

    public TipoPremiacao getTipoPremiacao() {
        return tipoPremiacao;
    }

    public void setTipoPremiacao(TipoPremiacao tipoPremiacao) {
        this.tipoPremiacao = tipoPremiacao;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

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

}
