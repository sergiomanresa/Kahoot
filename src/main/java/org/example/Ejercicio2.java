package org.example;

import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Ejercicio2 {

    public static void Ejercicio2(){

         final String HOST = "localhost";
         final int PUERTO = 21;

        FTPClient cliente=new FTPClient();
        String usuario, contrasena;
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Introduce tu usuario:");
            usuario = sc.nextLine();
            if(usuario.equals("*") || usuario.isEmpty()) {
                System.out.println("Venga a programar un crud en java");
                return ;
            }
            System.out.println("Introduce la contraseña:");
            contrasena = sc.nextLine();
            cliente.connect(HOST, PUERTO);

            if(!cliente.login(usuario, contrasena)) {
                System.out.println("En ocasiones los humanos nos equivocamos ,ahora te a tocado a ti ser subnormal");
            } else{
                cliente.enterLocalPassiveMode();
                if(cliente.changeWorkingDirectory("/LOG")) {

                    OutputStream outputStream = cliente.appendFileStream("LOG.TXT");
                    if (outputStream != null) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                        writer.newLine();
                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        writer.write("Te has concetado a las : " + date);
                        writer.newLine();
                        writer.close();
                        outputStream.close();
                        System.out.println("Registro bueno, creo ,pero funciona eso es lo importante.");
                    } else {
                        System.out.println("Si les esto esque nose como arreglar algo suerte. LOG.TXT");
                    }
                }
            }
        }catch(Exception e){
            System.err.println("Mira ni tu ni yo sabemos porque este mensaje esta aqui asique corramos un tupido velo");
        }
    }
}
