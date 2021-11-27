package com.cursos.summerschool.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {

    SUMMERSCHOOL_IMAGE("images-summer");

    private final String bucketName;
}
