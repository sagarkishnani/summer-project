package com.cursos.summerschool.modelo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idcurso")
    private Integer id;

    @NotBlank
    private String titulo;

    @Size(max=500)
    private String descripcion;

    private String rutaImagen; //la columna en base de datos: ruta_imagen

    @NotNull
    @Min(1)
    @Max(5000)
    private Float precio;
    private LocalDateTime fechaCreacion; //colum en db: fecha_creacion

    @Column( name = "fecha_act")
    private LocalDateTime fechaActualizacion;

    @Transient
    private MultipartFile imagen;

    @PrePersist
    void prePersist()
    {
        fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate()
    {
        fechaActualizacion = LocalDateTime.now();
    }

}
