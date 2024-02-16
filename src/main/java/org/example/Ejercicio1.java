package org.example;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Ejercicio1 {
    private static final String HOST = "localhost";
    private static final int PUERTO = 21;

    public void archivo() {
        try {
            String usuario = pedirDatos("Introduce el nombre de usuario:");
            String contrasena = pedirDatos("Introduce la contrasena:");


            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(HOST, PUERTO);
            boolean loginSuccess = ftpClient.login(usuario, contrasena);

            if (loginSuccess) {
                JFileChooser fileChooser = new JFileChooser();
                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    FileInputStream inputStream = new FileInputStream(file);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    boolean uploaded = ftpClient.storeFile(file.getName(), inputStream);
                    inputStream.close();

                    if (uploaded) {
                        System.out.println("Fichero subido con éxito.");
                    } else {
                        System.out.println("Error al subir el fichero.");
                    }
                    String[] files = ftpClient.listNames();
                    System.out.println("Contenido del directorio raíz:");
                    for (String fileName : files) {
                        System.out.println(fileName);
                    }
                } else {
                    System.out.println("Operación cancelada por el usuario.");
                }

                ftpClient.logout();
                ftpClient.disconnect();
            } else {
                System.out.println("Error de autenticación. Inténtalo de nuevo.");
            }

            archivo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String pedirDatos(String mensaje) {
        System.out.print(mensaje + " ");
        return new Scanner(System.in).nextLine();
    }

    public static void main(String[] args) {
        Ejercicio1 ejercicio1 = new Ejercicio1();
        ejercicio1.archivo();
    }
}

