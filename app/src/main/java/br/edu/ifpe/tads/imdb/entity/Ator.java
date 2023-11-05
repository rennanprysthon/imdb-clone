package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "TB_ATOR")
@DiscriminatorValue(value = "A")
@PrimaryKeyJoinColumn(name="ID_USUARIO", referencedColumnName = "ID")
public class Ator extends Usuario implements Serializable {
    @Column(name = "TXT_CIDADE_NATAL")
    private String cidadeNatal;
    @Column(name = "TXT_NACIONALIDADE")
    private String nacionalidade;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO")
    private Date dataNascimento;
    @Column(name = "TXT_NOME_ARTISTICO")
    private String nomeArtistico;
    @ManyToOne
    private Filme filme;

    public String getCidadeNatal() {
        return cidadeNatal;
    }

    public void setCidadeNatal(String cidadeNatal) {
        this.cidadeNatal = cidadeNatal;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    public String getDataNascimentoFormatada() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return simpleDateFormat.format(this.dataNascimento);
    }
}
