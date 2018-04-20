package com.kramphub.task.librarysearcher.procecssor;

import com.kramphub.task.librarysearcher.invoker.APIInvokerService;
import com.kramphub.task.librarysearcher.model.books.Books;
import com.kramphub.task.librarysearcher.model.movies.Movies;
import com.kramphub.task.librarysearcher.model.output.Item;
import com.kramphub.task.librarysearcher.model.output.ItemType;
import com.kramphub.task.librarysearcher.model.output.LibraryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;

/**
 * The processor ans search aggregator class
 */
@Service
public class SearchDataProcessor {

    private final APIInvokerService apiInvokerService;


    @Autowired
    public SearchDataProcessor(APIInvokerService apiInvokerService) {
        this.apiInvokerService = apiInvokerService;
    }

    /**
     * This method returns a consolidated search result
     *
     * @param searchString the search query to look up on
     * @return LibraryModel the resource model of the service API
                * @throws IOException in case of jackson de-serialization issue
                */
        public LibraryModel getSearchResults(String searchString) throws IOException {
            Movies movies = apiInvokerService.searchMovieLibrary(searchString);
            Books books = apiInvokerService.searchBooksLibrary(searchString);

        return mapSearchResultsToDataModel(movies, books);
}


    LibraryModel mapSearchResultsToDataModel(Movies movies, Books books) {
        LibraryModel libraryModel = new LibraryModel();
        mapMovies(movies, libraryModel);
        mapBooks(books, libraryModel);
        if (libraryModel.getItemList().isEmpty()) {
            libraryModel.setInfoMsg("No records Found!");
        }
        libraryModel.getItemList().sort(Comparator.comparing(Item::getTitle));
        return libraryModel;
    }

    void mapBooks(Books books, LibraryModel libraryModel) {
        if (null != books && !books.isSearchDown()) {
            if (books.getTotalItems() > 0) {
                books.getItems().forEach(result -> {
                    Item item = new Item();
                    if (null != result.getVolumeInfo()) {
                        item.setTitle(result.getVolumeInfo().getTitle());
                        item.setAuthor(result.getVolumeInfo().getAuthors() == null ? "" : result.getVolumeInfo().getAuthors().get(0));
                    }
                    item.setItemType(ItemType.BOOK.name());
                    libraryModel.getItemList().add(item);
                });
            }
        } else {
            libraryModel.setInfoMsg(libraryModel.getInfoMsg().concat("Books Database is experiencing turbulence!"));
        }
    }

    void mapMovies(Movies movies, LibraryModel libraryModel) {
        if (null != movies && !movies.isSearchDown()) {
            movies.getResults().forEach(result -> {
                Item item = new Item();
                item.setItemType(ItemType.MOVIE.name());
                item.setArtist(result.getArtistName());
                item.setTitle(result.getTrackName());
                libraryModel.getItemList().add(item);
            });
        } else {
            libraryModel.setInfoMsg(libraryModel.getInfoMsg().concat("Movie Database is experiencing turbulence! "));
        }
    }
}
