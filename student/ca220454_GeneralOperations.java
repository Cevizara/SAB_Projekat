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
        try {
            // Proveri da li je konekcija još uvek otvorena
//            if (conn == null || conn.isClosed()) {
//                throw new SQLException("Konekcija ka bazi je zatvorena ili ne postoji");
//            }

            conn.setAutoCommit(false);

            try (Statement stmt = conn.createStatement()) {
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
                try { conn.rollback(); } catch (SQLException ex) { /* ignorisi */ }
                // Nemoj ponovo da bacaš izuzetak, samo ispiši
                System.out.println("Error in eraseAll: " + e.getMessage());
            }
        } catch (SQLException e) {
            // Ovo će uhvatiti conn.isClosed() grešku i samo će izaći
            System.out.println("Connection already closed111: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) { /* ignorisi */ }
        }
    }

}
