package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Reservas implements IReservas {
    private MongoCollection<Document> coleccionReservas;

    //Atributo que guarda el nombre de la colección de la base de datos
    private String COLECCION = "reservas";

    /*Crea el constructor con parámetros que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos de la clase a los valores correspondientes.*/
    public Reservas() {
        comenzar();
    }

    /*Crea el método get que devolverá una copia profunda de la colección haciendo uso del método copiaProfundaReservas.*/
    public ArrayList<Reserva> get() {
        ArrayList<Reserva> copiaReservas = new ArrayList<Reserva>();

        MongoCursor<Document> cursor = coleccionReservas.find().iterator();

        while(cursor.hasNext()){
            copiaReservas.add(MongoDB.getReserva(cursor.next()));
        }

        Collections.sort(copiaReservas);
        return copiaReservas;
    }

    public int getTamano() {
        return Integer.parseInt(""+MongoDB.getBD().getCollection(COLECCION).countDocuments());
    }

    /*Se permitirán insertar reservas no nulas al final de la colección sin admitir repetidos.*/
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        try {
            if (reserva != null) {
                if (buscar(reserva) == null) {
                    coleccionReservas.insertOne(MongoDB.getDocumento(reserva));
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

    /*El método buscar devolverá una reserva si ésta se encuentra en la colección y null en caso contrario.*/
    public Reserva buscar(Reserva reserva) {
        if(reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");

        Reserva reservaEncontrada = null;

        MongoCursor<Document> cursor = coleccionReservas.find().iterator();

        while (cursor.hasNext()) {
            Reserva r = MongoDB.getReserva(cursor.next());
            if (r.equals(reserva)) {
                reservaEncontrada = r;
            }
        }
        return reservaEncontrada;
    }

    /*El método borrar, si la reserva se encuentra en la colección, la borrará y desplazará los elementos hacia la izquierda
    para dejar el array compactado.
    */
    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        try {
            if(reserva != null) {
                Reserva r = buscar(reserva);
                if(r != null){
                    coleccionReservas.deleteOne(MongoDB.getDocumento(reserva));
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
        ArrayList<Reserva> copia = get();
        Iterator it = copia.iterator();

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
        ArrayList<Reserva> copia = get();
        try{
            if(tipoHabitacion == null) {
                throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
            }

            for(Reserva r : copia){
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
        ArrayList<Reserva> copia = get();
        Iterator it = copia.iterator();

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
            Reserva r = new Reserva(reserva);
            r.setCheckIn(fecha);
            coleccionReservas.findOneAndReplace(MongoDB.getDocumento(reserva), MongoDB.getDocumento(r));
        }catch(NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void realizarCheckout(Reserva reserva, LocalDateTime fecha) {
        try {
            Reserva r = new Reserva(reserva);
            r.setCheckOut(fecha);
            coleccionReservas.findOneAndReplace(MongoDB.getDocumento(reserva), MongoDB.getDocumento(r));
        }catch(NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void comenzar() {
        try {
            coleccionReservas = MongoDB.getBD().getCollection(COLECCION);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }
}