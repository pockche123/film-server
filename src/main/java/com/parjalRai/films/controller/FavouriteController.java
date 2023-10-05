package com.parjalRai.films.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parjalRai.films.model.Favourite;
import com.parjalRai.films.service.FavouriteService;

@RestController
@RequestMapping("/api/v1/favourites")
@CrossOrigin(origins ="http://localhost:3000")
public class FavouriteController {


    @Autowired
    private FavouriteService favouriteService;


    @GetMapping
    public ResponseEntity<List<Favourite>> getAllFavourites() {
        return new ResponseEntity<List<Favourite>>(favouriteService.findAllFavorites(), HttpStatus.OK);

    }
    
    @GetMapping("/{username}")
    public ResponseEntity<List<Favourite>> getAllFavouritesByUsername(@PathVariable String username) {
        return new ResponseEntity<List<Favourite>>(favouriteService.findAllFavouritesByUsername(username),
                HttpStatus.OK);
    }

    
    
}
