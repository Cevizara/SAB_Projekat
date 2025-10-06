package student;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import rs.ac.bg.etf.sab.operations.GeneralOperations;
import rs.ac.bg.etf.sab.operations.GenresOperations;
import rs.ac.bg.etf.sab.operations.MoviesOperations;
import rs.ac.bg.etf.sab.operations.RatingsOperations;
import rs.ac.bg.etf.sab.operations.TagsOperations;
import rs.ac.bg.etf.sab.operations.UsersOperations;
import rs.ac.bg.etf.sab.operations.WatchlistsOperations;


public class MyPubModTest {
    private Myhandler th;
    private GeneralOperations general;
    private UsersOperations users;
    private GenresOperations genres;
    private MoviesOperations movies;
    private RatingsOperations ratings;
    private TagsOperations tags;
    private WatchlistsOperations watchlists;

    public MyPubModTest() {
    }

    @Before
    public void setUp() {
        this.th = Myhandler.getInstance();
        Assert.assertNotNull(this.th); System.out.println("proso 1");
        this.general = this.th.getGeneralOperations();
        this.users = this.th.getUsersOperations();
        this.genres = this.th.getGenresOperations();
        this.movies = this.th.getMoviesOperations();
        this.ratings = this.th.getRatingsOperations();
        this.tags = this.th.getTagsOperations();
        this.watchlists = this.th.getWatchlistsOperations();
        Assert.assertNotNull(this.general); System.out.println("proso 2");
        Assert.assertNotNull(this.users);System.out.println("proso 3");
        Assert.assertNotNull(this.genres);System.out.println("proso 4");
        Assert.assertNotNull(this.movies);System.out.println("proso 5");
        Assert.assertNotNull(this.ratings);System.out.println("proso 6");
        Assert.assertNotNull(this.tags);System.out.println("proso 7");
        Assert.assertNotNull(this.watchlists);System.out.println("proso 8");
        this.general.eraseAll();System.out.println("proso 9");
    }



