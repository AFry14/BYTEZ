package com.example.experimentDB.experimentDB.controllers;

import com.example.experimentDB.experimentDB.repositories.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class personController {

    private final PersonRepository personRepository;

    public personController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping("/People")
    public String getPeople(Model model){

        model.addAttribute("People", personRepository.findAll());

        return "list";
    }
}
