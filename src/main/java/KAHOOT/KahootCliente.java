package KAHOOT;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class KahootCliente {

    public static void main(String[] args) {
        try {
            // Conectar al servidor Kahoot
            Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Participar en la sesión del juego
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Recibir pregunta del servidor
                Pregunta pregunta = (Pregunta) in.readObject();
                System.out.println("Pregunta: " + pregunta.getEnunciado());

                // Mostrar opciones de respuesta al usuario
                for (Respuesta respuesta : pregunta.getRespuestas()) {
                    System.out.println(respuesta.getOpcion() + ". " + respuesta.getTexto());
                }

                // Obtener la respuesta del usuario
                System.out.print("Tu respuesta: ");
                String respuestaUsuario = scanner.nextLine();

                // Enviar la respuesta al servidor
                Respuesta respuesta = new Respuesta(respuestaUsuario, "");
                out.writeObject(respuesta);
                out.flush();

                // Recibir feedback del servidor
                String feedback = (String) in.readObject();
                System.out.println("Feedback: " + feedback);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
