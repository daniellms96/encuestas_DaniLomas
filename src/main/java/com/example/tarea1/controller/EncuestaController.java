package com.example.tarea1.controller;

import com.example.tarea1.model.Encuesta;
import com.example.tarea1.repository.EncuestaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EncuestaController {

    private final EncuestaRepository encuestaRepository;

    public EncuestaController(EncuestaRepository encuestaRepository) {
        this.encuestaRepository = encuestaRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/encuestas/listar"; // Redirigir a listar con la nueva ruta
    }

    @GetMapping("/encuestas/listar")
    public String listarEncuestas(@RequestParam(value = "satisfaccion", required = false) String satisfaccion, Model model) {
        List<Encuesta> encuestas;

        // Si se selecciona un filtro de satisfacción, filtrar las encuestas
        if (satisfaccion != null && !satisfaccion.isEmpty()) {
            encuestas = encuestaRepository.findByNivelSatisfaccion(satisfaccion); // Asumiendo que tienes un método en tu repositorio para filtrar por satisfacción
        } else {
            encuestas = encuestaRepository.findAll();
        }

        if (encuestas == null) {
            encuestas = new ArrayList<>();  // Evitar pasar null
        }

        // Calcular el promedio de edad
        double promedioEdad = calcularPromedioEdad(encuestas);
        model.addAttribute("promedioEdad", promedioEdad);

        // Calcular los porcentajes de satisfacción
        Map<String, Double> porcentajeSatisfechos = calcularPorcentajesSatisfaccion(encuestas);
        model.addAttribute("porcentajeSatisfechos", porcentajeSatisfechos);

        model.addAttribute("encuestas", encuestas);
        model.addAttribute("filtroSatisfaccion", satisfaccion);  // Para mantener el valor seleccionado en el filtro
        return "listarEncuestas";
    }


    private double calcularPromedioEdad(List<Encuesta> encuestas) {
        return encuestas.stream()
                .mapToInt(Encuesta::getEdad)
                .average()
                .orElse(0.0);
    }

    private Map<String, Double> calcularPorcentajesSatisfaccion(List<Encuesta> encuestas) {
        Map<String, Long> conteoSatisfaccion = encuestas.stream()
                .collect(Collectors.groupingBy(Encuesta::getNivelSatisfaccion, Collectors.counting()));

        long total = encuestas.size();
        Map<String, Double> porcentajes = new HashMap<>();

        for (Map.Entry<String, Long> entry : conteoSatisfaccion.entrySet()) {
            porcentajes.put(entry.getKey(), (entry.getValue() * 100.0) / total);
        }
        return porcentajes;
    }

    @GetMapping("/encuestas/crear")
    public String mostrarFormularioEncuesta(Model model) {
        model.addAttribute("encuesta", new Encuesta());
        return "formularioEncuesta";
    }

    @PostMapping("/encuestas/crear")
    public String procesarFormularioEncuesta(@Valid @ModelAttribute Encuesta encuesta, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "formularioEncuesta";
        }
        encuestaRepository.save(encuesta);
        redirectAttributes.addFlashAttribute("mensaje", "Encuesta guardada con éxito.");
        return "redirect:/encuestas/listar";
    }

    @GetMapping("/encuestas/ver/{id}")
    public String verEncuesta(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Encuesta> encuestaOpt = encuestaRepository.findById(id);
        if (encuestaOpt.isPresent()) {
            model.addAttribute("encuesta", encuestaOpt.get());
            return "verEncuesta";
        }
        redirectAttributes.addFlashAttribute("error", "Encuesta no encontrada.");
        return "redirect:/encuestas/listar";
    }

    @GetMapping("/encuestas/editar/{id}")
    public String editarEncuesta(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Encuesta> encuestaOpt = encuestaRepository.findById(id);
        if (encuestaOpt.isPresent()) {
            Encuesta encuesta = encuestaOpt.get();

            // Verificar que la fecha no sea nula y pasarla como un String en formato "yyyy-MM-dd"
            if (encuesta.getFechaInicioEstancia() != null) {
                model.addAttribute("fechaInicioEstancia", encuesta.getFechaInicioEstancia().toString()); // Formato adecuado para el input date
            }

            // Pasar el objeto encuesta al modelo para que Thymeleaf lo utilice en el formulario
            model.addAttribute("encuesta", encuesta);

            return "formularioEncuestaEditar";
        }

        redirectAttributes.addFlashAttribute("error", "Encuesta no encontrada.");
        return "redirect:/encuestas/listar";
    }


    @PostMapping("/encuestas/editar/{id}")
    public String procesarEdicionEncuesta(@PathVariable Long id, @Valid @ModelAttribute Encuesta encuesta, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "formularioEncuestaEditar";
        }
        Optional<Encuesta> encuestaOpt = encuestaRepository.findById(id);
        if (encuestaOpt.isPresent()) {
            encuesta.setId(id);
            encuestaRepository.save(encuesta);
            redirectAttributes.addFlashAttribute("mensaje", "Encuesta actualizada con éxito.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Encuesta no encontrada.");
        }
        return "redirect:/encuestas/listar";
    }

    @PostMapping("/encuestas/eliminar/{id}")
    public String eliminarEncuesta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Encuesta> encuestaOpt = encuestaRepository.findById(id);
        if (encuestaOpt.isPresent()) {
            encuestaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Encuesta eliminada.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Encuesta no encontrada.");
        }
        return "redirect:/encuestas/listar";
    }
}
