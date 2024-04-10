package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public interface IHabitaciones {

    public abstract ArrayList<Habitacion> get();

    public abstract ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion);

    public abstract int getTamano();

    public abstract void insertar(Habitacion habitacion) throws OperationNotSupportedException;

    public abstract Habitacion buscar(Habitacion habitacion);

    public abstract void borrar(Habitacion habitacion)throws OperationNotSupportedException;

    public abstract void comenzar();

    public abstract void terminar();
}