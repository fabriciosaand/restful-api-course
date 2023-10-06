package com.fabriciosaand.cursorestfulapis.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fabriciosaand.cursorestfulapis.data.vo.v1.BookVO;
import com.fabriciosaand.cursorestfulapis.exceptions.RequiredObjectIsNullException;
import com.fabriciosaand.cursorestfulapis.model.Book;
import com.fabriciosaand.cursorestfulapis.repository.BookRepository;
import com.fabriciosaand.cursorestfulapis.services.BookService;
import com.fabriciosaand.cursorestfulapis.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    BookRepository repository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockBook();
        MockitoAnnotations.openMocks(input);
    }

    @Test
    void testFindById() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        System.out.println(result.toString());
        assertNotNull(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));

        assertEquals("Title1", result.getTitle());
        assertEquals("Author1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(25D, result.getPrice());

    }

    @Test
    void testCreate() {
        Book entity = input.mockEntity(1);
        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.save(entity)).thenReturn(persisted);

        var result = service.create(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertNotNull(result.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));

        assertEquals("Title1", result.getTitle());
        assertEquals("Author1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(25D, result.getPrice());
    }

    @Test
    void testCreateWithNullBook() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testUpdate() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Book persisted = entity;
        persisted.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(persisted);

        var result = service.update(vo);

        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());

        assertNotNull(result.toString().contains("[</api/Book/v1/1>;rel=\"self\"]"));

        assertEquals("Title1", result.getTitle());
        assertEquals("Author1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(25D, result.getPrice());
    }

    @Test
    void testUpdateWithNullBook() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void testDelete() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }

    @Test
    void testFindAll() {

        List<Book> list = input.mockEntityList();

        when(repository.findAll()).thenReturn(list);

        var books = service.findAll();

        assertNotNull(books);
        assertEquals(14, books.size());

        var bookOne = books.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());

        assertNotNull(bookOne.toString().contains("[</api/Bbook/v1/1>;rel=\"self\"]"));

        assertEquals("Title1", bookOne.getTitle());
        assertEquals("Author1", bookOne.getAuthor());
        assertNotNull(bookOne.getLaunchDate());
        assertEquals(25D, bookOne.getPrice());

        var bookFour = books.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());

        assertNotNull(bookFour.toString().contains("[</api/book/v1/4>;rel=\"self\"]"));

        assertEquals("Title4", bookFour.getTitle());
        assertEquals("Author4", bookFour.getAuthor());
        assertNotNull(bookFour.getLaunchDate());
        assertEquals(25D, bookFour.getPrice());

        var bookSeven = books.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getKey());
        assertNotNull(bookSeven.getLinks());

        assertNotNull(bookSeven.toString().contains("[</api/book/v1/7>;rel=\"self\"]"));

        assertEquals("Title7", bookSeven.getTitle());
        assertEquals("Author7", bookSeven.getAuthor());
        assertNotNull(bookSeven.getLaunchDate());
        assertEquals(25D, bookSeven.getPrice());

    }

}
