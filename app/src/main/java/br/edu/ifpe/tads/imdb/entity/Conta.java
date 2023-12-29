package br.edu.ifpe.tads.imdb.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TB_CONTA")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
    name = "DISC_CONTA",
    discriminatorType = DiscriminatorType.STRING,
    length = 1
)
public abstract class Conta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "TXT_LOGIN")
    @NotNull
    protected String login;
    @Column(name = "TXT_SENHA")
    @NotNull
    @Size(min = 10)
    protected String senha;
    @Column(name = "TXT_EMAIL", unique = true)
    @Email
    protected String email;
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_CRIACAO")
    @Future
    private Date dataCriacao;

    @Column(name = "TXT_NOME")
    @Pattern(regexp = "\\p{Upper}{1}\\p{Lower}+", message = "Informe um nome valido")
    private String nome;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conta conta = (Conta) o;

        return Objects.equals(id, conta.id);
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCriacaoFormatada() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return simpleDateFormat.format(this.dataCriacao);
    }
}
