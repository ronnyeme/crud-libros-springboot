package com.practica.libros.service;

import com.practica.libros.dto.LibroDTO;
import com.practica.libros.model.Libro;
import com.practica.libros.repository.LibroRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // Convertir entidad a DTO
    private LibroDTO convertirDTO(Libro libro) {
        return new LibroDTO(
                libro.getId(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getIsbn(),
                libro.getPrecio()
        );
    }

    // 1. LISTAR TODOS LOS LIBROS
    public List<LibroDTO> listarLibros() {
        return libroRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    // 2. BUSCAR LIBRO POR ID
    public LibroDTO buscarPorId(Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Libro no encontrado con ID: " + id
                        )
                );

        return convertirDTO(libro);
    }

    // 3. CREAR LIBRO
    @Transactional
    public LibroDTO crearLibro(LibroDTO dto) {

        Libro libro = new Libro();

        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setPrecio(dto.getPrecio());

        Libro guardado = libroRepository.save(libro);

        return convertirDTO(guardado);
    }

    // 4. ACTUALIZAR LIBRO
    @Transactional
    public LibroDTO actualizarLibro(Long id, LibroDTO dto) {

        Libro libro = libroRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Libro no encontrado con ID: " + id
                        )
                );

        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setIsbn(dto.getIsbn());
        libro.setPrecio(dto.getPrecio());

        Libro actualizado = libroRepository.save(libro);

        return convertirDTO(actualizado);
    }

    // 5. ELIMINAR LIBRO
    @Transactional
    public void eliminarLibro(Long id) {

        Libro libro = libroRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Libro no encontrado con ID: " + id
                        )
                );

        libroRepository.delete(libro);
    }
}