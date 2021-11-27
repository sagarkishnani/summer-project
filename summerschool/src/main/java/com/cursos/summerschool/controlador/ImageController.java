package com.cursos.summerschool.controlador;

import com.cursos.summerschool.modelo.SummerImage;
import com.cursos.summerschool.service.ImageStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageStore imageStore;

    @GetMapping("/fotos")
    public String init() {

        return "images";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        String message;

        if(imageStore.upload(file)) {
            message="Se subió correctamente";
        }else {
            message= "ocurrió un error al subir el archivo";
        }
        log.info(message);

        redirectAttributes.addFlashAttribute("message", message+" " + file.getOriginalFilename());
        return "redirect:/";
    }
}
