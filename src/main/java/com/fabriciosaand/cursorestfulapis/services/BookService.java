package com.fabriciosaand.cursorestfulapis.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.fabriciosaand.cursorestfulapis.controllers.BookController;
import com.fabriciosaand.cursorestfulapis.data.vo.v1.BookVO;
import com.fabriciosaand.cursorestfulapis.exceptions.RequiredObjectIsNullException;
import com.fabriciosaand.cursorestfulapis.exceptions.ResourceNotFoundException;
import com.fabriciosaand.cursorestfulapis.mapper.DozerMapper;
import com.fabriciosaand.cursorestfulapis.model.Book;
import com.fabriciosaand.cursorestfulapis.repository.BookRepository;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookRepository repository;

    public List<BookVO> findAll() {

        logger.info("BookService :: findAll | Finding all books");

        var books = DozerMapper.parseListObject(repository.findAll(), BookVO.class);

        books
            .stream()
            .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

        return books;
    }

    public BookVO findById(Long id) {

        logger.info("BookService :: findById | Finding on book");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("BookService :: create | Creating one book");

        var entity = DozerMapper.parseObject(book, Book.class);

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {

        if(book == null) throw new RequiredObjectIsNullException();

        logger.info("BookService :: update | Updating one book");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setTitle(book.getTitle());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("BookService :: delete | Deleting one book");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No record found for this ID!"));

        repository.delete(entity);

    }
    
}
