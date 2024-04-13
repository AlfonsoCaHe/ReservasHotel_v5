package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IModelo {
    public abstract void comenzar();

    public abstract void terminar();

    public abstract void insertar(Huesped huesped) throws OperationNotSupportedException;
    public abstract Huesped buscar(Huesped huesped);
    public abstract void borrar(Huesped huesped) throws OperationNotSupportedException;
    public abstract ArrayList<Huesped> getHuespedes();

    public abstract void insertar(Habitacion habitacion) throws OperationNotSupportedException;
    public abstract Habitacion buscar(Habitacion habitacion);
    public abstract void borrar(Habitacion habitacion) throws OperationNotSupportedException;
    public abstract ArrayList<Habitacion> getHabitaciones();
    public abstract ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion);

    public abstract void insertar(Reserva reserva) throws OperationNotSupportedException;
    public abstract Reserva buscar(Reserva reserva);
    public abstract void borrar(Reserva reserva) throws OperationNotSupportedException;
    public abstract ArrayList<Reserva> getReservas();
    public abstract ArrayList<Reserva> getReservas(Huesped huesped);
    public abstract ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion);
    public abstract ArrayList<Reserva> getReservas(Habitacion habitacion);
    public abstract ArrayList<Reserva> getReservasFuturas(Habitacion habitacion);
    public abstract void realizarCheckIn(Reserva reserva, LocalDateTime fecha);
    public abstract void realizarCheckOut(Reserva reserva, LocalDateTime fecha);


}
