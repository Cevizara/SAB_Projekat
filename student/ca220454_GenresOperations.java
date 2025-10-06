package student;

import rs.ac.bg.etf.sab.operations.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ca220454_GenresOperations implements GenresOperations {

    private final Connection conn;
    public ca220454_GenresOperations(){
        this.conn= DB.getInstance().getConnection();
    }

    @Override
    public Integer addGenre(String name) {
        if (doesGenreExist(name)) return null;
        String sql = "INSERT INTO Zanr (Naziv) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
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
    public Integer updateGenre(Integer id, String newName) {
        if (getGenreId(newName) != null) return null; // novi naziv već postoji
        String sql = "UPDATE Zanr SET Naziv = ? WHERE IdZanr = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            int affected = ps.executeUpdate();
            if (affected > 0) return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer removeGenre(Integer id) {
        String sql = "DELETE FROM Zanr WHERE IdZanr = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected > 0) return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean doesGenreExist(String name) {
        String sql = "SELECT 1 FROM Zanr WHERE Naziv = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Integer getGenreId(String name) {
        String sql = "SELECT IdZanr FROM Zanr WHERE Naziv = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("IdZanr");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Integer> getAllGenreIds() {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT IdZanr FROM Zanr";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getInt("IdZanr"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
