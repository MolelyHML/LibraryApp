package com.prockopev.libraryapp.controllers;

import com.prockopev.libraryapp.dao.BookDAO;
import com.prockopev.libraryapp.dao.PersonDAO;
import com.prockopev.libraryapp.model.Book;
import com.prockopev.libraryapp.model.Person;
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

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
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
            return "/book/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model,
                       @ModelAttribute("person")Person person) {
        model.addAttribute("book",bookDAO.show(id));
        model.addAttribute("people", bookDAO.getPerson(bookDAO.show(id).getPersonId(),id));
        model.addAttribute("allPeople", personDAO.index());

        return "/books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
        bookDAO.update(book, id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String free(@PathVariable("id") int id) {
        bookDAO.free(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/catch")
    public String catchBook(@PathVariable("id")int id,
                            @ModelAttribute("person")Person person) {
        bookDAO.catchBook(id, person.getPersonId());
        System.out.println(person);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id) {
        bookDAO.delete(id);

        return "redirect:/books";
    }

//    @GetMapping("/{id}/edit")
}
