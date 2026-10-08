package com.practica.libros.controller;

import com.practica.libros.dto.LibroDTO;
import com.practica.libros.service.LibroService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<LibroDTO>> listarLibros() {
        return ResponseEntity.ok(libroService.listarLibros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<LibroDTO> crearLibro(
            @RequestBody LibroDTO dto) {

        LibroDTO nuevoLibro = libroService.crearLibro(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoLibro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizarLibro(
            @PathVariable Long id,
            @RequestBody LibroDTO dto) {

        return ResponseEntity.ok(
                libroService.actualizarLibro(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(
            @PathVariable Long id) {

        libroService.eliminarLibro(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> manejarNoEncontrado(
            NoSuchElementException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}