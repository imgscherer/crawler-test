package com.example.overmindtest.services.implementations;

import com.example.overmindtest.entities.Comment;
import com.example.overmindtest.entities.Movie;
import com.example.overmindtest.services.JSoupServiceInterface;
import com.example.overmindtest.services.MovieServiceInterface;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImplementation implements MovieServiceInterface {

    @Autowired
    JSoupServiceInterface service;

    @Override
    public List<Movie> listMovies(String path, String language){
        Document document = service.initialize(path, language);
        Elements bodyTable = document.select("tbody[class='lister-list']");
        List<Movie> moviesList = addMovieNamesAndRating(bodyTable);
        List <Movie> top10MoviesList =  moviesList.subList(0, 10);
        return addDetailsToMovie(top10MoviesList);
    }

    private List<Movie> addDetailsToMovie(List<Movie> top10MoviesList) {
        List<Movie> movieListDetails= new ArrayList<>();

        for (Movie movie: top10MoviesList) {
            Document document = service.initialize(movie.getUrl(), "en");
            Elements directorsMovie = document.select("div[class='ipc-metadata-list-item__content-container']");
            movie.setDirector(directorsMovie.first().getElementsByAttribute("href").html());
            addCastMovie(movie);
            addCommentToMovie(movie);
            movieListDetails.add(movie);
        }
        return movieListDetails;
    }

    private List<Movie> addMovieNamesAndRating(Elements bodyTable) {
        List<Movie> moviesList = new ArrayList<>();

        for (Element trElement : bodyTable.select("tr")) {
            Movie movie = new Movie();
            movie.setId(Integer.parseInt(trElement.getElementsByClass("posterColumn").get(0).selectFirst("span[name='rk']").attr("data-value")));
            movie.setUrl(trElement.getElementsByClass("posterColumn").get(0).selectFirst("a").attr("href"));
            movie.setName(trElement.getElementsByClass("titleColumn").get(0).getElementsByAttribute("href").text());
            movie.setRate(Double.valueOf(trElement.getElementsByClass("ratingColumn imdbRating").text()));
            moviesList.add(movie);
        }
        return moviesList;
    }

    private void addCastMovie(Movie movie) {
        String [] path = movie.getUrl().split("/");
        List<String> actorList = new ArrayList<>();

        Document document = service.initialize("title/"+path[2]+"/fullcredits/?ref_=tt_cl_sm", "en");
        Elements castTable = document.select("table[class='cast_list']");

        for (Element castElement: castTable.select("tr")) {
            String actor = castElement.select("td:nth-child(2)").text();
            String character = castElement.getElementsByAttributeValue("class", "character").text();

            if(!actor.isEmpty() && !character.isEmpty()){
                String actorAndCharacter = actor.concat(" as " + character);
                actorList.add(actorAndCharacter);
            }
        }
        movie.setCast(actorList);
    }
    private void addCommentToMovie(Movie movie) {
        String [] path = movie.getUrl().split("/");

        Document dom = service.initialize("title/"+path[2]+"/reviews?ref_=tt_urv", "en");
        Elements commentsDiv = dom.select("div[class='lister-list']");
        List<Comment> commentsList = new ArrayList<>();

        for(Element element : commentsDiv.select("div[class^='lister-item mode-detail imdb-user-review ']")) {
            Comment comments;
            if(checkHasRating(element)) {
                Double rate = convertRateToNumber(element.getElementsByClass("ipl-ratings-bar").text());
                if(checkGreaterEqualThanFive(rate)) {
                    comments = createComment(element,rate);
                    commentsList.add(comments);
                }
            }

        }
        movie.setComment(commentsList);
    }

    private Comment createComment(Element element,Double rate) {
        Comment comments = new Comment();
        comments.setTitle(element.getElementsByClass("lister-item-content").get(0).getElementsByClass("title").text());
        comments.setAuthor(element.getElementsByClass("display-name-link").text());
        comments.setRate(String.valueOf(rate));
        comments.setContent(element.getElementsByClass("text show-more__control").text());

        return comments;
    }

    private Boolean checkHasRating(Element element) {
        return element.getElementsByClass("ipl-ratings-bar").hasText();
    }

    private Boolean checkGreaterEqualThanFive(Double number) {
        return number > 4;
    }

    private Double convertRateToNumber(String rate) {
        String [] arrayValues = rate.split("/");
        return Double.parseDouble(arrayValues[0]);
    }
}
