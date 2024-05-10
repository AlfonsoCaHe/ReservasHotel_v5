package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;

public abstract class Vista {
    private Controlador controlador;

    public abstract void setControlador(Controlador controlador);

    public abstract Controlador getControlador();

    public abstract void comenzar();

    public abstract void terminar();
}
