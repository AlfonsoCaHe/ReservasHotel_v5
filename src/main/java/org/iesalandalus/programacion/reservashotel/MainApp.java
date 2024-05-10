package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

    public class MainApp {
        private static VistaTexto vistaTexto;
        private static IModelo modelo;
        private static Controlador controlador;

        /*Crea el método main que nos mostrará el menú de la aplicación, nos pedirá una opción y la ejecutará mientras no elijamos
        la opción salir. En caso de salir, la aplicación mostrará un mensaje de despedida.*/
        public static void main(String[] args) {
            System.out.println("Programa para la Gestión de Hoteles IES Al-Ándalus");
            if(args.length < 1){
                System.out.println("No se han introducido parámetros de inicio. Inicio por defecto modelo MongoDB.");
                procesarArgumentosFuentesDatos("-fdmongodb");
            }else{
                procesarArgumentosFuentesDatos(args[0]);
            }
            vistaTexto = new VistaTexto();
            controlador = new Controlador(modelo, vistaTexto);
            controlador.comenzar();
            controlador.terminar();
        }

        private static void procesarArgumentosFuentesDatos(String args){
            switch (args){
                case "-fdmongodb":
                    modelo = new Modelo(FactoriaFuenteDatos.MONGODB.crear());
                    break;
                case "-fdmemoria":
                    modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crear());
                    break;
                default:
                    System.out.println("ERROR: No se ha introducido un modelo válido.");
            }
        }
    }