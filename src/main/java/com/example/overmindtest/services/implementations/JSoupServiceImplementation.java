package com.example.overmindtest.services.implementations;

import com.example.overmindtest.services.JSoupServiceInterface;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JSoupServiceImplementation implements JSoupServiceInterface {
    public Document initialize(String path, String language){
        Document document = null;

        try {
            document = Jsoup.connect("https://www.imdb.com/"+path).header("Accept-Language", language).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
