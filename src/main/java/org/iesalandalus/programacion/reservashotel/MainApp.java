package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

    public class MainApp {
        private static Vista vista;
        private static Modelo modelo;
        private static Controlador controlador;

        /*Crea el método main que nos mostrará el menú de la aplicación, nos pedirá una opción y la ejecutará mientras no elijamos
        la opción salir. En caso de salir, la aplicación mostrará un mensaje de despedida.*/
        public static void main(String[] args) {
            System.out.println("Programa para la Gestión de Hoteles IES Al-Ándalus");
            vista = new Vista();
            modelo = new Modelo();
            controlador = new Controlador(modelo, vista);
            controlador.comenzar();
            controlador.terminar();
        }
    }