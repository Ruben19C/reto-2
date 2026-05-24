package digilibrary;
import java.util.Scanner;
public class MAIN {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestionBiblioteca gestion = new GestionBiblioteca();
        int opcion = 0;

        System.out.println("=========================================");
        System.out.println("  BIENVENIDO AL SISTEMA DIGILIBRARY      ");
        System.out.println("=========================================");

        do {
            System.out.println("\n--- MENÚ DE GESTIÓN ---");
            System.out.println("1. Registrar un Alquiler (Préstamo)");
            System.out.println("2. Registrar una Devolución");
            System.out.println("3. Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = scanner.nextInt();
                
                switch (opcion) {
                    case 1:
                        System.out.println("\n[REGISTRAR ALQUILER]");
                        System.out.print("Ingrese el ID del Socio: ");
                        int idSocio = scanner.nextInt();
                        System.out.print("Ingrese el Código del Ejemplar: ");
                        int codEjemplar = scanner.nextInt();
                        
                        // Llama a tu lógica de Base de Datos
                        boolean alquilerOk = gestion.registrarAlquiler(idSocio, codEjemplar);
                        if (alquilerOk) {
                            System.out.println(">> Operación realizada con éxito.");
                        } else {
                            System.out.println(">> Error al procesar el alquiler.");
                        }
                        break;

                    case 2:
                        System.out.println("\n[REGISTRAR DEVOLUCIÓN]");
                        System.out.print("Ingrese el Código del Préstamo/Alquiler: ");
                        int codPrestamo = scanner.nextInt();
                        
                        // Llama a tu lógica de Base de Datos
                        boolean devolucionOk = gestion.devolverEjemplar(codPrestamo);
                        if (devolucionOk) {
                            System.out.println(">> Operación realizada con éxito.");
                        } else {
                            System.out.println(">> Error al procesar la devolución.");
                        }
                        break;

                    case 3:
                        System.out.println("\n¡Gracias por usar DigiLibrary! Cerrando sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese solo números válidos.");
                scanner.next(); // Limpiar el error del scanner
            }
            
        } while (opcion != 3);

        scanner.close();
    }
}
