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
            conn.setAutoCommit(false); // start transakcije

            // Proveri da li već postoji ocena
            try (PreparedStatement check = conn.prepareStatement(
                    "SELECT 1 FROM Ocena WHERE IdKorisnik=? AND IdFilm=?")) {
                check.setInt(1, userId);
                check.setInt(2, movieId);
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return false; // već postoji ocena
                    }
                }
            }

            // Ubaci novu ocenu
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Ocena(IdKorisnik, IdFilm, Ocena) VALUES (?, ?, ?)")) {
                stmt.setInt(1, userId);
                stmt.setInt(2, movieId);
                stmt.setInt(3, rating);
                stmt.executeUpdate();
            }
            // Pozovi reward proceduru
            try (CallableStatement cs = conn.prepareCall("{call SP_REWARD_USER_ON_RATE(?, ?)}")) {
                cs.setInt(1, userId);
                cs.setInt(2, movieId);
                cs.execute();
            }
            conn.commit(); // commit ako sve prođe


            return true;

        } catch (SQLServerException e) {
            try {
                conn.rollback(); // rollback zbog triggera
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (e.getMessage().contains("previše ekstremnih ocena")) return false;
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            try {
                conn.rollback(); // rollback zbog bilo kog drugog SQL exception-a
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true); // vrati konekciju u normalan režim
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
