package com.parjalRai.films.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Stream;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.StreamRepository;

@Service
public class StreamService {

    @Autowired
    private StreamRepository streamRepository;

    @Autowired
    private FilmRepository filmRepository; 


    public List<Stream> findAllStreams() {
        return streamRepository.findAll();
    }

    public Optional<Stream> findStream(ObjectId id) {
        return streamRepository.findById(id);
    }

    public List<Stream> findByFilm(String title) {
        Optional<Film> optFilm = filmRepository.findFilmByTitleIgnoreCase(title);
        return streamRepository.findByFilm(optFilm.get());
    }

    public Stream createStream(String name, String icon, String link, String country, String filmTitle) {

        Optional<Film> optFilm = filmRepository.findFilmByTitleIgnoreCase(filmTitle);
        Film film = optFilm.orElseThrow(() -> new NotFoundException("Film not found"));

        Stream stream = new Stream();
        stream.setName(name);
        stream.setIcon(icon);
        stream.setLink(link);
        stream.setFilm(film);
        stream.setCountry(country);

        return streamRepository.save(stream);
    }

    public Stream updateStreamDetails(Stream stream, ObjectId id) {
        try {
            Optional<Stream> optStream = streamRepository.findById(id);
            Stream updatedStream = optStream.get();
            if (stream.getIcon() != null) {
                updatedStream.setIcon(stream.getIcon());
            }
            if (stream.getCountry() != null) {
                updatedStream.setCountry(stream.getCountry());
            }
            if (stream.getLink() != null) {
                updatedStream.setLink(stream.getLink());
            }
            if (stream.getName() != null) {
                updatedStream.setName(stream.getName());
            }
            return streamRepository.save(updatedStream);
        } catch (Exception e) {
            System.err.println("ERROR WHILE UPDATING STREAM " + e.getMessage());
        }
        return null;
    }
    
    public boolean deleteStream(ObjectId id) {
        return streamRepository.findById(id).map(stream -> {
            streamRepository.delete(stream);
            return true; 
        }).orElse(false);
    }



    


    
}
