package student;

import rs.ac.bg.etf.sab.operations.*;

import java.sql.*;

public class ca220454_GeneralOperations implements GeneralOperations {

    private final Connection conn;

    public ca220454_GeneralOperations(){
        this.conn= DB.getInstance().getConnection();
    }


    @Override
    public void eraseAll() {

            try (Statement stmt = conn.createStatement()) {

                conn.setAutoCommit(false);
                stmt.executeUpdate("DELETE FROM Watchlist");
                stmt.executeUpdate("DELETE FROM Ocena");
                stmt.executeUpdate("DELETE FROM FilmTag");
                stmt.executeUpdate("DELETE FROM FilmZanr");
                stmt.executeUpdate("DELETE FROM Film");
                stmt.executeUpdate("DELETE FROM Tag");
                stmt.executeUpdate("DELETE FROM Zanr");
                stmt.executeUpdate("DELETE FROM Korisnik");
                conn.commit();
            } catch (SQLException e) {
                try { conn.rollback(); } catch (SQLException ex) {}
                e.printStackTrace();
            }finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {}
            }
        }
}
