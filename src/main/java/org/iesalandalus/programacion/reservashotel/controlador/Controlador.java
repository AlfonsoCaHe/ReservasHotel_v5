package org.iesalandalus.programacion.reservashotel.controlador;

import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Controlador {
    private IModelo modelo;
    private Vista vista;

    public Controlador(IModelo modelo, Vista vista) {
        try {
            if (modelo == null || vista == null) {
                throw new NullPointerException("ERROR: El modelo y la vista no pueden ser nulos.");
            }
            this.modelo = modelo;
            this.vista = vista;
            vista.setControlador(this);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        try {
            modelo.insertar(huesped);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Huesped buscar(Huesped huesped) {
        Huesped h = null;
        try {
            h = modelo.buscar(huesped);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return h;
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        try {
            modelo.borrar(huesped);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public ArrayList<Huesped> getHuespedes() {
        return modelo.getHuespedes();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        try {
            modelo.insertar(habitacion);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (OperationNotSupportedException e) {
            throw new OperationNotSupportedException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Habitacion buscar(Habitacion habitacion) {
        Habitacion h = null;
        try {
            h = modelo.buscar(habitacion);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return h;
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        try {
            modelo.borrar(habitacion);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (OperationNotSupportedException e) {
            throw new OperationNotSupportedException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public ArrayList<Habitacion> getHabitaciones() {
        return modelo.getHabitaciones();
    }

    public ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) {
        ArrayList<Habitacion> h = null;
        try {
            h = modelo.getHabitaciones(tipoHabitacion);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return h;
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        try {
            modelo.insertar(reserva);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (OperationNotSupportedException e) {
            throw new OperationNotSupportedException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Reserva buscar(Reserva reserva) {
        Reserva r = null;
        try {
            r = modelo.buscar(reserva);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        try {
            modelo.borrar(reserva);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    /*Crea el método realizarCheckin, que una vez localizada la reserva a la que realizar el checkin, deberá llamar al método
    correspondiente del controlador. Para localizar la reserva deberá preguntarse por el huesped de la misma, obtener su lista
    de reservas y establecer la fecha y hora de checkin de la reserva correspondiente. Hay que tener en cuenta que un huesped
    puede haber realizado varias reservas para el mismo día. Además, en caso de intentar hacer checkin de una reserva no
    existente en el día indicado para el huésped, la aplicación deberá informar con algún mensaje de lo sucedido.*/
    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha) {
        try {
            modelo.realizarCheckIn(reserva, fecha);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha) {
        try {
            modelo.realizarCheckOut(reserva, fecha);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public ArrayList<Reserva> getReservas() {
        return modelo.getReservas();
    }

    public ArrayList<Reserva> getReservas(Huesped huesped) {
        ArrayList<Reserva> r = null;
        try {
            r = modelo.getReservas(huesped);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        ArrayList<Reserva> r = null;
        try {
            r = modelo.getReservas(tipoHabitacion);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public ArrayList<Reserva> getReservas(Habitacion habitacion) {
        ArrayList<Reserva> r = null;
        try {
            r = modelo.getReservas(habitacion);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion) {
        ArrayList<Reserva> r = null;
        try {
            r = modelo.getReservasFuturas(habitacion);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }
}