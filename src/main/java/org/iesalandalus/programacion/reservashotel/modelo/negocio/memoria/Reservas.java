package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Reservas implements IReservas {
    private ArrayList<Reserva> coleccionReservas;

    /*Crea el constructor con parámetros que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos de la clase a los valores correspondientes.*/
    public Reservas() {
        coleccionReservas = new ArrayList<>();
    }

    /*Crea el método get que devolverá una copia profunda de la colección haciendo uso del método copiaProfundaReservas.*/
    public ArrayList<Reserva> get() {
        return copiaProfundaReservas();
    }

    private ArrayList<Reserva> copiaProfundaReservas() {
        ArrayList<Reserva> copiaReservas = new ArrayList<>();
        Iterator it = coleccionReservas.iterator();
        while(it.hasNext()){
            copiaReservas.add(new Reserva((Reserva) it.next()));
        }
        return copiaReservas;
    }

    public int getTamano() {
        return coleccionReservas.size();
    }

    /*Se permitirán insertar reservas no nulas al final de la colección sin admitir repetidos.*/
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        try {
            if (reserva != null) {
                if(!coleccionReservas.contains(reserva)) {//El resultado será -1 si no se encuentra ya incluida la reserva
                    coleccionReservas.add(reserva);
                }else{
                    throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
                }
            } else {
                throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
    }

    private int buscarIndice(Reserva reserva) {
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");

        return coleccionReservas.indexOf(reserva);
    }

    /*El método buscar devolverá una reserva si ésta se encuentra en la colección y null en caso contrario.*/
    public Reserva buscar(Reserva reserva) {
        if(reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");

        Reserva reservaEncontrada = null;

        if(coleccionReservas.contains(reserva)){
            reservaEncontrada = coleccionReservas.get(coleccionReservas.indexOf(reserva));
        }
        return reservaEncontrada;
    }

    /*El método borrar, si la reserva se encuentra en la colección, la borrará y desplazará los elementos hacia la izquierda
    para dejar el array compactado.
    */
    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        try {
            if(reserva != null) {
                if(coleccionReservas.contains(reserva)){//Si la reserva está contenida en la colección
                    coleccionReservas.remove(reserva);
                }else {
                    throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
                }
            }else{
                throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }

    }

    /*El método getReservas que está sobrecargado y devolverá una colección de las reservas realizadas por el huésped pasado
    por parámetro o una colección de las reservas realizadas para el tipo de habitación indicada como parámetro.
    */
    public ArrayList<Reserva> getReservas(Huesped huesped) {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }

        ArrayList<Reserva> copiaReservas = new ArrayList<>();
        Iterator it = coleccionReservas.iterator();

        while(it.hasNext()){
            Reserva r = (Reserva) it.next();
            if(r.getHuesped().equals(huesped)){
                copiaReservas.add(r);
            }
        }
        return copiaReservas;
    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        ArrayList<Reserva> copiaReservas = new ArrayList<>();
        try{
            if(tipoHabitacion == null) {
                throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
            }

            for(Reserva r : coleccionReservas){
                if(r.getHabitacion() instanceof Simple){
                    if(tipoHabitacion.toString().toUpperCase().equals("SIMPLE"))
                        copiaReservas.add(r);
                }
                if(r.getHabitacion() instanceof Doble){
                    if(tipoHabitacion.toString().toUpperCase().equals("DOBLE"))
                        copiaReservas.add(r);
                }
                if(r.getHabitacion() instanceof Triple){
                    if(tipoHabitacion.toString().toUpperCase().equals("TRIPLE"))
                        copiaReservas.add(r);
                }
                if(r.getHabitacion() instanceof Suite){
                    if(tipoHabitacion.toString().toUpperCase().equals("SUITE"))
                        copiaReservas.add(r);
                }
            }

        }catch(NullPointerException e) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        return copiaReservas;
    }

    public ArrayList<Reserva> getReservas(Habitacion habitacion) {
        ArrayList<Reserva> copiaReservas = new ArrayList<>();
        try{
            if(habitacion == null) {
                throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
            }

            TipoHabitacion tipoHabitacion = null;
            if (habitacion instanceof Simple)
                tipoHabitacion = TipoHabitacion.SIMPLE;
            if(habitacion instanceof Doble)
                tipoHabitacion = TipoHabitacion.DOBLE;
            if(habitacion instanceof Triple)
                tipoHabitacion = TipoHabitacion.TRIPLE;
            if(habitacion instanceof Suite)
                tipoHabitacion = TipoHabitacion.SUITE;

            for(Reserva r : coleccionReservas){
                if(r.getHabitacion() instanceof Simple){
                    if(tipoHabitacion.toString().toUpperCase().equals("SIMPLE"))
                        copiaReservas.add(r);
                }
                if(r.getHabitacion() instanceof Doble){
                    if(tipoHabitacion.toString().toUpperCase().equals("DOBLE"))
                        copiaReservas.add(r);
                }
                if(r.getHabitacion() instanceof Triple){
                    if(tipoHabitacion.toString().toUpperCase().equals("TRIPLE"))
                        copiaReservas.add(r);
                }
                if(r.getHabitacion() instanceof Suite){
                    if(tipoHabitacion.toString().toUpperCase().equals("SUITE"))
                        copiaReservas.add(r);
                }
            }

        }catch(NullPointerException e) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        return copiaReservas;
    }


    /*El método getReservasFuturas que devolverá una colección de las reservas realizadas para la habitación indicada como
    parámetro y que sean posteriores a la fecha de hoy.
    */
    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion) {
        if(habitacion == null) {
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }

        ArrayList<Reserva> copiaReservas = new ArrayList<>();
        Iterator it = coleccionReservas.iterator();

        while(it.hasNext()){
            Reserva r = (Reserva) it.next();
            if(r.getHabitacion().equals(habitacion) && r.getFechaInicioReserva().atStartOfDay().isAfter(LocalDateTime.now())){
                copiaReservas.add(new Reserva(r));
            }
        }
        return copiaReservas;
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) {
        try{
            int indice = buscarIndice(reserva);
            coleccionReservas.get(indice).setCheckIn(fecha);
        }catch(NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha) {
        try {
            int indice = buscarIndice(reserva);
            coleccionReservas.get(indice).setCheckOut(fecha);
        }catch(NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}