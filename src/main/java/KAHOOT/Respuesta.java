package KAHOOT;

import java.io.Serializable;

public class Respuesta implements Serializable {
    private String opcion;
    private String texto;


    public Respuesta(String opcion, String texto) {
        this.opcion = opcion;
        this.texto = texto;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
