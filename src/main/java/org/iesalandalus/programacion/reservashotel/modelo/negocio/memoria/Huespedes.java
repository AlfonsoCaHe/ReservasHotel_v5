package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;

public class Huespedes implements IHuespedes {

    private ArrayList<Huesped> coleccionHuespedes;

    /*Crea el constructor con parámetros que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos
    de la clase a los valores correspondientes.
    */
    public Huespedes(){
        coleccionHuespedes = new ArrayList<>();

    }

    /*El método get devolverá una copia profunda de la colección haciendo uso del método copiaProfundaHuespedes.*/
    public ArrayList<Huesped> get(){
        return copiaProfundaHuespedes();
    }

    private ArrayList<Huesped> copiaProfundaHuespedes(){
        ArrayList<Huesped> copiaHuespedes = new ArrayList<>();

        Iterator it = coleccionHuespedes.iterator();

        while(it.hasNext()){
            copiaHuespedes.add(new Huesped((Huesped) it.next()));
        }
        return copiaHuespedes;
    }

    public int getTamano(){
        return coleccionHuespedes.size();
    }

    /*Se permitirán insertar huéspedes no nulos al final de la colección sin admitir repetidos.*/
    public void insertar(Huesped huesped)throws OperationNotSupportedException{
        try{
            if(huesped != null) {
                if (!coleccionHuespedes.contains(huesped)) {
                    coleccionHuespedes.add(huesped);
                } else {
                    throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
                }
            }else{
                throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
    }

    private int buscarIndice(Huesped huesped){
        if(huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        return coleccionHuespedes.indexOf(huesped);
    }

    /*El método buscar devolverá un huésped si éste se encuentra en la colección y null en caso contrario.*/
    public Huesped buscar(Huesped huesped){
        if(huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        Huesped huespedEncontrado = null;
        if(coleccionHuespedes.contains(huesped))
            huespedEncontrado = coleccionHuespedes.get(coleccionHuespedes.indexOf(huesped));
        return huespedEncontrado;
    }

    /*El método borrar, si el huésped se encuentra en la colección, lo borrará y desplazará los elementos hacia la izquierda para
    dejar el array compactado.*/
    public void borrar(Huesped huesped)throws OperationNotSupportedException{
        try{
            if(huesped == null){
                throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
            }
            if(coleccionHuespedes.contains(huesped)){
                coleccionHuespedes.remove(huesped);
            }else{
                throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
    }

    @Override
    public void comenzar() {

    }

    @Override
    public void terminar() {

    }
}