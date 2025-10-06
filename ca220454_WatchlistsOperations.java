package student;
import java.util.List;
import java.util.ArrayList;
import rs.ac.bg.etf.sab.operations.*;
import java.sql.*;

public class ca220454_WatchlistsOperations implements WatchlistsOperations {
    private final Connection conn;

    public ca220454_WatchlistsOperations() {
        this.conn = DB.getInstance().getConnection();
    }

    @Override
    public boolean addMovieToWatchlist(Integer userId, Integer movieId) {
        String query = "INSERT INTO Watchlist (IdKorisnik, IdFilm) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Integer> getMoviesInWatchlist(Integer userId) {
        List<Integer> movies = new ArrayList<>();
        String query = "SELECT IdFilm FROM Watchlist WHERE IdKorisnik = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movies.add(rs.getInt("IdFilm"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    public List<Integer> getUsersWithMovieInWatchlist(Integer movieId) {
        List<Integer> users = new ArrayList<>();
        String query = "SELECT IdKorisnik FROM Watchlist WHERE IdFilm = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.add(rs.getInt("IdKorisnik"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean isMovieInWatchlist(Integer userId, Integer movieId) {
        String query = "SELECT 1 FROM Watchlist WHERE IdKorisnik = ? AND IdFilm = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeMovieFromWatchlist(Integer userId, Integer movieId) {
        String query = "DELETE FROM Watchlist WHERE IdKorisnik = ? AND IdFilm = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, movieId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
