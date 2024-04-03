package com.mokoko.entities;
import jakarta.persistence.*;
import java.time.LocalDate;

/*Ici on ha l'entité cotéJava qui correspond à notre table player lato  database*/
@Entity
@Table(name = "player", schema = "public")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "cognome", length = 50, nullable = false)
    private String cognome;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "punti", nullable = false)
    private Integer punti;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    //Constructeur par defaut
    public PlayerEntity(){
    }

    //Constructeur parametrico
    public PlayerEntity(Long id, String nome, String cognome, LocalDate birthDate, Integer punti, Integer rank) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.birthDate = birthDate;
        this.punti = punti;
        this.rank = rank;
    }

    //Constructeur parametrico
    public PlayerEntity(String nome, String cognome, LocalDate birthDate, Integer punti, Integer rank) {
        this.nome = nome;
        this.cognome = cognome;
        this.birthDate = birthDate;
        this.punti = punti;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getPunti() {
        return punti;
    }
    public void setPunti(Integer punti) {
        this.punti = punti;
    }

    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
}