package com.parjalRai.films.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.service.FilmService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/films")
@CrossOrigin(origins = {"http://localhost:3000"})
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms(){
        return new ResponseEntity<List<Film>>(filmService.findAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Film>> getAFilm(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Film>>(filmService.findAFilm(imdbId), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Optional<Film>> getFilmByTitle(@PathVariable String title){
        return new ResponseEntity<Optional<Film>>(filmService.findFilmByTitle(title), HttpStatus.OK);
    }

}
