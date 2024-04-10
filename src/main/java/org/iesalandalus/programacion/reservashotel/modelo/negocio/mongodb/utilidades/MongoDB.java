package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MongoDB {
    //Formatos de las fechas
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    //Datos conexión de la base de datos
    private static final String SERVIDOR = "cluster0.a5owcrc.mongodb.net";
    private static final int PUERTO = 27017;

    //Datos del usuario
    private static final String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";

    //Atributos constantes
    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORREO = "correo";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + "." +DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO = "precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO = HABITACION + "." + TIPO;
    public static final String TIPO_SIMPLE = "SIMPLE";
    public static final String TIPO_DOBLE = "DOBLE";
    public static final String TIPO_TRIPLE = "TRIPLE";
    public static final String TIPO_SUITE = "SUITE";
    public static final String CAMAS_INDIVIDUALES = "camas_individuales";
    public static final String CAMAS_DOBLES = "camas_dobles";
    public static final String BANOS = "banos";
    public static final String JACUZZI = "jacuzzi";
    public static final String RESERVA = "reserva";
    public static final String REGIMEN = "regimen";
    public static final String FECHA_INICIO_RESERVA = "fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA = "fecha_fin_reserva";
    public static final String CHECKIN = "checkin";
    public static final String CHECKOUT = "checkout";
    public static final String PRECIO_RESERVA = "precio_reserva";
    public static final String NUMERO_PERSONAS = "numero_personas";

    private static MongoClient conexion;

    private MongoDB(){

    }

    public static MongoDatabase getBD() {
        if (conexion == null) {
            establecerConexion();
        }

        return conexion.getDatabase(BD);
    }

    private static void establecerConexion()
    {

        String connectionString;
        ServerApi serverApi;
        MongoClientSettings settings;

        if (!SERVIDOR.equals("localhost"))
        {
            connectionString = "mongodb+srv://"+ USUARIO+ ":" + CONTRASENA + "@"+ SERVIDOR +"/?retryWrites=true&w=majority";
            serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();
        }
        else
        {
            connectionString="mongodb://" + USUARIO + ":" + CONTRASENA + "@" + SERVIDOR + ":" + PUERTO ;
            MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());

            settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .credential(credenciales)
                    .build();
        }


        //Creamos la conexión con el serveridos según el setting anterior
        conexion = MongoClients.create(settings);

        try
        {
            if (!SERVIDOR.equals("localhost"))
            {
                MongoDatabase database = conexion.getDatabase(BD);
                database.runCommand(new Document("ping", 1));
            }
        }
        catch (MongoException e)
        {
            e.printStackTrace();
        }

        System.out.println("Conexión a MongoDB realizada correctamente.");

    }

    public static void cerrarConexion() {
        if (conexion != null) {
            conexion.close();
            conexion = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }

    public Document getDocumento(Huesped huesped){
        if (huesped == null) {
            return null;
        }
        String nombre = huesped.getNombre();
        String telefono = huesped.getTelefono();
        String correo = huesped.getCorreo();
        String dni = huesped.getDni();
        LocalDate fechaNacimiento = huesped.getFechaNacimiento();
        return new Document().append(NOMBRE, nombre).append(TELEFONO, telefono).append(CORREO, correo).append(DNI, dni).append(FECHA_NACIMIENTO, fechaNacimiento);
    }

    public Huesped getHuesped(Document documentoHuesped){
        if (documentoHuesped == null) {
            return null;
        }
        return new Huesped(documentoHuesped.getString(NOMBRE), documentoHuesped.getString(TELEFONO), documentoHuesped.getString(CORREO), documentoHuesped.getString(DNI), LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO), FORMATO_DIA));
    }

    public Document getDocumento(Habitacion habitacion){
        if (habitacion == null) {
            return null;
        }
        Document dHabitacion = new Document();
        String identificador = habitacion.getIdentificador();
        int planta = habitacion.getPlanta();
        int puerta = habitacion.getPuerta();
        double precio = habitacion.getPrecio();
        int camas_individuales;
        int camas_dobles;
        int banos;
        boolean jacuzzi;
        dHabitacion.append(IDENTIFICADOR, identificador).append(PLANTA, planta).append(PUERTA, puerta).append(PRECIO, precio);
        if(habitacion instanceof Doble){
            camas_individuales = ((Doble) habitacion).getNumCamasIndividuales();
            camas_dobles = ((Doble) habitacion).getNumCamasDobles();
            dHabitacion.append(CAMAS_INDIVIDUALES, camas_individuales).append(CAMAS_DOBLES, camas_dobles);
        }
        if(habitacion instanceof Triple){
            camas_individuales = ((Triple) habitacion).getNumCamasIndividuales();
            camas_dobles = ((Triple) habitacion).getNumCamasDobles();
            banos = ((Triple) habitacion).getNumBanos();
            dHabitacion.append(BANOS, banos).append(CAMAS_INDIVIDUALES, camas_individuales).append(CAMAS_DOBLES, camas_dobles);
        }
        if(habitacion instanceof Suite){
            banos = ((Suite) habitacion).getNumBanos();
            jacuzzi = ((Suite) habitacion).isTieneJacuzzi();
            dHabitacion.append(BANOS, banos).append(JACUZZI, jacuzzi);
        }
        return dHabitacion;
    }

    public Habitacion getHabitacion(Document documentoHabitacion){
        if (documentoHabitacion == null) {
            return null;
        }
        Habitacion habitacion = null;
        String tipo=documentoHabitacion.getString(TIPO);
        if (tipo.equals(TIPO_SIMPLE))
        {
            habitacion=new Simple(documentoHabitacion.getInteger(PLANTA),documentoHabitacion.getInteger(PUERTA),documentoHabitacion.getDouble(PRECIO));
        }
        else if (tipo.equals(TIPO_DOBLE))
        {
            habitacion=new Doble(documentoHabitacion.getInteger(PLANTA),documentoHabitacion.getInteger(PUERTA),documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(CAMAS_INDIVIDUALES), documentoHabitacion.getInteger(CAMAS_DOBLES));
        }
        else if (tipo.equals(TIPO_TRIPLE))
        {
            habitacion=new Triple(documentoHabitacion.getInteger(PLANTA),documentoHabitacion.getInteger(PUERTA),documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(BANOS), documentoHabitacion.getInteger(CAMAS_INDIVIDUALES), documentoHabitacion.getInteger(CAMAS_DOBLES));
        }
        else if (tipo.equals(TIPO_SUITE))
        {
            habitacion=new Suite(documentoHabitacion.getInteger(PLANTA),documentoHabitacion.getInteger(PUERTA),documentoHabitacion.getDouble(PRECIO), documentoHabitacion.getInteger(BANOS), documentoHabitacion.getBoolean(JACUZZI));
        }
        return habitacion;
    }

    public Reserva getReserva(Document documentoReserva){
        if(documentoReserva == null){
            return null;
        }

        Document dHuesped = (Document) documentoReserva.get(HUESPED);
        Huesped huesped = getHuesped(dHuesped);

        Document dHabitacion = (Document) documentoReserva.get(HABITACION);
        Habitacion habitacion = getHabitacion(dHabitacion);

        String tipoRegimen = documentoReserva.getString(REGIMEN);
        Regimen regimen = null;
        if(tipoRegimen.equals(Regimen.ALOJAMIENTO_DESAYUNO.toString())){
            regimen = Regimen.ALOJAMIENTO_DESAYUNO;
        }
        if(tipoRegimen.equals(Regimen.MEDIA_PENSION.toString())){
            regimen = Regimen.MEDIA_PENSION;
        }
        if(tipoRegimen.equals(Regimen.PENSION_COMPLETA.toString())){
            regimen = Regimen.PENSION_COMPLETA;
        }
        if(tipoRegimen.equals(Regimen.SOLO_ALOJAMIENTO.toString())){
            regimen = Regimen.SOLO_ALOJAMIENTO;
        }

        LocalDate fechaInicioReserva = (LocalDate) documentoReserva.get(FECHA_INICIO_RESERVA);
        LocalDate fechaFinReserva = (LocalDate) documentoReserva.get(FECHA_FIN_RESERVA);

        Reserva reserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva, fechaFinReserva, documentoReserva.getInteger(NUMERO_PERSONAS));

        String checkIn = documentoReserva.getString(CHECKIN);
        if (checkIn != null)
            try {
                reserva.setCheckIn(LocalDateTime.parse(checkIn, FORMATO_DIA_HORA));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        String checkOut = documentoReserva.getString(CHECKOUT);
        if (checkIn != null)
            try {
                reserva.setCheckOut(LocalDateTime.parse(checkOut, FORMATO_DIA_HORA));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        return reserva;
    }

    public Document getDocumento(Reserva reserva){
        if(reserva == null){
            return null;
        }
        Document dReserva = new Document();
        Document dHuesped = getDocumento(reserva.getHuesped());
        Document dHabitacion = getDocumento(reserva.getHabitacion());

        String fechaInicioReserva = reserva.getFechaInicioReserva().format(FORMATO_DIA);
        String fechaFinReserva = reserva.getFechaFinReserva().format(FORMATO_DIA);

        String checkIn = reserva.getCheckIn().format(FORMATO_DIA_HORA);
        String checkOut = reserva.getCheckOut().format(FORMATO_DIA_HORA);

        Double precio = reserva.getPrecio();
        int numeroPersonas = reserva.getNumeroPersonas();

        return dReserva.append(HUESPED, dHuesped).append(HABITACION, dHabitacion).append(REGIMEN, reserva.getRegimen()).append(FECHA_INICIO_RESERVA, fechaInicioReserva).append(FECHA_FIN_RESERVA, fechaFinReserva).append(CHECKIN, checkIn).append(CHECKOUT, checkOut).append(PRECIO_RESERVA, precio).append(NUMERO_PERSONAS, numeroPersonas);
    }
}
