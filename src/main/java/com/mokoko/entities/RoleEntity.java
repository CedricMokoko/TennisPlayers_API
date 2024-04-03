package com.mokoko.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role", schema = "public")
public class RoleEntity {

    @Id
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    public RoleEntity() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
