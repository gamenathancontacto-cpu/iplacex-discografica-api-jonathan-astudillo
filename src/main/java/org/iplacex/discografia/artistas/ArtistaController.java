package org.iplacex.discografia.artistas;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepository;

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {

        try {
            Artista artistaGuardado = artistaRepository.save(artista);

            return ResponseEntity
                    .status(201)
                    .body(artistaGuardado);

        } catch (Exception e) {

            return ResponseEntity
                    .status(500)
                    .body("Error al registrar el artista");
        }
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {

        try {
            List<Artista> artistas = artistaRepository.findAll();

            return ResponseEntity
                    .status(200)
                    .body(artistas);

        } catch (Exception e) {

            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }
    @GetMapping(
    value = "/artista/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable String id) {

    try {

        if (artistaRepository.existsById(id)) {

            Artista artista = artistaRepository.findById(id).get();

            return ResponseEntity
                    .status(200)
                    .body(artista);
        }

        return ResponseEntity
                .status(404)
                .body("Artista no encontrado");

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body("Error al obtener el artista");
    }
}
    @PutMapping(
    value = "/artista/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Object> HandleUpdateArtistaRequest(
        @PathVariable String id,
        @RequestBody Artista artista) {

    try {

        if (artistaRepository.existsById(id)) {

            artista._id = id;
            Artista artistaActualizado = artistaRepository.save(artista);

            return ResponseEntity
                    .status(200)
                    .body(artistaActualizado);
        }

        return ResponseEntity
                .status(404)
                .body("Artista no encontrado");

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body("Error al actualizar el artista");
    }
}
@DeleteMapping(
    value = "/artista/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable String id) {

    try {

        if (artistaRepository.existsById(id)) {

            artistaRepository.deleteById(id);

            return ResponseEntity
                    .status(200)
                    .body("Artista eliminado correctamente");
        }

        return ResponseEntity
                .status(404)
                .body("Artista no encontrado");

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body("Error al eliminar el artista");
    }
}
}
