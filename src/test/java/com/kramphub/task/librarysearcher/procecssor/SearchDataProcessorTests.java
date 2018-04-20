package com.kramphub.task.librarysearcher.procecssor;

import com.kramphub.task.librarysearcher.invoker.APIInvokerService;
import com.kramphub.task.librarysearcher.model.movies.Movies;
import com.kramphub.task.librarysearcher.model.movies.Result;
import com.kramphub.task.librarysearcher.model.output.LibraryModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SearchDataProcessorTests {

    @Mock
    APIInvokerService apiInvokerService;

    @InjectMocks
    @Spy
    SearchDataProcessor searchDataProcessor;

    @Test
    public void testMapMoviesWhenAPIDown() {
        LibraryModel lm = new LibraryModel();
        searchDataProcessor.mapMovies(null, lm);
        Assert.assertEquals("Movie Database is experiencing turbulence! ", lm.getInfoMsg());
    }

    @Test
    public void testMapMoviesPositive() {
        Movies movies = new Movies();
        List<Result> results = new ArrayList<>();
        Result result = new Result();
        result.setArtistName("Chris Nolan");
        result.setTrackName("Interstellar");
        results.add(result);
        movies.setResults(results);
        LibraryModel lm = new LibraryModel();
        searchDataProcessor.mapMovies(movies, lm);
        Assert.assertEquals("Interstellar", lm.getItemList().get(0).getTitle());
    }
}
