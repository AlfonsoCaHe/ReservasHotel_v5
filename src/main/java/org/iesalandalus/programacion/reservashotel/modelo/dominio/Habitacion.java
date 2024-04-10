package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public abstract class Habitacion implements Comparable<Habitacion>{
    /* Además, debes crear las constantes indicadas en el diagrama de clases y que luego utilizarás en los métodos de modificación.
    Los métodos de modificación lanzarán las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no
    sea adecuado.
    En el uso de las constantes debes tener presente que el hotel tiene un máximo de tres plantas, con un número máximo de 15
    habitaciones por planta (por tanto, 15 puertas por planta) y que el precio de una habitación (independientemente del tipo que
    sea) oscila entre 40? y 150?.
    */
    public static double MIN_PRECIO_HABITACION = 40;
    public static double MAX_PRECIO_HABITACION = 150;
    public static int MIN_NUMERO_PUERTA = 0;
    public static int MAX_NUMERO_PUERTA = 15;
    public static int MIN_NUMERO_PLANTA = 0;
    public static int MAX_NUMERO_PLANTA = 3;
    protected String identificador;
    protected int planta;
    protected int puerta;

    protected double precio;

    /*Crea los constructores con parámetros que harán uso de los métodos de modificación.*/
    public Habitacion(int planta, int puerta, double precio){
        try {
            setPlanta(planta);
            setPuerta(puerta);
            setPrecio(precio);
            setIdentificador();
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
    }

    /*Crea el constructor copia.*/
    public Habitacion(Habitacion habitacion){
        if (habitacion == null){
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
        try {
            this.planta = habitacion.getPlanta();
            this.puerta = habitacion.getPuerta();
            this.precio = habitacion.getPrecio();
            this.identificador = habitacion.getIdentificador();
        }catch(NullPointerException e){
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
    }

    public abstract int getNumeroMaximoPersonas();

    /*Crea los métodos de acceso y modificación de cada atributo con la visibilidad adecuada, teniendo en cuenta que el identificador
    de una habitación será el número de planta seguido del número de puerta.
    */
    public String getIdentificador() {
        return identificador;
    }

    protected void setIdentificador(){
        this.identificador = "" + this.planta + this.puerta;
    }

    protected void setIdentificador(String identificador){
        if(identificador.isEmpty()){
            throw new IllegalArgumentException("ERROR: El indentificador no puede ser vacío.");
        }
        this.identificador=identificador;
    }

    public int getPlanta(){
        return planta;
    }

    protected void setPlanta(int planta){
        Integer numero = Integer.parseInt(""+planta);//Comprobamos que el int planta no sea null
        if(numero == null){
            throw new NullPointerException("ERROR: El número de la planta no puede ser nulo");
        }

        boolean valido = false;
        if(planta > MIN_NUMERO_PLANTA)
            if(planta <= MAX_NUMERO_PLANTA){
                this.planta = planta;
                valido = true;
            }
        if(!valido)
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 0 ni mayor que 3.");

    }

    public int getPuerta(){
        return puerta;
    }

    protected void setPuerta(int puerta){
        Integer numero = Integer.parseInt("" + puerta);//Comprobamos que el int puerta no sea null
        if (numero == null) {
            throw new NullPointerException("ERROR: El número de la puerta no puede ser nulo");
        }

        boolean valido = false;
        if ((puerta >= MIN_NUMERO_PUERTA) && (puerta < MAX_NUMERO_PUERTA)) {
            this.puerta = puerta;
            valido = true;
        }
        if (!valido){
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 0 ni mayor que 15.");
        }
    }

    public double getPrecio() {
        return this.precio;
    }

    protected void setPrecio(double precio) {
        Double numero = Double.parseDouble("" + precio);//Comprobamos que el double precio no sea null
        if (numero == null) {
            throw new NullPointerException("ERROR: El precio de la habitación no puede ser nulo");
        }

        boolean valido = false;
        if ((precio >= MIN_PRECIO_HABITACION) && (precio <= MAX_PRECIO_HABITACION)) {
            this.precio = precio;
            valido = true;
        }
        if (!valido){
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");
        }
    }

    /*Una habitación será igual a otra si su identificador es el mismo. Basándote en ello crea los métodos equals y hashCode.*/
    @Override
    public boolean equals(Object obj) {
        Habitacion habitacion = (Habitacion)obj;
        return habitacion.getIdentificador().equals(this.identificador);
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    /*Crea el método toString que devuelva la cadena que esperan los tests.*/
    @Override
    public String toString() {
        return String.format("identificador=%s (%d-%d), precio habitación=%s, ", getIdentificador(), getPlanta(), getPuerta(), getPrecio());
    }

    @Override
    public int compareTo(Habitacion o) {
        int valor = 0;//Por defecto el valor será igual
        if(this.planta < o.getPlanta()){//Si la planta es menor, es que va primero
            valor = -1;
        }else{
            if(this.planta > o.getPlanta()){//Si la planta es mayor, es que va después
                valor = 1;
            }else{
                if(this.puerta < o.getPuerta()){//Si era la misma planta, si la puerta es menor, va primero
                    valor = -1;
                }
            }
        }
        return valor;
    }
}