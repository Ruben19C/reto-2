package digilibrary;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
public class GestionBiblioteca {
	public boolean registrarAlquiler(int idSocio, int codEjemplar) {
        String sql = "{call sp_registrar_alquiler(?, ?)}"; // Así se llama a un procedimiento
        
        try (Connection con = ConexionBD.conectar();
             CallableStatement cstmt = con.prepareCall(sql)) {
            
            cstmt.setInt(1, idSocio);
            cstmt.setInt(2, codEjemplar);
            
            cstmt.execute();
            System.out.println("Alquiler registrado con éxito en la BD.");
            return true;
            
        } catch (SQLException e) {
            System.out.println("No se pudo registrar el alquiler: " + e.getMessage());
            return false;
        }
    }

    // ACCIÓN 2: Registrar una Devolución llamando al procedimiento de la BD
    public boolean devolverEjemplar(int codPrestamo) {
        String sql = "{call sp_devolver_ejemplar(?)}";
        
        try (Connection con = ConexionBD.conectar();
             CallableStatement cstmt = con.prepareCall(sql)) {
            
            cstmt.setInt(1, codPrestamo);
            
            cstmt.execute();
            System.out.println("Devolución registrada con éxito en la BD.");
            return true;
            
        } catch (SQLException e) {
            System.out.println("No se pudo procesar la devolución: " + e.getMessage());
            return false;
        }
    }
}
