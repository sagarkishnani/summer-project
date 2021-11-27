package com.cursos.summerschool.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SummerImage {

    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private String imageFileName;
}
