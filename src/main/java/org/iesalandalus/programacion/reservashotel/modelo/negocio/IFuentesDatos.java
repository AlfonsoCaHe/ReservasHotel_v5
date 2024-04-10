package org.iesalandalus.programacion.reservashotel.modelo.negocio;

public interface IFuentesDatos {

    public abstract IHuespedes crearHuespedes();

    public abstract IHabitaciones crearHabitaciones();

    public abstract IReservas crearReservas();
}
