package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;

public class Huespedes implements IHuespedes {

    private MongoCollection<Document> coleccionHuespedes;

    //Atributo que guarda el nombre de la colección de la base de datos
    private String COLECCION = "huespedes";

    /*Crea el constructor con parámetros que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos
    de la clase a los valores correspondientes.
    */
    public Huespedes(){
        comenzar();
    }

    /*El método get devolverá una copia profunda de los Huéspedes ordenados por DNI de la colección haciendo uso del método copiaProfundaHuespedes.*/
    public ArrayList<Huesped> get(){
        ArrayList<Huesped> copiahuespedes = new ArrayList<Huesped>();

        MongoCursor<Document> cursor = coleccionHuespedes.find().iterator();

        while(cursor.hasNext()){
            copiahuespedes.add(MongoDB.getHuesped(cursor.next()));
        }

        Collections.sort(copiahuespedes);
        return copiahuespedes;
    }

    public int getTamano(){
        return Integer.parseInt(""+MongoDB.getBD().getCollection(COLECCION).countDocuments());
    }

    /*Se permitirán insertar huéspedes no nulos al final de la colección sin admitir repetidos.*/
    public void insertar(Huesped huesped)throws OperationNotSupportedException{
        try{
            if(huesped != null) {
                if (buscar(huesped) == null) {
                    coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
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

    /*El método buscar devolverá un huésped si este se encuentra en la colección y null en caso contrario.*/
    public Huesped buscar(Huesped huesped){
        if(huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        Huesped huespedEncontrado = null;

        MongoCursor<Document> cursor = coleccionHuespedes.find().iterator();

        while (cursor.hasNext()) {
            Huesped h = MongoDB.getHuesped(cursor.next());
            if (h.equals(huesped)) {
                huespedEncontrado = h;
            }
        }

        return huespedEncontrado;
    }

    /*El método borrar, si el huésped se encuentra en la colección, lo borrará y desplazará los elementos hacia la izquierda para
    dejar el array compactado.*/
    public void borrar(Huesped huesped)throws OperationNotSupportedException{
        try{
            if(huesped == null){
                throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
            }
            Huesped h = buscar(huesped);
            if(h != null){
                coleccionHuespedes.deleteOne(MongoDB.getDocumento(huesped));
            }else{
                throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
            }
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
    }

    @Override
    public void comenzar() {
        try {
            coleccionHuespedes = MongoDB.getBD().getCollection(COLECCION);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }
}