package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public enum FactoriaVista {
    TEXTO{
        public VistaTexto crear(){
            return new VistaTexto();
        }
    },
    GRAFICA{
        public VistaGrafica crear(){
            return VistaGrafica.getInstancia();
        }
    };

    public abstract Vista crear();
}
