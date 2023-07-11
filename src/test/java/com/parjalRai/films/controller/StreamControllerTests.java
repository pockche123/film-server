package com.parjalRai.films.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.parjalRai.films.model.Stream;
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

    





    
    
}
