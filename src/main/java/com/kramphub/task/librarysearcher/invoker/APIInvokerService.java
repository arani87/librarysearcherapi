package com.kramphub.task.librarysearcher.invoker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kramphub.task.librarysearcher.metrics.InfoMetrics;
import com.kramphub.task.librarysearcher.model.movies.Movies;
import com.kramphub.task.librarysearcher.model.books.Books;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class APIInvokerService {

    private RestTemplate restTemplate;
    private final InfoMetrics infoMetrics;

    @Autowired
    public APIInvokerService(RestTemplateBuilder builder, InfoMetrics infoMetrics) {
        restTemplate = builder.build();
        this.infoMetrics = infoMetrics;
    }

    /**
     * Method to invoke the iTunes API
     *
     * @param searchString the query based on which look up needs to be made over movie and books library
     * @return Movies as a collection of search items in a holder POJO
     */
    @Timed
    @HystrixCommand(fallbackMethod = "defaultMovieSearch")
    public Movies searchMovieLibrary(String searchString) throws IOException {
        // apparently iTunes API returns result as javascript file
        // hence response is assigned to a String before de-serializing
        String response = restTemplate.getForObject("https://itunes.apple.com/search?entity=movie&limit=5&term=" + searchString, String.class);
        infoMetrics.incrementMoviesHit();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, Movies.class);
    }

    /**
     * Method to invoke the Google Books API
     *
     * @param searchString the query based on which look up needs to be made over movie and books library
     * @return Books as a collection of search items in a holder POJO
     */
    @Timed
    @HystrixCommand(fallbackMethod = "defaultBookSearch")
    public Books searchBooksLibrary(String searchString) {
        infoMetrics.incrementBooksHit();
        return restTemplate.getForObject("https://content.googleapis.com/books/v1/volumes?maxResults=5&key=AIzaSyBWu6mg-YfPcvwy3srDtcis4Hdar_xl_nA&q=" + searchString, Books.class);
    }

    /**
     * Fallback method in case Google Books API is unavailable
     *
     * @param searchString the query based on which look up needs to be made over movie and books library
     * @return book with default status
     */
    public Books defaultBookSearch(String searchString) {
        Books book = new Books();
        book.setSearchDown(true);
        return book;
    }

    /**
     * Fallback method in case iTunes API is unavailable
     *
     * @param searchString the query based on which look up needs to be made over movie and books library
     * @return movie with default status
     */
    public Movies defaultMovieSearch(String searchString) {
        Movies movies = new Movies();
        movies.setSearchDown(true);
        return movies;
    }


}
