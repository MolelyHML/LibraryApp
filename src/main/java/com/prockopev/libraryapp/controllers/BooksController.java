package com.prockopev.libraryapp.controllers;

import com.prockopev.libraryapp.models.Book;
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
    public String addBook(@ModelAttribute("book")Book book) {
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors())
            return "/books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model,
                       @ModelAttribute("person")Person person) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("owner", bookService.findById(id).getOwner());
        model.addAttribute("allPeople", personService.findAll());

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
        System.out.println(person);
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
