package com.example.overmindtest.interfaces;

import java.util.List;

public interface MovieInterface<T> {

    public List<T> listMovies(String path, String language);
}
