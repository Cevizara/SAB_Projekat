package student;

import rs.ac.bg.etf.sab.operations.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class ca220454_UsersOperations implements UsersOperations {
    private final Connection conn;

    public ca220454_UsersOperations() {
        this.conn = DB.getInstance().getConnection();
    }

    @Override
    public Integer addUser(String username) {
        if (doesUserExist(username)) return null;

        String sql = "INSERT INTO Korisnik (KorisnickoIme) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateUser(Integer id, String newUsername) {
        try {
            // proveri da li postoji user sa id
            PreparedStatement checkId = conn.prepareStatement(
                    "SELECT IdKorisnik FROM Korisnik WHERE IdKorisnik=?"
            );
            checkId.setInt(1, id);
            ResultSet rs = checkId.executeQuery();
            if (!rs.next()) {
                rs.close();
                checkId.close();
                return null;
            }
            rs.close();
            checkId.close();

            // proveri da li novo ime vec postoji
            if (doesUserExist(newUsername)) return null;

            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE Korisnik SET KorisnickoIme=? WHERE IdKorisnik=?"
            );
            stmt.setString(1, newUsername);
            stmt.setInt(2, id);
            int updated = stmt.executeUpdate();
            stmt.close();
            return updated > 0 ? id : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer removeUser(Integer id) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM Korisnik WHERE IdKorisnik=?"
            );
            stmt.setInt(1, id);
            int deleted = stmt.executeUpdate();
            stmt.close();
            return deleted > 0 ? id : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean doesUserExist(String username) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM Korisnik WHERE KorisnickoIme=?"
            );
            stmt.setString(1, username);
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
    public Integer getUserId(String username) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdKorisnik FROM Korisnik WHERE KorisnickoIme=?"
            );
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            Integer id = null;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Integer> getAllUserIds() {
        List<Integer> users = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT IdKorisnik FROM Korisnik"
            );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(rs.getInt(1));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    @Override
    public List<Integer> getRecommendedMoviesFromFavoriteGenres(Integer userId) {
        List<Integer> recommended = new ArrayList<>();

        String sql =
                "WITH UserGenreAvg AS (" +
                        "    SELECT fz.IdZanr, AVG(CAST(o.Ocena AS FLOAT)) AS avgScore " +
                        "    FROM Ocena o " +
                        "    JOIN FilmZanr fz ON o.IdFilm = fz.IdFilm " +
                        "    WHERE o.IdKorisnik = ? " +
                        "    GROUP BY fz.IdZanr " +
                        "    HAVING AVG(CAST(o.Ocena AS FLOAT)) >= 8" +
                        "), " +
                        "MovieStats AS (" +
                        "    SELECT o.IdFilm, COUNT(*) AS numRatings, AVG(CAST(o.Ocena AS FLOAT)) AS avgScore " +
                        "    FROM Ocena o " +
                        "    GROUP BY o.IdFilm" +
                        "), " +
                        "UserWatchlistFilms AS (" +
                        "    SELECT lw.IdFilm " +
                        "    FROM Watchlist lw " +
                        "    WHERE lw.IdKorisnik = ?" +
                        ") " +
                        "SELECT f.IdFilm " +
                        "FROM Film f " +
                        "JOIN FilmZanr fz ON f.IdFilm = fz.IdFilm " +
                        "JOIN UserGenreAvg uga ON fz.IdZanr = uga.IdZanr " +
                        "JOIN MovieStats ms ON f.IdFilm = ms.IdFilm " +
                        "WHERE f.IdFilm NOT IN (SELECT o2.IdFilm FROM Ocena o2 WHERE o2.IdKorisnik = ?) " +
                        "  AND f.IdFilm NOT IN (SELECT lw2.IdFilm FROM UserWatchlistFilms lw2) " +
                        "  AND ((ms.numRatings >= 4 AND ms.avgScore >= 7.5) OR (ms.numRatings < 4 AND ms.avgScore >= 9)) " +
                        "ORDER BY ms.avgScore DESC, f.IdFilm ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId); // za UserGenreAvg
            ps.setInt(2, userId); // za UserWatchlistFilms
            ps.setInt(3, userId); // za Ocena NOT IN

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    recommended.add(rs.getInt("IdFilm"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recommended;
    }



    @Override
    public Integer getRewards(Integer userId) {
        int rewards = 0;
        String sql = "SELECT COUNT(*) AS cnt FROM Ocena WHERE IdKorisnik = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rewards = rs.getInt("cnt") / 10; // primer: 1 nagrada na 10 ocena
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rewards;
    }

    @Override
    public List<String> getThematicSpecializations(Integer userId) {
        List<String> themes = new ArrayList<>();
        String sql = "SELECT DISTINCT t.Naziv " +
                "FROM Ocena o " +
                "JOIN FilmTag ft ON o.IdFilm = ft.IdFilm " +
                "JOIN Tag t ON ft.IdTag = t.IdTag " +
                "WHERE o.IdKorisnik = ? AND o.Ocena >= 8";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    themes.add(rs.getString("Naziv"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return themes;
    }



    @Override
    public String getUserDescription(Integer userId) {
        String description = "undefined";

        // 1. Prebroj koliko je filmova korisnik ocenio
        int numRatedMovies = 0;
        String sqlCountMovies = "SELECT COUNT(*) AS cnt FROM Ocena WHERE IdKorisnik = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlCountMovies)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numRatedMovies = rs.getInt("cnt");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ako ima manje od 10 ocena, opis ostaje "nedefinisan"
        if (numRatedMovies >= 10) {
            // 2. Prebroj koliko je različitih tagova obuhvaćeno tim filmovima
            int numDistinctTags = 0;
            String sqlTags = "SELECT COUNT(DISTINCT t.IdTag) AS tagCount " +
                    "FROM Ocena o " +
                    "JOIN FilmTag ft ON o.IdFilm = ft.IdFilm " +
                    "JOIN Tag t ON ft.IdTag = t.IdTag " +
                    "WHERE o.IdKorisnik = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlTags)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        numDistinctTags = rs.getInt("tagCount");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // 3. Odredi opis na osnovu broja tagova
            description = (numDistinctTags >= 10) ? "radoznao" : "focused";
        }

        return description;
    }

}
