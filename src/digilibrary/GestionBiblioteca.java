package digilibrary;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionBiblioteca {

    public boolean registrarAlquiler(int idSocio, int codEjemplar) {
        String sql = "{call sp_registrar_alquiler(?, ?)}";
        
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

    // ACCIÓN 2: Registrar una Devolución limpia
    public static void registrarDevolucion(Scanner scanner) {
        System.out.println("\n[REGISTRAR DEVOLUCIÓN]");
        
        System.out.print("Ingrese el ID del Socio (ej: 1): ");
        String idUsuario = scanner.next();
        
        System.out.print("Ingrese el Código del Ejemplar (ej: 101): ");
        String codEjemplar = scanner.next();

        String sql = "{call sp_registrar_devolucion(?, ?)}";

        // Corregido: Ahora usa ConexionBD.conectar() igual que el método de arriba
        try (Connection conn = ConexionBD.conectar();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, idUsuario);
            stmt.setString(2, codEjemplar);

            stmt.execute();
            System.out.println(">> Operación realizada con éxito.");

        } catch (SQLException e) {
            System.out.println("No se pudo procesar la devolución: " + e.getMessage());
            System.out.println(">> Error al procesar la devolución.");
        }
    }
}