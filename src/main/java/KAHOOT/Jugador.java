package KAHOOT;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class Jugador implements Runnable {
    private Socket clienteSocket;
    private int preguntaActual;
    private List<Pregunta> preguntas;

    public Jugador(Socket clienteSocket, int preguntaActual, List<Pregunta> preguntas) {
        this.clienteSocket = clienteSocket;
        this.preguntaActual = preguntaActual;
        this.preguntas = preguntas;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clienteSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clienteSocket.getInputStream())
        ) {
            // Lógica de comunicación con el cliente
            for (int i = preguntaActual; i < preguntas.size(); i++) {
                Pregunta pregunta = preguntas.get(i);
                out.writeObject(pregunta);
                out.flush();

                // Recibir respuesta del cliente
                Respuesta respuestaCliente = (Respuesta) in.readObject();

                // Comparar respuesta con la correcta y enviar feedback al cliente
                if (respuestaCliente.getOpcion().equals(pregunta.getRespuestaCorrecta())) {
                    out.writeObject("Correcto");
                } else {
                    out.writeObject("Incorrecto");
                }
                out.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clienteSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
