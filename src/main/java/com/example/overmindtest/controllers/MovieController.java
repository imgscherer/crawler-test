package com.example.overmindtest.controllers;

import com.example.overmindtest.entities.Movie;
import com.example.overmindtest.services.implementations.MovieServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MovieController {

    @Autowired
    MovieServiceImplementation service;

    @GetMapping
    public ModelAndView listAllMovies(){
        List<Movie> movieList = service.listMovies("chart/bottom", "en");
        ModelAndView modelAndView = new  ModelAndView("moviesList");
        modelAndView.addObject("moviesList",movieList);
        return modelAndView;
    }
}
