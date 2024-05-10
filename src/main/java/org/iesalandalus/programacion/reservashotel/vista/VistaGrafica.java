package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.vista.grafica.LanzadorVentanaPrincipal;

public class VistaGrafica extends Vista{

    private static VistaGrafica instancia;
    private Controlador controlador;

    /*
     * Aunque en el diagrama que acompaña al enunciado de la tarea el método constructor aparece como público, el patrón Singleton
     * requiere que sea de tipo privado
     */
    private VistaGrafica(){

    }

    public VistaGrafica getInstancia(){
        if(this.instancia == null) {
            this.instancia = new VistaGrafica();
        }
        return instancia;
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public Controlador getControlador() {
        return null;
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
