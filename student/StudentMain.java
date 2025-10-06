//package rs.ac.bg.etf.sab;
package student;

//import rs.ac.bg.etf.sab.operations.*;
//import rs.ac.bg.etf.sab.tests.TestHandler;
//import rs.ac.bg.etf.sab.tests.TestRunner;


import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import rs.ac.bg.etf.sab.operations.*;
import rs.ac.bg.etf.sab.tests.TestHandler;
import rs.ac.bg.etf.sab.tests.TestRunner;

import java.sql.*;



public class StudentMain {
//    public static void IspisiFilmove() {
//        Connection conn=DB.getInstance().getConnection();
//
//
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs=stmt.executeQuery("select * from Film");
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//
//            while(rs.next()){
//                for(int i=1;i<rsmd.getColumnCount();i++){
//
//                    if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
//                        System.out.print(rsmd.getColumnName(i)+ "="+ rs.getInt(i));
//                    }
//                    if(rsmd.getColumnType(i)== Types.VARCHAR){
//                        System.out.print(" "+rsmd.getColumnName(i)+"=" +rs.getString(i));
//                    }
//                    System.out.println();
//                }
//                System.out.println(rs.getString(1)+" "+rs.getString(2)+" " +rs.getString(3));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public static void main(String[] args) throws Exception {
// Uncomment and change fallowing lines

//        IspisiFilmove();
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
