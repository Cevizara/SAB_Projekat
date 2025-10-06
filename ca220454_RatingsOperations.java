package student;
import java.util.List;
import java.util.ArrayList;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import rs.ac.bg.etf.sab.operations.*;
import java.sql.*;

public class ca220454_RatingsOperations implements RatingsOperations {


    private final Connection conn;

    public ca220454_RatingsOperations() {
        this.conn = DB.getInstance().getConnection();
    }

    @Override
    public boolean addRating(Integer userId, Integer movieId, Integer rating) {
        try {


            if (getRating(userId, movieId) != null) {
                return false;
            }

            conn.setAutoCommit(false);
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO Ocena(IdKorisnik, IdFilm, Ocena) VALUES (?, ?, ?)");
            insert.setInt(1, userId);
            insert.setInt(2, movieId);
            insert.setInt(3, rating);
            insert.executeUpdate();
            insert.close();

            CallableStatement reward = conn.prepareCall("{call SP_REWARD_USER_ON_RATE(?, ?)}");
            reward.setInt(1, userId);
            reward.setInt(2, movieId);
            reward.execute();
            reward.close();

            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean updateRating(Integer userId, Integer movieId, Integer rating) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE Ocena SET Ocena=? WHERE IdKorisnik=? AND IdFilm=?");
            stmt.setInt(1, rating);
            stmt.setInt(2, userId);
            stmt.setInt(3, movieId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeRating(Integer userId, Integer movieId) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Ocena WHERE IdKorisnik=? AND IdFilm=?");
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Integer getRating(Integer userId, Integer movieId) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT Ocena FROM Ocena WHERE IdKorisnik=? AND IdFilm=?");
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int rating = rs.getInt(1);
                rs.close();
                stmt.close();
                return rating;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<Integer> getRatedMoviesByUser(Integer userId) {
        List<Integer> movies = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdFilm FROM Ocena WHERE IdKorisnik=?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) movies.add(rs.getInt(1));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return movies;
    }

    @Override
    public List<Integer> getUsersWhoRatedMovie(Integer movieId) {
        List<Integer> users = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdKorisnik FROM Ocena WHERE IdFilm=?");
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) users.add(rs.getInt(1));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
