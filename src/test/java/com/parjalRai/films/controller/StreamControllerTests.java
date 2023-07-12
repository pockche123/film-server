package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Stream;
import com.parjalRai.films.model.dto.StreamDTO;
import com.parjalRai.films.service.StreamService;

@ExtendWith(MockitoExtension.class)
public class StreamControllerTests {
    
    @Mock
    StreamService streamService;
    
    @InjectMocks
    StreamController streamController;

    Stream stream1 = new Stream();
    Stream stream2 = new Stream();

    @BeforeEach
    public void setUp() {
        stream1 = new Stream();
        stream2 = new Stream();
    }

    @AfterEach
    public void tearDown() {
        stream1 = null;
        stream2 = null;
    }

    @Test
    public void testGetAllStreams_ReturnsAListOfStreams() {
        //arrange
        List<Stream> expectedStream = Arrays.asList(stream1, stream2);
        when(streamService.findAllStreams()).thenReturn(expectedStream);

        //act
        ResponseEntity<List<Stream>> response = streamController.getAllStreams();

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStream, response.getBody());
        verify(streamService).findAllStreams();
    }

    @Test
    public void testGetAStream_WhenAValidIdIsPassed() {
        //arrange
        ObjectId id = new ObjectId();
        stream1.setId(id);
        when(streamService.findStream(id)).thenReturn(Optional.of(stream1));

        //act
        ResponseEntity<Optional<Stream>> response = streamController.getAStream(id);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(stream1), response.getBody());
        verify(streamService).findStream(id);
    }

    @Test
    public void testGetListOfStream_WhenAValidFilmTitleCalled() {
        //arrange
        String title = "Django";
        Film film = new Film();
        film.setTitle(title);
        stream1.setFilm(film);
        stream2.setFilm(film);
        List<Stream> expectedStreams = Arrays.asList(stream1, stream2);
        when(streamService.findByFilm(title)).thenReturn(expectedStreams);

        //act
        ResponseEntity<List<Stream>> response = streamController.getStreamByFilm(title);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStreams, response.getBody());
        verify(streamService).findByFilm(title);
    }

    @Test
    public void testCreateStream_ShouldReturnOkStatus_WhenCreated() {
        StreamDTO streamDTO = new StreamDTO("string", "string", "string", "string", "string");
        stream1.setName(streamDTO.name());
        when(streamService.createStream(streamDTO.name(), streamDTO.icon(), streamDTO.link(), streamDTO.country(),
                streamDTO.country()))
                .thenReturn(stream1);

        //act
        ResponseEntity<Stream> response = streamController.createStream(streamDTO);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stream1, response.getBody());
        verify(streamService).createStream(streamDTO.name(), streamDTO.icon(), streamDTO.link(), streamDTO.country(),
                streamDTO.filmTitle());
    }

    @Test
    public void testUpdateStream_ReturnResponseOK_WhenStreamUpdated() {
        //arrange
        stream1.setName("google play");
        ObjectId id = new ObjectId();
        stream1.setId(id);
        when(streamService.updateStreamDetails(stream1, id)).thenReturn(stream1);

        //act
        ResponseEntity<Stream> response = streamController.updateStream(stream1, id);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stream1, response.getBody());

    }
    
    @Test
    public void testDeleteStream_ReturnTrue_WhenStreamDeleted() {
        ObjectId id = new ObjectId();
        stream1.setId(id);
        when(streamService.deleteStream(id)).thenReturn(true);

        //act
        ResponseEntity<String> response = streamController.deleteStream(id);

        //assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Stream deleted successfully", response.getBody());
        verify(streamService).deleteStream(id);
    }



    
    





    
    
}
