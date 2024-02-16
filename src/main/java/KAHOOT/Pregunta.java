package KAHOOT;

import java.io.Serializable;
import java.util.List;

public class Pregunta implements Serializable {
    private int id;
    private String tema;
    private String enunciado;
    private List<Respuesta> respuestas;
    private String respuestaCorrecta;

    public Pregunta(int id, String tema, String enunciado, List<Respuesta> respuestas, String respuestaCorrecta) {
        this.id = id;
        this.tema = tema;
        this.enunciado = enunciado;
        this.respuestas = respuestas;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}
