package com.parjalRai.films.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Stream;
import com.parjalRai.films.model.dto.StreamDTO;
import com.parjalRai.films.service.StreamService;

@RestController
@RequestMapping("/api/v1/stream")
@CrossOrigin(origins="http://localhost:3000")
public class StreamController {

    @Autowired
    private StreamService streamService;

    @GetMapping
    public ResponseEntity<List<Stream>> getAllStreams() {
        return ResponseEntity.ok(streamService.findAllStream());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Stream>> getAStream(@PathVariable ObjectId id) {
        return ResponseEntity.ok(streamService.findStream(id));
    }

    @PostMapping 
    public ResponseEntity<Stream> createStream(@RequestBody StreamDTO streamDTO) {
        try {
            Stream stream = streamService.createStream(streamDTO.name(), streamDTO.icon(), streamDTO.link(),
                    streamDTO.country(), streamDTO.filmTitle());
            return ResponseEntity.ok(stream);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Stream> updateStream(@RequestBody Stream stream, @PathVariable ObjectId id) {
        Stream updatedStream = streamService.updateStreamDetails(stream, id);
        if (updatedStream != null) {
            return ResponseEntity.ok(updatedStream);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStream(@PathVariable ObjectId id){
        boolean deleted = streamService.deleteStream(id);
        if(deleted){
            return ResponseEntity.ok("Stream deleted successfully");
        } else{
            return ResponseEntity.notFound().build();
        }
    }
    
}
