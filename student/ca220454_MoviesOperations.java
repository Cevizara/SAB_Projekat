package student;


import rs.ac.bg.etf.sab.operations.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ca220454_MoviesOperations implements MoviesOperations {
    private final Connection conn;

    public ca220454_MoviesOperations() {
        this.conn = DB.getInstance().getConnection();
    }

    @Override
    public Integer addMovie(String title, Integer genreId, String director) {
        try {
            // Proveri da li već postoji isti film sa istim naslovom i direktorom
            PreparedStatement check = conn.prepareStatement(
                    "SELECT IdFilm FROM Film WHERE Naslov=? AND Reziser=?");
            check.setString(1, title);
            check.setString(2, director);
            ResultSet rs = check.executeQuery();
            if (rs.next()) return null; // već postoji
            rs.close();
            check.close();

            // Ubaci novi film
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Film(Naslov, Reziser) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, title);
            stmt.setString(2, director);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int movieId = keys.getInt(1);
                // Dodaj prvi žanr
                addGenreToMovie(movieId, genreId);
                keys.close();
                stmt.close();
                return movieId;
            }
            keys.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateMovieTitle(Integer movieId, String newTitle) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE Film SET Naslov=? WHERE IdFilm=?");
            stmt.setString(1, newTitle);
            stmt.setInt(2, movieId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0 ? movieId : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer updateMovieDirector(Integer movieId, String newDirector) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE Film SET Reziser=? WHERE IdFilm=?");
            stmt.setString(1, newDirector);
            stmt.setInt(2, movieId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0 ? movieId : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer removeMovie(Integer movieId) {
        try {
            // prvo ukloni veze sa žanrovima
            PreparedStatement delGenres = conn.prepareStatement(
                    "DELETE FROM FilmZanr WHERE IdFilm=?");
            delGenres.setInt(1, movieId);
            delGenres.executeUpdate();
            delGenres.close();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Film WHERE IdFilm=?");
            stmt.setInt(1, movieId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0 ? movieId : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer addGenreToMovie(Integer movieId, Integer genreId) {
        try {
            // proveri da li film postoji
            PreparedStatement checkFilm = conn.prepareStatement(
                    "SELECT 1 FROM Film WHERE IdFilm = ?");
            checkFilm.setInt(1, movieId);
            ResultSet rsFilm = checkFilm.executeQuery();
            if (!rsFilm.next()) {
                rsFilm.close();
                checkFilm.close();
                return null; // film ne postoji
            }
            rsFilm.close();
            checkFilm.close();

            // proveri da li žanr postoji
            PreparedStatement checkGenre = conn.prepareStatement(
                    "SELECT 1 FROM Zanr WHERE IdZanr = ?");
            checkGenre.setInt(1, genreId);
            ResultSet rsGenre = checkGenre.executeQuery();
            if (!rsGenre.next()) {
                rsGenre.close();
                checkGenre.close();
                return null; // žanr ne postoji
            }
            rsGenre.close();
            checkGenre.close();

            // proveri da li veza već postoji
            PreparedStatement checkLink = conn.prepareStatement(
                    "SELECT * FROM FilmZanr WHERE IdFilm=? AND IdZanr=?");
            checkLink.setInt(1, movieId);
            checkLink.setInt(2, genreId);
            ResultSet rsLink = checkLink.executeQuery();
            if (rsLink.next()) {
                rsLink.close();
                checkLink.close();
                return null; // veza već postoji
            }
            rsLink.close();
            checkLink.close();

            // ubaci u FilmZanr
            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO FilmZanr(IdFilm, IdZanr) VALUES (?, ?)");
            insert.setInt(1, movieId);
            insert.setInt(2, genreId);
            insert.executeUpdate();
            insert.close();

            return movieId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer removeGenreFromMovie(Integer movieId, Integer genreId) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM FilmZanr WHERE IdFilm=? AND IdZanr=?");
            stmt.setInt(1, movieId);
            stmt.setInt(2, genreId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0 ? movieId : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Integer> getAllMovieIds() {
        List<Integer> res = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IdFilm FROM Film");
            while (rs.next()) res.add(rs.getInt(1));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Integer> getMovieIds(String title, String director) {
        List<Integer> res = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdFilm FROM Film WHERE Naslov=? AND Reziser=?");
            stmt.setString(1, title);
            stmt.setString(2, director);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) res.add(rs.getInt(1));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Integer> getGenreIdsForMovie(Integer movieId) {
        List<Integer> res = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdZanr FROM FilmZanr WHERE IdFilm=?");
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) res.add(rs.getInt(1));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Integer> getMovieIdsByDirector(String director) {
        List<Integer> res = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdFilm FROM Film WHERE Reziser=?");
            stmt.setString(1, director);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) res.add(rs.getInt(1));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Integer> getMovieIdsByGenre(Integer genreId) {
        List<Integer> res = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdFilm FROM FilmZanr WHERE IdZanr=?");
            stmt.setInt(1, genreId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) res.add(rs.getInt(1));
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
