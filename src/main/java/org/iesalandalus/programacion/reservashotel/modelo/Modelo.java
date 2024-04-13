package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuentesDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Modelo implements IModelo{

    private IHuespedes huespedes;
    private IHabitaciones habitaciones;
    private IReservas reservas;
    private IFuentesDatos fuentesDatos;

    public Modelo(IFuentesDatos factoriaFuenteDatos){
        setFuentesDatos(factoriaFuenteDatos);
        comenzar();
    }

    public void comenzar(){
        huespedes = fuentesDatos.crearHuespedes();
        habitaciones = fuentesDatos.crearHabitaciones();
        reservas = fuentesDatos.crearReservas();
    }

    public void terminar(){
        huespedes.terminar();
        habitaciones.terminar();
        reservas.terminar();
        System.out.println("************************************************************");
        System.out.println("\t\t\t\tEl modelo ha terminado.");
        System.out.println("************************************************************\n");
    }

    public void setFuentesDatos(IFuentesDatos factoriaFuenteDatos){
        if(fuentesDatos == null){
            throw new NullPointerException("ERROR: No se ha escogido un modelo de datos válido.");
        }
        this.fuentesDatos = factoriaFuenteDatos;
    }

    /*Crea los diferentes métodos insertar (para huesped, habitación y reserva).
    Crea los diferentes métodos buscar, cada uno de los cuales devolverá una nueva instancia del elemento encontrado si éste existe.
    Crea los diferentes métodos borrar (para huesped, habitación y reserva).
    Crea los diferentes métodos get, que deben devolver una nueva lista conteniendo nuevas instancias no una copia de los elementos.
    */
    public void insertar(Huesped huesped)throws OperationNotSupportedException{
        try{
            huespedes.insertar(huesped);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Huesped buscar(Huesped huesped){
        Huesped h;
        try{
            h = huespedes.buscar(huesped);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return h;
    }

    public void borrar(Huesped huesped)throws OperationNotSupportedException{
        try{
            huespedes.borrar(huesped);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public ArrayList<Huesped> getHuespedes(){
        return huespedes.get();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException{
        try{
            habitaciones.insertar(habitacion);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }catch(OperationNotSupportedException e){
            throw new OperationNotSupportedException(e.getMessage());
        }
    }

    public Habitacion buscar(Habitacion habitacion){
        Habitacion h;
        try {
            h = habitaciones.buscar(habitacion);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return h;
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException{
        try{
            habitaciones.borrar(habitacion);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }catch(OperationNotSupportedException e){
            throw new OperationNotSupportedException(e.getMessage());
        }
    }

    public ArrayList<Habitacion> getHabitaciones(){
        return habitaciones.get();
    }

    public ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion){
        return habitaciones.get(tipoHabitacion);
    }

    public void insertar(Reserva reserva)throws OperationNotSupportedException{
        try{
            reservas.insertar(reserva);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }catch(OperationNotSupportedException e){
            throw new OperationNotSupportedException(e.getMessage());
        }
    }

    public Reserva buscar(Reserva reserva){
        Reserva r;
        try{
            r = reservas.buscar(reserva);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        try{
            reservas.borrar(reserva);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public ArrayList<Reserva> getReservas(){
        return reservas.get();
    }

    public ArrayList<Reserva> getReservas(Huesped huesped){
        ArrayList<Reserva> r = new ArrayList<>();
        try{
            r = reservas.getReservas(huesped);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        ArrayList<Reserva> r;
        try{
            r = reservas.getReservas(tipoHabitacion);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public ArrayList<Reserva> getReservas(Habitacion habitacion){
        ArrayList<Reserva> r;
        try{
            r = reservas.getReservas(habitacion);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion){
        ArrayList<Reserva> r;
        try{
            r = reservas.getReservasFuturas(habitacion);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha){
        try{
            reservas.realizarCheckin(reserva, fecha);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        try{
            reservas.realizarCheckout(reserva, fecha);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }
}