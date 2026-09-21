package org.iplacex.discografia.discos;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.iplacex.discografia.artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;
@PostMapping(
    value = "/disco",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {

    try {

        if (artistaRepository.existsById(disco.idArtista)) {

            Disco discoGuardado = discoRepository.save(disco);

            return ResponseEntity
                    .status(201)
                    .body(discoGuardado);
        }

        return ResponseEntity
                .status(404)
                .body("Artista no encontrado");

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body("Error al registrar el disco");
    }
}
@GetMapping(
    value = "/discos",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

    try {

        List<Disco> discos = discoRepository.findAll();

        return ResponseEntity
                .status(200)
                .body(discos);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(null);
    }
}
@GetMapping(
    value = "/disco/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable String id) {

    try {

        if (discoRepository.existsById(id)) {

            Disco disco = discoRepository.findById(id).get();

            return ResponseEntity
                    .status(200)
                    .body(disco);
        }

        return ResponseEntity
                .status(404)
                .body("Disco no encontrado");

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body("Error al obtener el disco");
    }
}
@GetMapping(
    value = "/artista/{id}/discos",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(
        @PathVariable String id) {

    try {

        List<Disco> discos = discoRepository.findDiscosByIdArtista(id);

        return ResponseEntity
                .status(200)
                .body(discos);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body(null);
    }
}
@PutMapping(
    value = "/disco/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Object> HandlePutDiscoRequest(
        @PathVariable String id,
        @RequestBody Disco disco) {

    try {

        if (!discoRepository.existsById(id)) {
            return ResponseEntity
                    .status(404)
                    .body("Disco no encontrado");
        }

        if (!artistaRepository.existsById(disco.idArtista)) {
            return ResponseEntity
                    .status(404)
                    .body("Artista no encontrado");
        }

        disco._id = id;

        Disco discoActualizado = discoRepository.save(disco);

        return ResponseEntity
                .status(200)
                .body(discoActualizado);

    } catch (Exception e) {

        return ResponseEntity
                .status(500)
                .body("Error al actualizar el disco");
    }
}
}