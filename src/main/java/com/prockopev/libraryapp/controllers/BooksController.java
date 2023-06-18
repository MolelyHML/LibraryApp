package com.prockopev.libraryapp.controllers;

import com.prockopev.libraryapp.models.Book;
import com.prockopev.libraryapp.models.Genre;
import com.prockopev.libraryapp.models.Person;
import com.prockopev.libraryapp.services.BookService;
import com.prockopev.libraryapp.services.PersonService;
import com.prockopev.libraryapp.util.BookValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {


    private final BookValidator bookValidator;
    private final BookService bookService;

    private final PersonService personService;

    @Autowired
    public BooksController(BookValidator bookValidator, BookService bookService, PersonService personService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "items_per_page", required = false) Integer itemsPerPage,
                        @RequestParam(value = "sort", required = false) String sort) {

        if(sort != null) {
            if(page != null)
                model.addAttribute("books", bookService.findAll(page, itemsPerPage, sort));
            else
                model.addAttribute("books", bookService.findAll(sort));
        }
        else {
            if(page != null)
                model.addAttribute("books", bookService.findAll(page, itemsPerPage));
            else
                model.addAttribute("books", bookService.findAll());
        }

        return "books/index";
    }


    @GetMapping("/new")
    public String addBook(@ModelAttribute("book")Book book, Model model) {
        model.addAttribute("genres",bookService.getAllGenres());
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book")@Valid Book book, @Valid String genre, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors())
            return "/books/new";

        Genre bookGenre = bookService.findGenreByName(genre).orElse(null);
        bookGenre.setBooks(Collections.singletonList(book));
        book.setGenres(Collections.singletonList(bookGenre));
        bookService.save(book);
        bookService.saveGenre(bookGenre);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model,
                       @ModelAttribute("person")Person person) {

        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("owner", book.getOwner());
        model.addAttribute("allPeople", personService.findAll());
        List<Genre> bookGenres = book.getGenres();

        if(bookGenres.isEmpty())
            bookGenres.add(bookService.findGenreByName("Жанр не указан").orElse(null));
        Genre genre = bookGenres.get(0);
        model.addAttribute("genre", genre);

        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model) {
        model.addAttribute("book", bookService.findById(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String free(@PathVariable("id") int id) {
        bookService.free(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/catch")
    public String catchBook(@PathVariable("id")int id,
                            @ModelAttribute("person")Person person) {
        bookService.catchBook(id, person);
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("book")Book book) {
        return "books/search";
    }

    @GetMapping("/find")
    public String find(@ModelAttribute("book") Book book, Model model) {
        String s = book.getTitle();

        Book foundBook = bookService.findByTitleStartingWith(s);

        model.addAttribute("foundBook", foundBook);

        return "/books/found";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id) {
        bookService.delete(id);

        return "redirect:/books";
    }


}
