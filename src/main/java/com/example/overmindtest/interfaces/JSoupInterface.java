package com.example.overmindtest.interfaces;

import org.jsoup.nodes.Document;

public interface JSoupInterface<T> {
    public Document initialize(String path, String language);
}
