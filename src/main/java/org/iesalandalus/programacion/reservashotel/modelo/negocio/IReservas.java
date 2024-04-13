package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IReservas {

    public abstract ArrayList<Reserva> get();

    public abstract int getTamano();

    public abstract void insertar(Reserva reserva) throws OperationNotSupportedException;

    public abstract Reserva buscar(Reserva reserva);

    public abstract void borrar(Reserva reserva) throws OperationNotSupportedException;

    public abstract ArrayList<Reserva> getReservas(Huesped hueped);

    public abstract ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion);

    public abstract ArrayList<Reserva> getReservas(Habitacion habitacion);

    public abstract ArrayList<Reserva> getReservasFuturas(Habitacion habitacion);

    public abstract void realizarCheckin(Reserva reserva, LocalDateTime fecha);

    public abstract void realizarCheckout(Reserva reserva, LocalDateTime fecha);

    public abstract void comenzar();

    public abstract void terminar();
}
