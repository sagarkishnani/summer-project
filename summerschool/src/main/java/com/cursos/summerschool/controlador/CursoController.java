package com.cursos.summerschool.controlador;

import com.cursos.summerschool.modelo.Curso;
import com.cursos.summerschool.repositorio.CursoRepository;
import com.cursos.summerschool.service.FileSystemStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    //http:localhost:8080/cursos    =>  index.html - mostrar el listado de cursos   =>  GET
    @GetMapping("")
    String index(Model model)
    {
        List<Curso> cursos = cursoRepository.findAll(); //Obtenemos el listado de los cursos desde la bd y se guarda en la variable cursos
        model.addAttribute("cursos", cursos); //agregar un atributo al model que se enviara a la VISTA
        return "index"; //retorna a la VISTA index.html
    }

    //http:localhost:8080/cursos/nuevo  => nuevo.html   => MOSTRAR EL FORMULARIO PARA EL REGISTRO => GET
    @GetMapping("/nuevo")
    String nuevo(Model model)
    {
        model.addAttribute("curso", new Curso());
        return "nuevo";
    }

    //http:localhost:8080/cursos/nuevo  => nuevo.html   => GUARDE EN BD EL CURSO => POST
    @PostMapping("/nuevo")
    String crear(Model model, @Validated Curso curso, BindingResult bindingResult, RedirectAttributes ra)
    {
        if(curso.getImagen().isEmpty())
        {
            bindingResult.rejectValue("imagen", "MultipartNotEmpty");
        }

        if(bindingResult.hasErrors())
        {
            model.addAttribute("curso", curso);
            return "nuevo";
        }

        String RutaImagen = fileSystemStorageService.store(curso.getImagen());
        curso.setRutaImagen(RutaImagen);

        cursoRepository.save(curso);
        ra.addFlashAttribute("msgExito", "Registro satisfactorio..");
        return "redirect:/cursos"; //redirige al listado de cursos
    }

    //editar
    //http:localhost:8080/cursos/editar  => editar.html   => VISUALIZAR LOS DATOS DEL CURSO => GET
    @GetMapping("/{id}/editar") //buscar el curso y lo envie como parametro a la vista para que se muestre en pantalla
    String editar(@PathVariable Integer id, Model model)
    {
        Curso curso = cursoRepository.getById(id);
        model.addAttribute("curso", curso);
        return "editar";
    }

    //editar
    //http:localhost:8080/cursos/editar  => editar.html   => ACTAULIZAR LOS DATOS DEL CURSO EN DB => POST
    @PostMapping("/{id}/editar") //buscar el curso y lo envie como parametro a la vista para que se muestre en pantalla
    String actualizar(@PathVariable Integer id, Model model, Curso curso, RedirectAttributes ra)
    {
        Curso cursoFromDB = cursoRepository.getById(id);
        cursoFromDB.setTitulo(curso.getTitulo());
        cursoFromDB.setPrecio(curso.getPrecio());
        cursoFromDB.setDescripcion(curso.getDescripcion());

        if (! curso.getImagen().isEmpty())
        {
            String rutaImagen = fileSystemStorageService.store(curso.getImagen());
            cursoFromDB.setRutaImagen(rutaImagen);
        }
        cursoRepository.save(cursoFromDB);
        ra.addFlashAttribute("msgExito", "Curso actualizado..");

        return "redirect:/cursos";
    }


    //eliminar
    //http:localhost:8080/cursos/{id}/eliminar  ELIMINAR CURSO DE LA BD => POST
    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes ra)
    {
        Curso curso = cursoRepository.getById(id);
        cursoRepository.delete(curso);
        ra.addFlashAttribute("msgExito", "Curso eliminado..");
        return "redirect:/cursos";
    }

}