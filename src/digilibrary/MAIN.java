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
            System.out.println("3. Agregar un Nuevo Libro (Prueba Java)");
            System.out.println("4. Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
                
                switch (opcion) {
                    case 1:
                        System.out.println("\n[REGISTRAR ALQUILER]");
                        System.out.print("Ingrese el ID del Socio: ");
                        int idSocio = scanner.nextInt();
                        System.out.print("Ingrese el Código del Ejemplar: ");
                        int codEjemplar = scanner.nextInt();
                        
                        boolean alquilerOk = gestion.registrarAlquiler(idSocio, codEjemplar);
                        if (alquilerOk) {
                            System.out.println(">> Operación realizada con éxito.");
                        } else {
                            System.out.println(">> Error al procesar el alquiler.");
                        }
                        break;

                    case 2:
                        GestionBiblioteca.registrarDevolucion(scanner);
                        break;

                    case 3:
                        System.out.println("\n[AGREGAR NUEVO LIBRO]");
                        System.out.print("Ingrese el Código del Libro (ej. 201): ");
                        String nuevoCodLibro = scanner.nextLine();
                        System.out.print("Ingrese el Título del Libro: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Ingrese el ID del Autor (ej. A001): ");
                        String idAutor = scanner.nextLine();
                        System.out.print("Ingrese el ID de la Editorial (ej. ED001): ");
                        String idEditorial = scanner.nextLine();
                        
                        
                        boolean libroOk = gestion.agregarLibro(nuevoCodLibro, titulo, idAutor, idEditorial);
                        
                        if (libroOk) {
                            System.out.println(">> ¡Libro agregado con éxito en la BD!");
                        } else {
                            System.out.println(">> Error al agregar el libro. Revisa que el ID del Autor exista.");
                        }
                        break;

                    case 4:
                        System.out.println("\n¡Gracias por usar DigiLibrary! Cerrando sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese solo datos válidos.");
                scanner.next(); 
            }
            
        } while (opcion != 4);

        scanner.close();
    }
}