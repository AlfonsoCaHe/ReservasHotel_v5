package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.FactoriaVista;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class MainApp {
        private static Vista vista;
        private static IModelo modelo;
        private static Controlador controlador;

        /*Crea el método main que nos mostrar� el men� de la aplicaci�n, nos pedir� una opci�n y la ejecutar� mientras no elijamos
        la opción salir. En caso de salir, la aplicaci�n mostrar� un mensaje de despedida.*/
        public static void main(String[] args) {
            System.out.println("Programa para la Gestión de Hoteles IES Al-ándalus");
            if(args.length < 1){
                System.out.println("No se han introducido parámetros de inicio. Inicio por defecto modelo MongoDB-Gráfico.");
                procesarArgumentosFuentesDatos("-fdmongodb");
                procesarArgumentosFuentesGraficas("-vGrafica");
            }else{
                procesarArgumentosFuentesDatos(args[0]);
                procesarArgumentosFuentesGraficas(args[1]);
            }
            controlador = new Controlador(modelo, vista);
            controlador.comenzar();
            controlador.terminar();
        }

        private static void procesarArgumentosFuentesGraficas(String args) {
            switch (args){
                case "-vGrafica":
                    vista = FactoriaVista.GRAFICA.crear();
                    break;
                case "-vTexto":
                    vista = FactoriaVista.TEXTO.crear();
                    break;
                default:
                    System.out.println("ERROR: No se ha introducido una vista válida.");
                    System.out.println("Iniciamos vista GRAFICA");
                    vista = FactoriaVista.GRAFICA.crear();
            }
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
                    System.out.println("Iniciamos modelo MONGODB");
                    modelo = new Modelo(FactoriaFuenteDatos.MONGODB.crear());
            }
        }
    }