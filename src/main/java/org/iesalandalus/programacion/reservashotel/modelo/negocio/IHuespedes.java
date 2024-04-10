package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public interface IHuespedes {
    public abstract ArrayList<Huesped> get();

    public abstract int getTamano();

    public abstract void insertar(Huesped huesped) throws OperationNotSupportedException;

    public abstract Huesped buscar(Huesped huesped);

    public abstract void borrar(Huesped huesped) throws OperationNotSupportedException;

    public abstract void comenzar();

    public abstract void terminar();
}