package student;
import java.util.List;
import java.util.ArrayList;
import rs.ac.bg.etf.sab.operations.*;
import java.sql.*;

public class ca220454_TagsOperations implements TagsOperations {
    private final Connection conn;

    public ca220454_TagsOperations() {
        this.conn = DB.getInstance().getConnection();
    }

    @Override
    public Integer addTag(Integer movieId, String tagName) {
        try {
            PreparedStatement getTag = conn.prepareStatement(
                    "SELECT IdTag FROM Tag WHERE Naziv=?");
            getTag.setString(1, tagName);
            ResultSet rs = getTag.executeQuery();
            if (!rs.next()) return null; //tag ne postoji
            int tagId = rs.getInt(1);
            rs.close();
            getTag.close();

            PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO FilmTag(IdFilm, IdTag) VALUES (?, ?)");
            insert.setInt(1, movieId);
            insert.setInt(2, tagId);
            insert.executeUpdate();
            insert.close();

            return movieId;

        } catch (SQLException e) {
            return null;
        }
    }



    @Override
    public Integer removeTag(Integer movieId, String tagName) {
        try {
            PreparedStatement getTagId = conn.prepareStatement(
                    "SELECT IdTag FROM Tag WHERE Naziv=?");
            getTagId.setString(1, tagName);
            ResultSet rs = getTagId.executeQuery();
            if (!rs.next()) {
                rs.close();
                getTagId.close();
                return null;
            }
            int tagId = rs.getInt(1);
            rs.close();
            getTagId.close();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM FilmTag WHERE IdFilm=? AND IdTag=?");
            stmt.setInt(1, movieId);
            stmt.setInt(2, tagId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows > 0 ? movieId : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int removeAllTagsForMovie(Integer movieId) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM FilmTag WHERE IdFilm=?");
            stmt.setInt(1, movieId);
            int rows = stmt.executeUpdate();
            stmt.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<String> getTagsForMovie(Integer movieId) {
        List<String> tags = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT t.Naziv FROM Tag t JOIN FilmTag ft ON t.IdTag = ft.IdTag WHERE ft.IdFilm=?");
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tags.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    @Override
    public List<Integer> getMovieIdsByTag(String tagName) {
        List<Integer> movies = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT ft.IdFilm FROM FilmTag ft JOIN Tag t ON ft.IdTag = t.IdTag WHERE t.Naziv=?");
            stmt.setString(1, tagName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                movies.add(rs.getInt(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public boolean hasTag(Integer movieId, String tagName) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM FilmTag ft JOIN Tag t ON ft.IdTag = t.IdTag WHERE ft.IdFilm=? AND t.Naziv=?");
            stmt.setInt(1, movieId);
            stmt.setString(2, tagName);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            rs.close();
            stmt.close();
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT DISTINCT Naziv FROM Tag");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tags.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }
}
