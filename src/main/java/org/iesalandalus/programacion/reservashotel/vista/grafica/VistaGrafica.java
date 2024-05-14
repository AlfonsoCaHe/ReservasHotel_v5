package org.iesalandalus.programacion.reservashotel.vista.grafica;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class VistaGrafica extends Vista {

    private static VistaGrafica instancia;
    private Controlador controlador;

    /*
     * Aunque en el diagrama que acompaña al enunciado de la tarea el método constructor aparece como público, el patrón Singleton
     * requiere que sea de tipo privado
     */
    public VistaGrafica(){

    }

    public static VistaGrafica getInstancia(){
        if(instancia == null) {
            instancia = new VistaGrafica();
        }
        return instancia;
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public Controlador getControlador() {
        return this.controlador;
    }

    @Override
    public void comenzar() {
        LanzadorVentanaPrincipal.comenzar();
    }

    @Override
    public void terminar() {
        controlador.terminar();
    }
}
