package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("/--------MENU--------/");
            System.out.println("    1. EJERCICIO 1");
            System.out.println("    2. EJERCICIO 2");
            System.out.println("      3. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Ejercicio1 ejercicio1 = new Ejercicio1();
                    ejercicio1.archivo();
                    break;
                case 2:
                    Ejercicio2 ejercicio2 = new Ejercicio2();
                    ejercicio2.Ejercicio2();
                    break;
                case 3:
                    System.out.println("Saliendo de la aplicación.");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                    break;
            }

        } while (opcion != 3);
        scanner.close();
    }
}
