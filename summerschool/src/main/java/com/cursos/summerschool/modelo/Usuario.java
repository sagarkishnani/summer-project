package com.cursos.summerschool.modelo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;
    private String nombres;
    private String apellidos;

    @Column(name = "nom_completo")
    private String nombreCompleto;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;
    public enum Rol{
        ADMIN,
        ESTUDIANTE
    }

    @PrePersist
    @PreUpdate
    void asingarNombreCompleto()
    {
        nombreCompleto = nombres + " " + apellidos;
    }
}
