package com.kramphub.task.librarysearcher.controllers;

import com.kramphub.task.librarysearcher.model.output.LibraryModel;
import com.kramphub.task.librarysearcher.procecssor.SearchDataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * Main controller class  providing the  REST endpoints for search service
 * @author  Arani
 */
@EnableCircuitBreaker
@RestController
@RequestMapping(path = "/v1/search")
public class SearchController {

    private final SearchDataProcessor searchDataProcessor;

    @Autowired
    public SearchController(SearchDataProcessor searchDataProcessor) {
        this.searchDataProcessor = searchDataProcessor;
    }

    /**
     * The main search operation end point
     * @param searchString the search query provided as query param
     * @return list of books/movies
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public LibraryModel search(@RequestParam(value="query") String searchString) throws IOException {
        return searchDataProcessor.getSearchResults(searchString);
    }
}
