package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "TB_ATOR")
@DiscriminatorValue(value = "A")
@PrimaryKeyJoinColumn(name="ID_CONTA", referencedColumnName = "ID")
@NamedQueries(
    {
        @NamedQuery(
            name = "Ator.SemFilmes",
            query = "SELECT count(a) FROM Ator a WHERE a.filmes IS EMPTY"
        )
    }
)
public class Ator extends Conta implements Serializable {
    @Column(name = "TXT_CIDADE_NATAL")
    private String cidadeNatal;
    @Column(name = "TXT_NACIONALIDADE")
    private String nacionalidade;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO")
    private Date dataNascimento;
    @Column(name = "TXT_NOME_ARTISTICO")
    private String nomeArtistico;
    @ManyToMany(mappedBy = "atores")
    private Set<Filme> filmes;

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

    public Set<Filme> getFilmes() {
        return filmes;
    }

    public void addFilme(Filme filme) {
        this.filmes.add(filme);
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    public String getDataNascimentoFormatada() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return simpleDateFormat.format(this.dataNascimento);
    }
}
