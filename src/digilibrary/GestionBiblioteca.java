package digilibrary;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionBiblioteca {

    // ACCIÓN 1: Registrar un Alquiler usando el Procedimiento Almacenado
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

    // ACCIÓN 2: Registrar una Devolución usando el Procedimiento Almacenado
    public static void registrarDevolucion(Scanner scanner) {
        System.out.println("\n[REGISTRAR DEVOLUCIÓN]");
        
        System.out.print("Ingrese el ID del Socio (ej: 1): ");
        String idUsuario = scanner.next();
        
        System.out.print("Ingrese el Código del Ejemplar (ej: 101): ");
        String codEjemplar = scanner.next();

        String sql = "{call sp_registrar_devolucion(?, ?)}";

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

    // ACCIÓN 3: Agregar un Nuevo Libro adaptado PERFECTAMENTE a la foto de la BD
    public boolean agregarLibro(String codLibro, String titulo, String idAutor, String idEditorial) {
        // Mirando tu captura: las columnas son cod_libro, titulo, genero, editorial, anio, id_autor.
        // Mapeamos los datos de forma segura rellenando género y año con valores por defecto para que no falle.
        String sql = "INSERT INTO libros VALUES (?, ?, 'novela', ?, 2026, ?)";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codLibro);      // Ej: L007
            pstmt.setString(2, titulo);        // Ej: El nuevo libro de Java
            pstmt.setString(3, idEditorial);   // Ej: Planeta (va a la columna editorial)
            pstmt.setString(4, idAutor);       // Ej: A001 (va a la columna id_autor)
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("No se pudo agregar el libro: " + e.getMessage());
            return false;
        }
    }
}