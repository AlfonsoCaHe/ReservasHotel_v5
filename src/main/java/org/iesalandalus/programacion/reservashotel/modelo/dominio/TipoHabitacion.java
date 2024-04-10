package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SUITE("SUITE"),
    SIMPLE("SIMPLE"),
    DOBLE("DOBLE"),
    TRIPLE("TRIPLE");

    private String cadenaAMostrar;

    private TipoHabitacion(String cadenaAMostrar){
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String toString(){
        return cadenaAMostrar;
    }
}