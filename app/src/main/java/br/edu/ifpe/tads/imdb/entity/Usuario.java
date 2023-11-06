package br.edu.ifpe.tads.imdb.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="TB_USUARIO")
@DiscriminatorValue(value = "U")
@PrimaryKeyJoinColumn(name="ID_CONTA", referencedColumnName = "ID")
public class Usuario extends Conta {
}
