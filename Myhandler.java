package student;


import rs.ac.bg.etf.sab.operations.GeneralOperations;
import rs.ac.bg.etf.sab.operations.GenresOperations;
import rs.ac.bg.etf.sab.operations.MoviesOperations;
import rs.ac.bg.etf.sab.operations.RatingsOperations;
import rs.ac.bg.etf.sab.operations.TagsOperations;
import rs.ac.bg.etf.sab.operations.UsersOperations;
import rs.ac.bg.etf.sab.operations.WatchlistsOperations;

public class Myhandler {
    private static Myhandler testHandler = null;
    private GenresOperations genresOperations;
    private MoviesOperations moviesOperations;
    private RatingsOperations ratingsOperations;
    private TagsOperations tagsOperations;
    private UsersOperations usersOperations;
    private WatchlistsOperations watchlistsOperations;
    private GeneralOperations generalOperations;

    private Myhandler(GenresOperations genresOperations, MoviesOperations moviesOperations, RatingsOperations ratingsOperations, TagsOperations tagsOperations, UsersOperations usersOperations, WatchlistsOperations watchlistsOperations, GeneralOperations generalOperations) {
        this.genresOperations = genresOperations;
        this.moviesOperations = moviesOperations;
        this.ratingsOperations = ratingsOperations;
        this.tagsOperations = tagsOperations;
        this.usersOperations = usersOperations;
        this.watchlistsOperations = watchlistsOperations;
        this.generalOperations = generalOperations;
    }

    public static void createInstance(GenresOperations genresOperations, MoviesOperations moviesOperations, RatingsOperations ratingsOperations, TagsOperations tagsOperations, UsersOperations usersOperations, WatchlistsOperations watchlistsOperations, GeneralOperations generalOperations) {
        testHandler = new Myhandler(genresOperations, moviesOperations, ratingsOperations, tagsOperations, usersOperations, watchlistsOperations, generalOperations);
    }

    static Myhandler getInstance() {
        return testHandler;
    }

    GenresOperations getGenresOperations() {
        return this.genresOperations;
    }

    MoviesOperations getMoviesOperations() {
        return this.moviesOperations;
    }

    RatingsOperations getRatingsOperations() {
        return this.ratingsOperations;
    }

    TagsOperations getTagsOperations() {
        return this.tagsOperations;
    }

    UsersOperations getUsersOperations() {
        return this.usersOperations;
    }

    WatchlistsOperations getWatchlistsOperations() {
        return this.watchlistsOperations;
    }

    GeneralOperations getGeneralOperations() {
        return this.generalOperations;
    }
}

