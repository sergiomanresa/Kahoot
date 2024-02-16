package KAHOOT;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class KahootServer {
    private static final int PUERTO = 12345;
    private List<Pregunta> preguntas;
    private List<Jugador> jugadores = new CopyOnWriteArrayList<>();
    private int indexPreguntaActual = 0;

    public KahootServer(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public void iniciar() {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor Kahoot iniciado. Esperando conexiones...");

            while (true) {
                Socket clienteSocket = servidor.accept();
                System.out.println("Nuevo cliente conectado: " + clienteSocket);

                Jugador nuevoJugador = new Jugador(clienteSocket, indexPreguntaActual, preguntas);
                jugadores.add(nuevoJugador);

                // Cada jugador se manejará en un hilo separado
                Thread hiloJugador = new Thread(nuevoJugador);
                hiloJugador.start();

                indexPreguntaActual++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        List<Pregunta> preguntas = cargarPreguntasDesdeJSON();

        KahootServer servidor = new KahootServer(preguntas);
        servidor.iniciar();
    }

    private static List<Pregunta> cargarPreguntasDesdeJSON() {
        List<Pregunta> preguntas = new CopyOnWriteArrayList<>();

        try (Reader reader = new FileReader("java/KAHOOT/preguntas.json")) {
            JsonReader jsonReader = Json.createReader(reader);
            JsonObject jsonRoot = jsonReader.readObject();
            JsonArray jsonPreguntas = jsonRoot.getJsonArray("preguntas");

            for (int i = 0; i < jsonPreguntas.size(); i++) {
                JsonObject jsonPregunta = jsonPreguntas.getJsonObject(i);

                int id = jsonPregunta.getInt("id");
                String tema = jsonPregunta.getString("tema");
                String enunciado = jsonPregunta.getString("enunciado");
                String respuestaCorrecta = jsonPregunta.getString("respuesta_correcta");

                JsonArray jsonRespuestas = jsonPregunta.getJsonArray("respuestas");
                List<Respuesta> respuestas = new CopyOnWriteArrayList<>();

                for (int j = 0; j < jsonRespuestas.size(); j++) {
                    JsonObject jsonRespuesta = jsonRespuestas.getJsonObject(j);
                    String opcion = jsonRespuesta.getString("opcion");
                    String texto = jsonRespuesta.getString("texto");
                    respuestas.add(new Respuesta(opcion, texto));
                }

                preguntas.add(new Pregunta(id, tema, enunciado, respuestas, respuestaCorrecta));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return preguntas;
    }

    private class Jugador implements Runnable {
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
                // Envío de preguntas al cliente
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
}