    @Test
    public void publicOne() { System.out.println("uso u publicone");



        Integer uAlice = this.users.addUser("alice");
        Integer uBob = this.users.addUser("bob");
        Integer uCarol = this.users.addUser("carol");
        Integer gCrime = this.genres.addGenre("Crime");
        Integer gSci = this.genres.addGenre("Sci-Fi");
        Integer gDrama = this.genres.addGenre("Drama");
        Integer gAdv = this.genres.addGenre("Adventure");
        Integer gFant = this.genres.addGenre("Fantasy");
        Integer mGF = this.movies.addMovie("The Godfather", gCrime, "Francis Ford Coppola");
        Integer mIrish = this.movies.addMovie("The Irishman", gCrime, "Martin Scorsese");
        Integer mPF = this.movies.addMovie("Pulp Fiction", gCrime, "Quentin Tarantino");
        Integer mInB = this.movies.addMovie("In Bruges", gCrime, "Martin McDonagh");
        Integer mMtrx = this.movies.addMovie("The Matrix", gSci, "Lana Wachowski");
        Integer mBR49 = this.movies.addMovie("Blade Runner 2049", gSci, "Denis Villeneuve");
        Integer mInt = this.movies.addMovie("Interstellar", gSci, "Christopher Nolan");
        Integer mShaw = this.movies.addMovie("The Shawshank Redemption", gDrama, "Frank Darabont");
        Integer mDie = this.movies.addMovie("Die Hard", gAdv, "John McTiernan");
        Integer mFury = this.movies.addMovie("Mad Max: Fury Road", gAdv, "George Miller");
        Integer mFifE = this.movies.addMovie("The Fifth Element", gSci, "Luc Besson");
        Integer mPrs = this.movies.addMovie("Prisoners", gDrama, "Denis Villeneuve");
        Integer mMoon = this.movies.addMovie("Moon", gSci, "Duncan Jones");
        Integer mJup = this.movies.addMovie("Jupiter Ascending", gSci, "Lana Wachowski");
        Integer mGood = this.movies.addMovie("Goodfellas", gCrime, "Martin Scorsese");
        Integer mLotr = this.movies.addMovie("The Lord of the Rings: The Fellowship of the Ring", gFant, "Peter Jackson");
        this.movies.addGenreToMovie(mLotr, gAdv);
        this.tags.addTag(mMtrx, "cyberpunk");
        this.tags.addTag(mBR49, "cyberpunk");
        this.tags.addTag(mInt, "space");
        this.tags.addTag(mPF, "nonlinear");
        this.tags.addTag(mDie, "christmas");
        this.tags.addTag(mFury, "road");
        this.tags.addTag(mFifE, "space-opera");
        this.tags.addTag(mInB, "dark-comedy");
        this.tags.addTag(mPrs, "kidnapping");
        this.tags.addTag(mLotr, "fantasy-quest");
        this.tags.addTag(mMtrx, "virtual-reality");
        this.ratings.addRating(uAlice, mGF, 10);
        this.ratings.addRating(uAlice, mIrish, 1);
        this.ratings.addRating(uAlice, mPF, 10);
        this.ratings.addRating(uAlice, mInB, 1);
        boolean extremeAccepted = this.ratings.addRating(uAlice, mGood, 10);System.out.println("doso do prvog asserta");
        Assert.assertFalse(extremeAccepted);  System.out.println("PUBLICONE 1");
        Assert.assertTrue(this.ratings.addRating(uAlice, mGood, 7)); System.out.println("PUBLICONE 2");
        Assert.assertTrue(this.ratings.updateRating(uAlice, mPF, 8)); System.out.println("PUBLICONE 3");
        this.ratings.addRating(uAlice, mMtrx, 10);
        this.ratings.addRating(uAlice, mBR49, 8);
        this.ratings.addRating(uAlice, mInt, 9);
        this.ratings.addRating(uBob, mMtrx, 9);
        this.ratings.addRating(uCarol, mMtrx, 10);
        this.ratings.addRating(uBob, mBR49, 8);
        this.ratings.addRating(uCarol, mBR49, 8);
        this.ratings.addRating(uBob, mInt, 7);
        this.ratings.addRating(uCarol, mInt, 8);
        this.ratings.addRating(uAlice, mLotr, 10);
        this.ratings.addRating(uBob, mLotr, 10);
        this.ratings.addRating(uCarol, mLotr, 10);
        this.watchlists.addMovieToWatchlist(uAlice, mBR49);
        List<Integer> rec = this.users.getRecommendedMoviesFromFavoriteGenres(uAlice);
        Assert.assertTrue(rec.size() == 0); System.out.println("PUBLICONE 4");
        this.ratings.addRating(uBob, mMoon, 9);

        rec = this.users.getRecommendedMoviesFromFavoriteGenres(uAlice);
        System.out.println(rec);
        Assert.assertTrue(rec.contains(mMoon)); System.out.println("PUBLICONE 5");
        Integer uDave = this.users.addUser("dave");
        Integer uEve = this.users.addUser("eve");
        Integer uFrank = this.users.addUser("frank");
        Integer uGrace = this.users.addUser("grace");
        this.ratings.addRating(uBob, mFifE, 9);
        this.ratings.addRating(uCarol, mFifE, 10);
        this.ratings.addRating(uDave, mFifE, 10);
        this.ratings.addRating(uEve, mFifE, 9);
        this.watchlists.addMovieToWatchlist(uAlice, mFifE);
        Integer mArr = this.movies.addMovie("Arrival", gSci, "Denis Villeneuve");
        this.ratings.addRating(uBob, mArr, 9);
        this.ratings.addRating(uCarol, mArr, 8);
        this.ratings.addRating(uDave, mArr, 8);
        this.ratings.addRating(uEve, mArr, 8);
        this.ratings.updateRating(uBob, mMtrx, 10);
        this.ratings.addRating(uDave, mMtrx, 10);
        this.ratings.addRating(uEve, mMtrx, 10);
        this.ratings.addRating(uFrank, mMtrx, 10);
        this.ratings.addRating(uGrace, mMtrx, 10);
        this.ratings.addRating(uDave, mLotr, 10);
        this.ratings.addRating(uEve, mLotr, 10);
        this.ratings.addRating(uFrank, mLotr, 10);
        this.ratings.addRating(uGrace, mLotr, 10);
        rec = this.users.getRecommendedMoviesFromFavoriteGenres(uAlice);
        Assert.assertTrue(rec.contains(mArr));System.out.println("PUBLICONE 6");
        Assert.assertTrue(rec.contains(mMoon));System.out.println("PUBLICONE 7");
        Assert.assertFalse(rec.contains(mFifE));System.out.println("PUBLICONE 8");
        Assert.assertFalse(rec.contains(mInt));System.out.println("PUBLICONE 9");
        Assert.assertFalse(rec.contains(mBR49));System.out.println("PUBLICONE 10");
        Assert.assertFalse(rec.contains(mMtrx));System.out.println("PUBLICONE 11");
        this.ratings.addRating(uBob, mJup, 5);
        this.ratings.addRating(uCarol, mJup, 4);
        this.ratings.addRating(uAlice, mJup, 7);
        Integer rewards = this.users.getRewards(uAlice);
        Assert.assertNotNull(rewards);System.out.println("PUBLICONE 12");
        System.out.println(rewards);
        Assert.assertTrue(rewards == 1);System.out.println("PUBLICONE 13");
        List<String> specs = this.users.getThematicSpecializations(uAlice);
        Assert.assertNotNull(specs);System.out.println("PUBLICONE 14");
        System.out.println(specs);

        Assert.assertTrue(specs.contains("cyberpunk"));System.out.println("PUBLICONE 15");
        this.ratings.addRating(uAlice, mDie, 8);
        this.ratings.addRating(uAlice, mFifE, 8);
        this.ratings.addRating(uAlice, mShaw, 8);
        this.ratings.addRating(uAlice, mPrs, 8);

        String desc = this.users.getUserDescription(uAlice);
//        System.out.println("___________________________"+desc + "___________________________");
        Assert.assertEquals("focused", desc);System.out.println("PUBLICONE 16");
    }
    @After
    public void tearDown() {
        this.general.eraseAll();
    }
}
