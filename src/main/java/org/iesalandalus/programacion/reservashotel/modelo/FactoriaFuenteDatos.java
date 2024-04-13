package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuentesDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.FuenteDatosMongoDB;

public enum FactoriaFuenteDatos {
    MEMORIA{
        public IFuentesDatos crear(){
            return new FuenteDatosMemoria();
        }
    },
    MONGO_DB{
        public IFuentesDatos crear(){
            return new FuenteDatosMongoDB();
        }
    };

    public abstract IFuentesDatos crear();
}
