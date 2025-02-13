package com.example.tarea1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Encuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres.")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(min = 2, message = "Los apellidos deben tener al menos 2 caracteres.")
    private String apellidos;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico debe ser válido.")
    private String email;

    @Min(value = 18, message = "La edad debe ser mayor o igual a 18 años.")
    private int edad;

    @NotBlank(message = "El teléfono es obligatorio.")
    private String telefono;

    @PastOrPresent(message = "La fecha de inicio de la estancia no puede ser futura.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicioEstancia;

    @NotBlank(message = "El motivo de la visita es obligatorio.")
    private String motivoVisita;

    private String serviciosUtilizados;

    @NotBlank(message = "El nivel de satisfacción es obligatorio.")
    private String nivelSatisfaccion;

    private String comentarios;


    public Long getId() {
        return id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public @NotBlank(message = "El nivel de satisfacción es obligatorio.") String getNivelSatisfaccion() {
        return nivelSatisfaccion;
    }

    public String getServiciosUtilizados() {
        return serviciosUtilizados;
    }

    public @NotBlank(message = "El motivo de la visita es obligatorio.") String getMotivoVisita() {
        return motivoVisita;
    }

    public @PastOrPresent(message = "La fecha de inicio de la estancia no puede ser futura.") LocalDate getFechaInicioEstancia() {
        return fechaInicioEstancia;
    }

    public @NotBlank(message = "El teléfono es obligatorio.") String getTelefono() {
        return telefono;
    }

    @Min(value = 18, message = "La edad debe ser mayor o igual a 18 años.")
    public int getEdad() {
        return edad;
    }

    public @NotBlank(message = "El correo electrónico es obligatorio.") @Email(message = "El correo electrónico debe ser válido.") String getEmail() {
        return email;
    }

    public @NotBlank(message = "Los apellidos son obligatorios.") @Size(min = 2, message = "Los apellidos deben tener al menos 2 caracteres.") String getApellidos() {
        return apellidos;
    }

    public @NotBlank(message = "El nombre es obligatorio.") @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres.") String getNombre() {
        return nombre;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void setNivelSatisfaccion(@NotBlank(message = "El nivel de satisfacción es obligatorio.") String nivelSatisfaccion) {
        this.nivelSatisfaccion = nivelSatisfaccion;
    }

    public void setServiciosUtilizados(String serviciosUtilizados) {
        this.serviciosUtilizados = serviciosUtilizados;
    }

    public void setMotivoVisita(@NotBlank(message = "El motivo de la visita es obligatorio.") String motivoVisita) {
        this.motivoVisita = motivoVisita;
    }

    public void setFechaInicioEstancia(@PastOrPresent(message = "La fecha de inicio de la estancia no puede ser futura.") LocalDate fechaInicioEstancia) {
        this.fechaInicioEstancia = fechaInicioEstancia;
    }

    public void setTelefono(@NotBlank(message = "El teléfono es obligatorio.") String telefono) {
        this.telefono = telefono;
    }

    public void setEdad(@Min(value = 18, message = "La edad debe ser mayor o igual a 18 años.") int edad) {
        this.edad = edad;
    }

    public void setEmail(@NotBlank(message = "El correo electrónico es obligatorio.") @Email(message = "El correo electrónico debe ser válido.") String email) {
        this.email = email;
    }

    public void setApellidos(@NotBlank(message = "Los apellidos son obligatorios.") @Size(min = 2, message = "Los apellidos deben tener al menos 2 caracteres.") String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio.") @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres.") String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }
}