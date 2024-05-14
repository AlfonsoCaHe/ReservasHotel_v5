package org.iesalandalus.programacion.reservashotel.vista.texto;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class VistaTexto extends Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador){
        if(controlador != null){
            this.controlador = controlador;
            Opcion.setVista(this);
        }
        else{
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
    }

    public Controlador getControlador(){
        return this.controlador;
    }

    public void comenzar(){
        System.out.println("************************************************************");
        System.out.println("Bienvenido al sistema de gestión de nuestra cadena hotelera.");
        System.out.println("************************************************************\n");
        Opcion opcion = Opcion.SALIR;
        do {
            try {
                opcion = Consola.elegirOpcion();
                opcion.ejecutar();
                System.out.println("\n************************************************************\n");
            }catch(IllegalArgumentException | NullPointerException e){
                System.out.println("ERROR: Operación no permitida. "+e.getMessage());
            }
        } while (!opcion.toString().equals("0.- Salir"));
    }

    public void terminar(){
        System.out.println("\t\tFIN DE LA APLICACIÓN. Les esperamos pronto.\n");
        System.out.println("************************************************************");
    }

    public void realizarCheckin() {
        try{
            System.out.println("\n************************************************************");
            System.out.println("Realizar check in");
            System.out.println("************************************************************\n");

            System.out.print("Introduzca el dni del huésped: ");//Buscamos el huesped
            String cadena = Entrada.cadena();
            Huesped huespedReserva = Consola.leerClientePorDni(cadena);//Obtenemos la información del huesped
            System.out.println(" ");
            if (huespedReserva != null) {
                ArrayList<Reserva> reserva = controlador.getReservas(huespedReserva);//Obtenemos las reservas del huesped
                LocalDate fechaCheck = Consola.leerFecha();
                int horas, minutos;
                do {
                    System.out.print("Introduzca la hora: ");
                    horas = Entrada.entero();
                } while (horas < 0 || horas > 23);
                do {
                    System.out.print("Introduzca los minutos: ");
                    minutos = Entrada.entero();
                } while (minutos < 0 || minutos > 59);
                LocalTime horaCheck = LocalTime.of(horas, minutos, 0);
                LocalDateTime fecha = LocalDateTime.of(fechaCheck, horaCheck);

                Iterator it = reserva.iterator();

                boolean checkValido = false;//Validamos que se haya realizado el check in al menos 1 vez
                while (it.hasNext()) {
                    Reserva r = (Reserva) it.next();
                    if (r.getFechaInicioReserva().isEqual(fecha.toLocalDate())) {
                        controlador.realizarCheckIn(r, fecha);
                        checkValido = true;
                    }
                }

                if (!checkValido) {
                    System.out.println("\nNo se ha podido realizar el check in. El huésped no tiene reserva la fecha dada.");
                } else {
                    System.out.println("\nCheck In realizado correctamente.");
                }
            } else {
                System.out.println("\nNo existe un huésped con ese dni.");
            }
        }catch(IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    public void realizarCheckOut() {
        try{
            System.out.println("\n************************************************************");
            System.out.println("Realizar check out");
            System.out.println("************************************************************\n");

            System.out.print("Introduzca el dni del huesped: ");//Buscamos el huesped
            String cadena = Entrada.cadena();
            Huesped huespedReserva = Consola.leerClientePorDni(cadena);//Obtenemos la información del huesped
            System.out.println(" ");
            if (huespedReserva != null) {
                ArrayList<Reserva> reserva = controlador.getReservas(huespedReserva);//Obtenemos las reservas del huesped
                LocalDate fechaCheck = Consola.leerFecha();
                int horas, minutos;
                do {
                    System.out.print("Introduzca la hora: ");
                    horas = Entrada.entero();
                } while (horas < 0 || horas > 23);
                do {
                    System.out.print("Introduzca los minutos: ");
                    minutos = Entrada.entero();
                } while (minutos < 0 || minutos > 59);
                LocalTime horaCheck = LocalTime.of(horas, minutos, 0);
                LocalDateTime fecha = LocalDateTime.of(fechaCheck, horaCheck);

                boolean checkValido = false;//Validamos que se realice al menos 1 check out correctamente
                Iterator it = reserva.iterator();

                while (it.hasNext()) {
                    Reserva r = (Reserva) it.next();
                    if (r.getFechaFinReserva().isEqual(fecha.toLocalDate())) {
                        controlador.realizarCheckOut(r, fecha);
                        checkValido = true;
                    }
                }

                if (!checkValido) {
                    System.out.println("\nNo se ha podido realizar el check in. El hu?sped no tiene reserva la fecha dada.");
                } else {
                    System.out.println("\nCheck Out realizado correctamente.");
                }
            } else {
                System.out.println("\nNo existe un hu?sped con ese dni.");
            }
        }catch(IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /*Crea el método insertarHuesped que nos pedirá los datos de un huésped, haciendo uso de la clase Consola, y lo insertará en
    la colección correspondiente si es posible o informará del problema en caso contrario.*/
    public void insertarHuesped(){
        Huesped huesped = null;
        try{
            huesped = Consola.leerHuesped();
            controlador.insertar(huesped);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch(OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    /*Crea el método buscarHuesped que nos pedirá el dni de un huésped, haciendo uso de la clase Consola, mostrándonos a dicho
    huesped o informará de que no existe o informará del problema en caso acontecido.*/
    public void buscarHuesped(){
        try{
            if(!controlador.getHuespedes().isEmpty()){//Si el primer huesped no es vacío, es que existen huespedes en el array
                System.out.print("Introduzca el dni que desea buscar: ");
                String dni = Entrada.cadena();
                System.out.println(" ");
                Huesped huesped = Consola.leerClientePorDni(dni);
                boolean encontrado = false;
                ArrayList<Huesped> huespedesBusqueda = controlador.getHuespedes();

                if(huespedesBusqueda.contains(huesped)){
                    huesped = controlador.buscar(huesped);
                    encontrado = true;
                }

                if(encontrado) {
                    System.out.println(huesped.toString());
                } else {
                    System.out.println("No se ha encontrado un huesped con ese dni.");
                }
            }else{
                System.out.println("No existen registros de huéspedes.");
            }
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /*Crea el método borrarHuesped que nos pedirá el dni de un huesped, haciendo uso de la clase Consola, borrándolo si es
    posible o informará del problema en caso contrario.*/
    public void borrarHuesped(){
        try {
            if(!controlador.getHuespedes().isEmpty()) {
                System.out.print("Introduzca el dni que desea buscar: ");
                String dni = Entrada.cadena();
                System.out.println(" ");
                Huesped huesped = Consola.leerClientePorDni(dni);

                if (controlador.getHuespedes().contains(huesped)) {
                    huesped = controlador.buscar(huesped);
                    ArrayList<Reserva> reservasAnulables = new ArrayList<>();
                    if(!controlador.getReservas(huesped).isEmpty()) {
                        reservasAnulables = getReservasAnulables(controlador.getReservas(huesped));
                    }
                    if (reservasAnulables.isEmpty()) {//Si no quedan reservas de ese huesped
                        controlador.borrar(huesped);
                        System.out.println("Huesped borrado correctamente.");
                    } else {
                        System.out.println("No se puede borrar un huesped con reservas pendientes.");
                    }
                } else {
                    System.out.println("No se ha encontrado un huesped con ese dni.");
                }
            }else {
                System.out.println("No existen registros de huéspedes.");
            }
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }catch(OperationNotSupportedException e){
            System.out.println("ERROR: El huesped no ha sido borrado correctamente");
        }
    }

    /*Crea el método mostrarHuespedes que mostrará todos los huéspedes almacenados si es que hay o si no, nos informará de que
    no hay huéspedes.*/
    public void mostrarHuespedes(){
        if(controlador.getHuespedes().isEmpty()){
            System.out.println("No existen registros de huéspedes.");
        }else{
            ArrayList<Huesped> huespedesOrdenados = controlador.getHuespedes();
            Collections.sort(huespedesOrdenados);

            Iterator it = huespedesOrdenados.iterator();
            while(it.hasNext()){
                Huesped h = (Huesped) it.next();
                System.out.println(h.toString());
            }
        }
    }

    /*Crea el método insertarHabitacion que nos pedirá los datos de una habitación, haciendo uso de la clase Consola, y lo
    insertará en la colección correspondiente si es posible o informará del problema en caso contrario.*/
    public void insertarHabitacion(){
        Habitacion habitacion = null;
        try{
            habitacion = Consola.leerHabitacion();
            controlador.insertar(habitacion);
        }catch(IllegalArgumentException |OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    /*Crea el método buscarHabitacion que nos pedirá el n?mero de puerta y el n?mero de planta de una habitación, haciendo uso
    de la clase Consola, mostrándonos a dicha habitación o informará de que no existe o informará del problema en caso
    acontecido.*/
    public void buscarHabitacion(){
        try{
            if(!controlador.getHabitaciones().isEmpty()){//Si el primer huesped no es nulo, es que existen huespedes en el array
                System.out.print("Introduzca el número de planta: ");
                int plantaBuscada = Entrada.entero();
                System.out.println(" ");
                System.out.print("Introduzca el número de habitación: ");
                int puertaBuscada = Entrada.entero();
                System.out.println(" ");
                Habitacion habitacion = new Simple(plantaBuscada, puertaBuscada, Habitacion.MIN_PRECIO_HABITACION);
                boolean encontrado = false;
                ArrayList<Habitacion> habitacionesBusqueda = controlador.getHabitaciones();

                if(habitacionesBusqueda.contains(habitacion)){
                    habitacion = controlador.buscar(habitacion);
                    encontrado = true;
                }

                if(encontrado) {
                    System.out.println(habitacion.toString());
                } else {
                    System.out.println("No se ha encontrado una habitación con el identificador "+plantaBuscada+puertaBuscada+".");
                }
            }else{
                System.out.println("No existen registros de habitaciones.");
            }
        }catch(IllegalArgumentException e){
            System.out.println("ERROR NO ESPERADO. " + e.getMessage());
        }
    }

    /*Crea el método borrarHabitacion que nos pedirá el número de puerta y el número de planta de una habitación, haciendo uso
    de la clase Consola, borrándolo si es posible o informará del problema en caso contrario.*/
    public void borrarHabitacion(){
        try{
            if(!controlador.getHabitaciones().isEmpty()) {
                System.out.print("Introduzca el número de planta: ");
                int plantaBuscada = Entrada.entero();
                System.out.println(" ");
                System.out.print("Introduzca el número de habitación: ");
                int puertaBuscada = Entrada.entero();
                System.out.println(" ");
                Habitacion habitacion = new Simple(plantaBuscada, puertaBuscada, Habitacion.MIN_PRECIO_HABITACION);

                if (controlador.getHabitaciones().contains(habitacion)) {
                    habitacion = controlador.buscar(habitacion);
                    ArrayList<Reserva> reservasAnulables = getReservasAnulables(controlador.getReservasFuturas(habitacion));
                    if (reservasAnulables.isEmpty()) {//Si no quedan reservas de esa habitacion
                        controlador.borrar(habitacion);
                        System.out.println("Habitación borrada correctamente.");
                    } else {
                        System.out.println("No se puede borrar una habitación con reservas pendientes.");
                    }
                } else {
                    System.out.println("No se ha encontrado una habitación con el identificador " + plantaBuscada + puertaBuscada + ".");
                }
            }else {
                System.out.println("No existen registros de habitaciones.");
            }
        }catch(IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    /*Crea el método mostrarHabitaciones que mostrará todos las habitaciones almacenadas si es que hay o si no, nos informar?
    de que no hay habitaciones.*/
    public void mostrarHabitaciones(){
        ArrayList<Habitacion> habitaciones = controlador.getHabitaciones();

        if(habitaciones.isEmpty()){
            System.out.println("No existen registros de habitaciones.");
        }else{
            Collections.sort(habitaciones);
            Iterator it = habitaciones.iterator();

            while(it.hasNext()){
                Habitacion h = (Habitacion) it.next();
                System.out.println(h.toString());
            }
        }
    }

    /*Crea el método insertarReserva que nos pedirá los datos de una reserva, haciendo uso de la clase Consola, y lo insertar?
    en la colección correspondiente si es posible o informará del problema en caso contrario. Debe tenerse en cuenta que para
    poder insertar una reserva debe haber disponibilidad del tipo de habitación deseada por el huésped en el periodo indicado.*/
    public void insertarReserva(){
        try{
            TipoHabitacion t = Consola.leerTipoHabitacion();
            System.out.println("Introduzca la fecha de inicio de la reserva: ");
            LocalDate fi = Consola.leerFecha();
            System.out.println(" ");
            System.out.println("Introduzca la fecha de fin de la reserva: ");
            LocalDate ff = Consola.leerFecha();
            System.out.println(" ");
            Habitacion habitacionReserva = consultarDisponibilidad(t, fi, ff);
            if(habitacionReserva != null) {
                Reserva r = Consola.leerReserva();
                Huesped huespedReserva = controlador.getHuespedes().get(controlador.getHuespedes().indexOf(r.getHuesped()));
                controlador.insertar(new Reserva(huespedReserva, habitacionReserva, r.getRegimen(), fi, ff, r.getNumeroPersonas()));
                System.out.println("\n Habitación insertada correctamente.");
            }else{
                System.out.println("No hay ninguna habitación "+t.toString()+" disponible en esas fechas.");
            }
        }catch(IllegalArgumentException | NullPointerException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    public void mostrarReservasHuesped(){
        System.out.print("Introduzca el dni del huésped: ");
        String dni = Entrada.cadena();
        System.out.print(" ");
        Huesped huespedBusqueda = controlador.buscar(Consola.leerClientePorDni(dni));

        if(huespedBusqueda != null){
            if(!controlador.getReservas(huespedBusqueda).isEmpty()) {//Comprobamos que existan reservas del huesped
                listarReservas(huespedBusqueda);//Mostramos las reservas del huesped
            }else{
                System.out.println("No se han encontrado reservas del huésped.");
            }
        }else {
            System.out.println("No se ha encontrado un huésped con ese dni.");
        }
    }

    /*Crea el método listarReservas que está sobrecargado, mostrando todas las reservas almacenadas (si es que hay) del
    huésped, del tipo de habitación o de la fecha indicada como parámetro. Si no hay, nos informará de que no hay
    reservas.*/
    private void listarReservas(Huesped huesped){
        ArrayList<Reserva> reservasHuesped = controlador.getReservas(huesped);
        Collections.sort(reservasHuesped, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                int valor = 0;//Definimos como valor por defecto que las reservas sean iguales
                if(o1.getFechaInicioReserva().isBefore(o2.getFechaInicioReserva())){//Si la fecha de inicio es anterior va despu?s
                    valor = 1;
                }else{
                    if(o1.getFechaInicioReserva().isAfter(o2.getFechaInicioReserva())){//Si la fecha de inicio es posterior va primero
                        valor = -1;
                    }else{//Si ambas fechas de inicio son la misma, pasamos a ordenar por el identificador de la Habitacion, que ya se encuentra implementado en Habitacion
                        valor = o1.getHabitacion().compareTo(o2.getHabitacion());
                    }
                }
                return valor;
            }
        });

        Iterator it = reservasHuesped.iterator();
        while(it.hasNext()){
            Reserva r = (Reserva) it.next();
            System.out.println(r.toString());
        }
    }

    public void mostrarReservasTipoHabitacion(){
        TipoHabitacion tipo = Consola.leerTipoHabitacion();
        listarReservas(tipo);
    }

    public void listarReservas(LocalDate fecha){
        ArrayList<Reserva> reservasFecha = controlador.getReservas();
        Collections.sort(reservasFecha);
        Iterator it = reservasFecha.iterator();
        while(it.hasNext()){
            Reserva r = (Reserva) it.next();
            if(r.getFechaInicioReserva().isBefore(fecha) && r.getFechaFinReserva().isAfter(fecha)){
                System.out.println(r.toString());
            }else{
                if(r.getFechaInicioReserva().isEqual(fecha) || r.getFechaFinReserva().isEqual(fecha)){
                    System.out.println(r.toString());
                }
            }
        }
    }

    /*Crea el método getReservasAnulables que de la colección de reservas recibida como parámetro, devolverá aquellas que
    sean anulables, es decir, cuya fecha de inicio de la reserva aún no haya llegado.*/
    public ArrayList<Reserva> getReservasAnulables(ArrayList<Reserva> reservasAAnular){
        ArrayList<Reserva> reserva = new ArrayList<>();
        try{
            if(!reservasAAnular.isEmpty()){
                Iterator it = reservasAAnular.iterator();
                while(it.hasNext()){
                    Reserva r = (Reserva) it.next();
                    if(r.getFechaInicioReserva().isAfter(LocalDate.now())){//Si la fecha de inicio es posterior a hoy, se a?ade al array a devolver.
                        reserva.add(r);
                    }
                }
            }else{
                throw new IllegalArgumentException("ERROR: el array reservasAAnular est? vac?o.");
            }
        }catch(IllegalArgumentException | NullPointerException e){
            System.out.println("ERROR INESPERADO. " + e.getMessage());
        }
        return reserva;
    }

    /*Crea el método anularReserva que pedirá el dni del huésped del que se desea anular una reserva (haciendo uso de la clase
    Consola), obteniendo de todas las reservas de dicho huésped aquellas que sean anulables. En el caso de que no tenga ninguna
    anulable deberá de informarse de dicha circunstancia. Si solo tiene una reserva anulable deberá confirmarse de que realmente
    se quiere anular. Y por último, en el caso de que el huésped tenga más de una reserva anulable, deberán ser listadas
    precedidas por un número para que el usuario elija la reserva que desea anular. */
    public void anularReserva() {
        try {
            System.out.print("Introduzca el dni del cliente: ");
            Huesped huesped = controlador.buscar(Consola.leerClientePorDni(Entrada.cadena()));
            System.out.println(" ");

            ArrayList<Reserva> reservaHuesped = getReservasAnulables(controlador.getReservas(huesped));
            if (!reservaHuesped.isEmpty()) {
                int opcion;
                if (reservaHuesped.size() == 1) {
                    System.out.println("El huésped solo dispone de 1 reserva anulable: ");
                    System.out.println(reservaHuesped.get(0).toString());
                    System.out.println("¿Desea realmente anularla?\n\t1. S?\n\t2. No");
                    do {
                        opcion = Entrada.entero();
                    } while (opcion < 1 || opcion > 2);
                    if (opcion == 1) {
                        controlador.borrar(reservaHuesped.get(0));
                        System.out.println("\nReserva anulada correctamente.");
                    } else {
                        System.out.println("\nNo se ha borrado ninguna reserva.");
                    }
                } else {
                    Iterator it = reservaHuesped.iterator();
                    int contador = 0;//Iniciamos un contador para las diferentes opciones
                    System.out.println("0. Cancelar");
                    while (it.hasNext()) {
                        Reserva r = (Reserva) it.next();
                        System.out.println((contador + 1) + ". " + r.toString());
                        contador++;
                    }
                    do {
                        System.out.print("\nSeleccione una reserva para anularla o escoja 0 para cancelar: ");
                        opcion = Entrada.entero();
                    } while (opcion < 0 || opcion > reservaHuesped.size());
                    if (opcion != 0) {
                        controlador.borrar(reservaHuesped.get(opcion - 1));//Borramos el contenido de (opcion-1) que ser? el elemento escogido
                        System.out.println("\nReserva anulada correctamente.");
                    } else {
                        System.out.println("\nNo se ha borrado ninguna reserva.");
                    }
                }
            } else {
                System.out.println("\nNo existen reservas anulables para el huesped.");
            }
        }catch(OperationNotSupportedException e){
            System.out.println("ERROR: La reserva no ha sido anulada correctamente.");
        }
    }

    /*Crea el método mostrarReservas que mostrará todas las reservas almacenadas si es que hay o si no, nos informará de que no
    hay reservas. */
    public void mostrarReservas(){
        int opcion = 0;
        ArrayList<Reserva> reservas = new ArrayList<>();
        if(controlador.getReservas().isEmpty()){
            System.out.println("No existen registros de reservas.");
        }else{
            reservas = controlador.getReservas();
            Collections.sort(reservas);
            Iterator it = reservas.iterator();

            while(it.hasNext()){
                Reserva rAux = (Reserva) it.next();
                System.out.println(rAux.toString());
            }
        }
    }

    public void comprobarDisponibilidad(){
        try {
            TipoHabitacion tipoHabitacion = Consola.leerTipoHabitacion();
            System.out.println("Introduzca la fecha de inicio de reserva:");
            LocalDate fechaInicioReserva = Consola.leerFecha();
            System.out.println("Introduzca la fecha de fin de reserva:");
            LocalDate fechaFinReserva = Consola.leerFecha();
            Habitacion habitacion = consultarDisponibilidad(tipoHabitacion, fechaInicioReserva, fechaFinReserva);
            if(habitacion != null) {
                System.out.println(habitacion.toString());
            }else{
                System.out.println("No hay ninguna habitación libre");
            }
        }catch(IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /*Crea el método consultarDisponibilidad que devuelve una habitación del tipo indicado por parámetro y que está disponible
    entre las fechas de inicio y fin de la reserva, también indicados por parámetro.*/
    public Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva){
        Habitacion habitacion = null;

        try{
            ArrayList<Habitacion> habitacionesDisponibles = controlador.getHabitaciones(tipoHabitacion);//Creamos una copia profunda de todas las habitaciones del hotel que sean del tipo solicitado.
            /*
            Ahora vamos a eliminar aquellas habitaciones del tipo esperado que están ocupadas en la fecha solicitada.
            Para ello vamos a crear otra copia de las reservas del tipo de habitación y las vamos a ir recorriendo para
            borrar aquellas habitaciones que no est?n disponibles en el intervalo de fechas solicitado.
            */
            ArrayList<Reserva> reservas = controlador.getReservas(tipoHabitacion);
            Iterator it = reservas.iterator();

            while(it.hasNext() && !habitacionesDisponibles.isEmpty()){
                Reserva r = (Reserva) it.next();
                if(fechaInicioReserva.isBefore(r.getFechaFinReserva()) && fechaFinReserva.isAfter(r.getFechaInicioReserva())){//Si la fecha de inicio de la reserva es anterior a la fecha de la reserva y la fecha de fin de la reserva es mayor que la de inicio. La habitación estará ocupada. Por tanto se borra.
                    if(habitacionesDisponibles.contains(r.getHabitacion())){//Nos aseguramos que la habitación no haya sido borrada ya
                        habitacionesDisponibles.remove(r.getHabitacion());
                    }
                }else{
                    if(fechaInicioReserva.equals(r.getFechaInicioReserva())){//Si las fechas de inicio coinciden, la habitación no estará disponible. Por tanto se borra.
                        if(habitacionesDisponibles.contains(r.getHabitacion())){//Nos aseguramos que la habitación no haya sido borrada ya
                            habitacionesDisponibles.remove(r.getHabitacion());
                        }
                    }else{//Solo queda que la fecha de inicio fuera posterior a la fecha de inicio de la reserva
                        if(fechaInicioReserva.isAfter(r.getFechaInicioReserva()) && fechaInicioReserva.isBefore(r.getFechaFinReserva())){//Si la fecha de inicio es posterior a la de inicio de la reserva y anterior de a la de fin de la reserva, no estará disponible. Por tanto se borra.
                            if(habitacionesDisponibles.contains(r.getHabitacion())){//Nos aseguramos que la habitación no haya sido borrada ya
                                habitacionesDisponibles.remove(r.getHabitacion());
                            }
                        }
                    }
                }
            }

            if(!habitacionesDisponibles.isEmpty()){//Si todavía quedan habitaciones
                habitacion = habitacionesDisponibles.get(0);
            }
        }catch(IllegalArgumentException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return habitacion;
    }

    public void listarReservas(TipoHabitacion tipoHabitacion){
        ArrayList<Reserva> reservasTipoHabitacion = controlador.getReservas(tipoHabitacion);
        Collections.sort(reservasTipoHabitacion, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                int valor = 0;//Definimos como valor por defecto que las reservas sean iguales
                if(o1.getFechaInicioReserva().isBefore(o2.getFechaInicioReserva())){//Si la fecha de inicio es anterior va después
                    valor = 1;
                }else{
                    if(o1.getFechaInicioReserva().isAfter(o2.getFechaInicioReserva())){//Si la fecha de inicio es posterior va primero
                        valor = -1;
                    }else{//Si ambas fechas de inicio son la misma, pasamos a ordenar por nombre del huesped, que ya se encuentra implementado en Huesped
                        valor = o1.getHuesped().compareTo(o2.getHuesped());
                    }
                }
                return valor;
            }
        });
        Iterator it = reservasTipoHabitacion.iterator();
        while(it.hasNext()){
            Reserva r = (Reserva) it.next();
            System.out.println(r.toString());
        }
    }
}