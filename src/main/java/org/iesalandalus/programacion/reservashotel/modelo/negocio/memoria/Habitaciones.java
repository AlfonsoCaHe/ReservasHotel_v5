package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Habitaciones implements IHabitaciones {

    private ArrayList<Habitacion> coleccionHabitaciones;

    /*Crea el constructor que creará una lista del tipo Habitacion*/
    public Habitaciones(){
        coleccionHabitaciones = new ArrayList<>();
    }

    /*Crea el método get que está sobrecargado y devolverá
    El método sin parámetros, una copia profunda de la colección haciendo uso del método copiaProfundaHabitaciones.
    */
    public ArrayList<Habitacion> get() {
        return copiaProfundaHabitaciones();
    }

    private ArrayList<Habitacion> copiaProfundaHabitaciones(){
        ArrayList<Habitacion> copiaHabitaciones = new ArrayList<>();

        for(Habitacion h : coleccionHabitaciones){
            if(h instanceof Simple)
                copiaHabitaciones.add(new Simple((Simple)h));
            if(h instanceof Doble)
                copiaHabitaciones.add(new Doble((Doble)h));
            if(h instanceof Triple)
                copiaHabitaciones.add(new Triple((Triple)h));
            if(h instanceof Suite)
                copiaHabitaciones.add(new Suite((Suite)h));
        }

        return copiaHabitaciones;
    }

    /*
    El método con el parámetro TipoHabitacion, un copia profunda de la colección pero de solo aquellas habitaciones cuyo tipo sea
    el indicado como parámetro.
     */
    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion) {
        ArrayList<Habitacion> copiaHabitaciones = new ArrayList<>();

        for(Habitacion h : coleccionHabitaciones){
            if(h instanceof Simple){
                if(tipoHabitacion.toString().toUpperCase().equals("SIMPLE"))
                    copiaHabitaciones.add(new Simple((Simple)h));
            }
            if(h instanceof Doble){
                if(tipoHabitacion.toString().toUpperCase().equals("DOBLE"))
                    copiaHabitaciones.add(new Doble((Doble)h));
            }
            if(h instanceof Triple){
                if(tipoHabitacion.toString().toUpperCase().equals("TRIPLE"))
                    copiaHabitaciones.add(new Triple((Triple)h));
            }
            if(h instanceof Suite){
                if(tipoHabitacion.toString().toUpperCase().equals("SUITE"))
                    copiaHabitaciones.add(new Suite((Suite)h));
            }
        }

        return copiaHabitaciones;
    }

    /*Se permitirán insertar habitaciones no nulas al final de la colección sin admitir repetidos.*/
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        try{
            if(habitacion != null) {
                if(!coleccionHabitaciones.contains(habitacion)) {
                    coleccionHabitaciones.add(habitacion);//Insertamos al final del array
                }else{
                    throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
                }
            }else{
                throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
    }

    /*El método buscar devolverá una habitación si ésta se encuentra en la colección y null en caso contrario.*/
    public Habitacion buscar(Habitacion habitacion){
        if(habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        Habitacion habitacionEncontrada = null;
        if(coleccionHabitaciones.contains(habitacion)) {//Si no se encuentra índice, es que la habitacion no se encuentra dentro del array
            habitacionEncontrada = coleccionHabitaciones.get(coleccionHabitaciones.indexOf(habitacion));
        }
        return habitacionEncontrada;
    }

    /*El método borrar, si la habitación se encuentra en la colección, la borrará y desplazará los elementos hacia la izquierda para dejar el array compactado.*/
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        if(habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if(coleccionHabitaciones.contains(habitacion)){
            coleccionHabitaciones.remove(habitacion);
        }else{
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }

    public int getTamano(){
        return coleccionHabitaciones.size();
    }
}