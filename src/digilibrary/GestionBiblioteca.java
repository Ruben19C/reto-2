package digilibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionBiblioteca {

    // Método auxiliar para asegurar que existan las tablas de alquiler y ejemplar en Somorrostro
    private void verificarTablasAlquilerYEjemplar() {
        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement()) {
            if (conn == null) return;

            // 1. Creamos la tabla ejemplar si no existe
            String sqlEjemplar = "CREATE TABLE IF NOT EXISTS ejemplar ("
                    + "id_ejemplar INT PRIMARY KEY, "
                    + "estado_devolucion VARCHAR(50)"
                    + ")";
            stmt.executeUpdate(sqlEjemplar);

            // 2. Creamos la tabla alquiler si no existe
            String sqlAlquiler = "CREATE TABLE IF NOT EXISTS alquiler ("
                    + "id_socio INT, "
                    + "cod_ejemplar INT, "
                    + "fecha_alquiler DATE, "
                    + "fecha_devolucion DATE"
                    + ")";
            stmt.executeUpdate(sqlAlquiler);
            
            // 3. Insertamos el ejemplar de prueba 101 si la tabla está vacía para que puedas testear
            try {
                stmt.executeUpdate("INSERT IGNORE INTO ejemplar VALUES (101, 'disponible')");
            } catch (Exception e) {
                // Si ya existe o falla, pasa de largo
            }

        } catch (SQLException e) {
            System.out.println("[BD] Aviso al verificar tablas auxiliares: " + e.getMessage());
        }
    }

    // ACCIÓN 1: Registrar un Alquiler de forma directa (Reparado y Blindado)
 // ACCIÓN 1: Registrar un Alquiler (¡AHORA CONTROLANDO QUE NO SE DUPLIQUE!)
    public boolean registrarAlquiler(int idSocio, int codEjemplar) {
        // Aseguramos que existan las tablas auxiliares en el servidor
        verificarTablasAlquilerYEjemplar();

        // 1. Consulta para comprobar el estado actual del libro
        String sqlCheck = "SELECT estado_devolucion FROM ejemplar WHERE id_ejemplar = ?";
        
        // 2. Consultas para realizar el alquiler
        String sqlInsertAlquiler = "INSERT INTO alquiler (id_socio, cod_ejemplar, fecha_alquiler) VALUES (?, ?, CURDATE())";
        String sqlUpdateEjemplar = "UPDATE ejemplar SET estado_devolucion = 'prestado' WHERE id_ejemplar = ?";
        
        try (Connection con = ConexionBD.conectar()) {
            if (con == null) return false;
            
            // --- PASO DE SEGURIDAD: Comprobamos si ya está prestado ---
            try (PreparedStatement pCheck = con.prepareStatement(sqlCheck)) {
                pCheck.setInt(1, codEjemplar);
                try (java.sql.ResultSet rs = pCheck.executeQuery()) {
                    if (rs.next()) {
                        String estado = rs.getString("estado_devolucion");
                        // Si el estado ya es 'prestado', frenamos la operación en seco
                        if ("prestado".equalsIgnoreCase(estado)) {
                            System.out.println(">> Alerta: El ejemplar " + codEjemplar + " ya está alquilado por otra persona.");
                            return false; 
                        }
                    }
                }
            }
            
            // --- SI ESTÁ DISPONIBLE, PROCEDEMOS CON LA TRANSACCIÓN ---
            con.setAutoCommit(false);
            
            try (PreparedStatement pstmt1 = con.prepareStatement(sqlInsertAlquiler);
                 PreparedStatement pstmt2 = con.prepareStatement(sqlUpdateEjemplar)) {
                
                pstmt1.setInt(1, idSocio);
                pstmt1.setInt(2, codEjemplar);
                pstmt1.executeUpdate();
                
                pstmt2.setInt(1, codEjemplar);
                pstmt2.executeUpdate();
                
                con.commit(); // Guardamos los cambios
                System.out.println("Alquiler registrado con éxito en la BD (Modo Directo).");
                return true;
                
            } catch (SQLException e) {
                con.rollback(); // Si falla algo, deshacemos
                System.out.println("Error en la transacción de alquiler: " + e.getMessage());
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("No se pudo conectar para registrar el alquiler: " + e.getMessage());
            return false;
        }
    }

    // ACCIÓN 2: Registrar una Devolución de forma directa (Reparado y Blindado)
 // ACCIÓN 2: Registrar una Devolución de forma directa (Lógica de control corregida)
    public static void registrarDevolucion(Scanner scanner) {
        System.out.println("\n[REGISTRAR DEVOLUCIÓN]");
        
        System.out.print("Ingrese el ID del Socio (ej: 1): ");
        String idUsuario = scanner.next();
        
        System.out.print("Ingrese el Código del Ejemplar (ej: 101): ");
        String codEjemplar = scanner.next();

        // 1. Buscamos y cerramos el alquiler activo de ESE socio con ESE libro
        String sqlUpdateAlquiler = "UPDATE alquiler SET fecha_devolucion = CURDATE() WHERE id_socio = ? AND cod_ejemplar = ? AND fecha_devolucion IS NULL";
        // 2. Volvemos a poner el ejemplar como disponible
        String sqlUpdateEjemplar = "UPDATE ejemplar SET estado_devolucion = 'disponible' WHERE id_ejemplar = ?";

        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) return;
            
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlUpdateAlquiler);
                 PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdateEjemplar)) {
                
                pstmt1.setString(1, idUsuario);
                pstmt1.setString(2, codEjemplar);
                
                // executeUpdate() devuelve cuántas filas se han modificado de verdad
                int filasAlquilerModificadas = pstmt1.executeUpdate(); 
                
                // SI NO SE MODIFICÓ NINGUNA FILA, significa que ese socio NO tenía ese libro alquilado
                if (filasAlquilerModificadas == 0) {
                    System.out.println(">> Error: El socio " + idUsuario + " no tiene ningún alquiler activo del ejemplar " + codEjemplar + ".");
                    conn.rollback(); // Cancelamos todo
                    return;
                }
                
                // Si el socio sí lo tenía, procedemos a liberar el ejemplar
                pstmt2.setString(1, codEjemplar);
                pstmt2.executeUpdate();
                
                conn.commit();
                System.out.println(">> Operación realizada con éxito (Modo Directo).");
                
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("No se pudo procesar la devolución: " + e.getMessage());
                System.out.println(">> Error al procesar la devolución.");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión en devolución: " + e.getMessage());
        }
    }

    // ACCIÓN 3: Agregar un Nuevo Libro adaptado a vuestra tabla de Somorrostro
    public boolean agregarLibro(String codLibro, String titulo, String idAutor, String idEditorial) {
        String sql = "INSERT INTO libros (cod_libro, titulo, editorial, id_autor) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, codLibro);      
            pstmt.setString(2, titulo);        
            pstmt.setString(3, idEditorial);   
            pstmt.setString(4, idAutor);       
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("No se pudo agregar el libro: " + e.getMessage());
            return false;
        }
    }
}