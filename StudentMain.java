
package student;


import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import rs.ac.bg.etf.sab.operations.*;
import rs.ac.bg.etf.sab.tests.TestHandler;
import rs.ac.bg.etf.sab.tests.TestRunner;

import java.sql.*;



public class StudentMain {
    public static void main(String[] args) throws Exception {

        GeneralOperations generalOperations = new ca220454_GeneralOperations();
        GenresOperations genresOperations = new ca220454_GenresOperations();
        MoviesOperations moviesOperations = new ca220454_MoviesOperations();
        RatingsOperations ratingsOperation = new ca220454_RatingsOperations();
        TagsOperations tagsOperations = new ca220454_TagsOperations();
        UsersOperations usersOperations = new ca220454_UsersOperations();
        WatchlistsOperations watchlistsOperations = new ca220454_WatchlistsOperations();

        TestHandler.createInstance(
                genresOperations,
                moviesOperations,
                ratingsOperation,
                tagsOperations,
                usersOperations,
                watchlistsOperations,
                generalOperations);
        TestRunner.runTests();


//        Myhandler.createInstance(
//                genresOperations,
//                moviesOperations,
//                ratingsOperation,
//                tagsOperations,
//                usersOperations,
//                watchlistsOperations,
//                generalOperations);
//
//        // Run just your MyPubModTest class
//        JUnitCore junit = new JUnitCore();
//        Request request = Request.aClass(MyPubModTest.class);
//        Result result = junit.run(request);
//
//        System.out.println("Tests run: " + result.getRunCount());
//        System.out.println("Failures: " + result.getFailureCount());
//        result.getFailures().forEach(f -> System.out.println(f.toString()));
    }
}
