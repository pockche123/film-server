package com.parjalRai.films.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.parjalRai.films.model.Film;
import com.parjalRai.films.model.Stream;
import com.parjalRai.films.repository.FilmRepository;
import com.parjalRai.films.repository.StreamRepository;

@ExtendWith(MockitoExtension.class)
public class StreamServiceTest {

    @Mock
    private StreamRepository streamRepository;

    @Mock
    private FilmRepository filmRepository;
    
    @InjectMocks
    private StreamService streamService;
    
    private Stream stream1;
    private Stream stream2;
    
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
    public void testFindAllStreams_ReturnsAListOfStreams() {
        //arrange
        List<Stream> expectedStreams = Arrays.asList(stream1, stream2);
        when(streamRepository.findAll()).thenReturn(expectedStreams);

        //act
        List<Stream> actualStreams = streamService.findAllStreams();

        //assert
        assertEquals(expectedStreams, actualStreams);
        verify(streamRepository).findAll();
    }

    @Test
    public void testFindAStream_ReturnsAStream_WhenFound() {
        //arrange
        ObjectId id = new ObjectId();
        stream1.setId(id);
        when(streamRepository.findById(id)).thenReturn(Optional.of(stream1));

        //act
        Optional<Stream> actualStream = streamService.findStream(id);

        //assert
        assertEquals(Optional.of(stream1), actualStream);
        verify(streamRepository).findById(id);
    }

    @Test
    public void testFindStreamByFilm_ReturnsAStream_WhenAValidTitlePassed() {
        //arrange
        String title = "Django";
        Film film = new Film();
        film.setTitle(title);
        List<Stream> expectedStreams = Arrays.asList(stream1, stream2);
        when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(Optional.of(film));
        when(streamRepository.findByFilm(film)).thenReturn(expectedStreams);

        //act
        List<Stream> actualStreams = streamService.findByFilm(title);

        //assert
        assertEquals(expectedStreams, actualStreams);
        verify(streamRepository).findByFilm(film);
    }

    @Test
    public void testCreateAStream_ReturnsACreatedStream() {
        //arrange
        String title = "Django";
        Film film = new Film();
        film.setTitle(title);
        when(filmRepository.findFilmByTitleIgnoreCase(title)).thenReturn(Optional.of(film));
        stream1.setName("name");
        stream1.setIcon("icon");
        stream1.setLink("link");
        stream1.setFilm(film);
        stream1.setCountry("country");
        when(streamRepository.save(stream1)).thenReturn(stream1);

        //act
        Stream actualStream = streamService.createStream("name", "icon", "link", "country", title);

        //assert
        assertEquals(stream1, actualStream);
        verify(streamRepository).save(stream1);
    }

    @Test
    public void testUpdateStreamDetails_returnsUpdatedStream_WhenUpdated() {
        ObjectId id = new ObjectId();
        stream1.setId(id);
        stream1.setCountry("UK");
        stream2.setCountry("USA");
        when(streamRepository.findById(id)).thenReturn(Optional.of(stream1));
        when(streamRepository.save(stream1)).thenReturn(stream2);

        //act
        Stream updatedStream = streamService.updateStreamDetails(stream2, id);

        //assert
        assertNotNull(updatedStream);
        assertEquals(stream1.getCountry(), stream2.getCountry());

    }
    
    @Test
    public void testDeleteAStream_ReturnTrue_WhenDeleted() {
        //arrange
        ObjectId id = new ObjectId();
        stream1.setId(id);
        when(streamRepository.findById(id)).thenReturn(Optional.of(stream1));

        //act
        boolean result = streamService.deleteStream(id);

        //act
        assertTrue(result);
        verify(streamRepository).delete(stream1);
    }








    
}
