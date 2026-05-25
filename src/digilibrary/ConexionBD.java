package digilibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://datos.somorrostro.com/2526DAMEquipo03";
    private static final String USER = "2526DAMEquipo03";
    private static final String PASSWORD = "2526DAMEquipo03"; 

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }

    // MÉTODO CRUCIAL: Crea la tabla libros en Somorrostro si no existe
    public static void crearTablaSiNoExiste() {
        // Estructura idéntica de 6 columnas según la captura de tu compañero
        String sqlTablaLibros = "CREATE TABLE IF NOT EXISTS libros ("
                + "cod_libro VARCHAR(50) PRIMARY KEY, "
                + "titulo VARCHAR(255), "
                + "genero VARCHAR(100), "
                + "editorial VARCHAR(100), "
                + "anio INT, "
                + "id_autor VARCHAR(50)"
                + ")";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            
            if (conn != null) {
                stmt.executeUpdate(sqlTablaLibros);
                System.out.println("[BD Somorrostro] Verificación de tablas completada con éxito.");
            }
            
        } catch (SQLException e) {
            System.out.println("[BD Somorrostro] Error al verificar/crear la tabla: " + e.getMessage());
        }
    }
}