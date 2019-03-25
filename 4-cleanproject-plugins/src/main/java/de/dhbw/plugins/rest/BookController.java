package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.application.book.BookApplicationService;
import de.dhbw.cleanproject.book.BookResource;
import de.dhbw.cleanproject.book.BookToBookResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {

    private BookApplicationService bookApplicationService;

    private BookToBookResourceMapper bookToBookResourceMapper;

    @Autowired
    public BookController(BookApplicationService bookApplicationService, BookToBookResourceMapper bookToBookResourceMapper) {
        this.bookApplicationService = bookApplicationService;
        this.bookToBookResourceMapper = bookToBookResourceMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BookResource> getBooks() {
        return this.bookApplicationService.findAll().stream()
                .map(bookToBookResourceMapper)
                .collect(Collectors.toList());
    }
}
