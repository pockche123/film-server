package com.parjalRai.films.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parjalRai.films.exception.NotFoundException;
import com.parjalRai.films.model.Discussion;
import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.UserEntity;
import com.parjalRai.films.repository.DiscussionRepository;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.UserEntityRepository;

@Service
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserEntityRepository userRepository;

    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    public Optional<Discussion> getDiscussion(ObjectId id) {
        return discussionRepository.findById(id);
    }

    public List<Discussion> getDiscussionsByFilm(String filmTitle) {

        Optional<Film> optionalFilm = filmRepository.findFilmByTitleIgnoreCase(filmTitle);
        Film film = optionalFilm.orElseThrow(() -> new NotFoundException("film not found"));
        return discussionRepository.findByFilm(film);
    }

    public List<Discussion> getDiscussionsByUser(String username) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        UserEntity user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));
        return discussionRepository.findByUser(user);
    }

    public Discussion createDiscussion(String filmTitle, String username, String title, String description,
            Long likes) {

        Optional<Film> optionalFilm = filmRepository.findFilmByTitleIgnoreCase(filmTitle);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

        Film film = optionalFilm.orElseThrow(() -> new NotFoundException("Film not found"));
        UserEntity user = optionalUser.orElseThrow(() -> new NotFoundException("User not found"));

        Discussion discussion = new Discussion();
        discussion.setFilm(film);
        discussion.setUser(user);
        discussion.setTitle(title);
        discussion.setTimestamp(Instant.now());
        discussion.setDescription(description);
        discussion.setLikes(likes);

        return discussionRepository.save(discussion);
    }

    public boolean isTheOwner(ObjectId id, String username) {

        Optional<Discussion> optDiscussion = discussionRepository.findById(id);
        Optional<UserEntity> optUser = userRepository.findByUsername(username);

        Discussion discussion = optDiscussion.orElseThrow(() -> new NotFoundException("Discussion not found"));
        UserEntity user = optUser.orElseThrow(() -> new NotFoundException("User not found"));

        return discussion != null && discussion.getUser().equals(user);
    }

    public Discussion updateDiscussionDetails(Discussion discussion, ObjectId id) {
        try {
            Optional<Discussion> optDiscussion = discussionRepository.findById(id);
            Discussion updatedDiscussion = optDiscussion.get();
            if (discussion.getTitle() != null) {
                updatedDiscussion.setTitle(discussion.getTitle());
            }
            if (discussion.getDescription() != null) {
                updatedDiscussion.setDescription(discussion.getDescription());
            }
            updatedDiscussion.setLikes(discussion.getLikes());
            updatedDiscussion.setTimestamp(Instant.now());

            return discussionRepository.save(updatedDiscussion);

        } catch (Exception e) {
            System.err.println("ERROR WHILE UPDATING DISCUSSION");

        }

        return null;
    }
    
    public boolean deleteDiscussion(ObjectId id) {
        return discussionRepository.findById(id).map(discussion -> {
            discussionRepository.delete(discussion);
            return true;
        }).orElse(false);
        
    }

}
