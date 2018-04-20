package com.kramphub.task.librarysearcher.metrics;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class InfoMetrics implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Long> usageDetails = new HashMap<>();
        usageDetails.put("booksApiHitCounter", booksApiHitCounter);
        usageDetails.put("moviesApiHitCounter",moviesApiHitCounter);
        usageDetails.put("averageResponseTimeMovie (ms)", averageResponseTimeMovie);
        usageDetails.put("averageResponseTimeBook (ms)",averageResponseTimeBook);
        builder.withDetail("apimetrics", usageDetails);
    }

    private long booksApiHitCounter;
    private long moviesApiHitCounter;

    public long getAverageResponseTimeMovie() {
        return averageResponseTimeMovie;
    }

    public long getAverageResponseTimeBook() {
        return averageResponseTimeBook;
    }

    private long averageResponseTimeMovie;

    public void setAverageResponseTimeMovie(long averageResponseTimeMovie) {
        this.averageResponseTimeMovie = averageResponseTimeMovie;
    }

    public void setAverageResponseTimeBook(long averageResponseTimeBook) {
        this.averageResponseTimeBook = averageResponseTimeBook;
    }

    private long averageResponseTimeBook;

    public void incrementBooksHit(){
        booksApiHitCounter++;
    }

    public long getBooksApiHitCounter() {
        return booksApiHitCounter;
    }

    public long getMoviesApiHitCounter() {
        return moviesApiHitCounter;
    }

    public void incrementMoviesHit(){
        moviesApiHitCounter++;
    }


}
