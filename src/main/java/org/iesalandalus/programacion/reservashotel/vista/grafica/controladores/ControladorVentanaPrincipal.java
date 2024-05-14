package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;

import javafx.event.ActionEvent;
import java.io.IOException;

public class ControladorVentanaPrincipal {

    @FXML
    private Button bVentanaHuespedes;
    @FXML
    private Button bVentanaReservas;
    @FXML
    private Button bVentanaHabitaciones;

    public void abrirVentanaDatosHuesped(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("/recursos/vistas/VentanaDatosHuesped.fxml"));
            Parent raiz=fxmlLoader.load();
            ControladorVentanaDatosHuesped c=fxmlLoader.getController();

            Scene escena=new Scene(raiz);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Reservas Hotel V.5 - Gestión de Huéspedes");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void abrirVentanaDatosHabitaciones(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("/recursos/vistas/VentanaDatosHabitaciones.fxml"));
            Parent raiz=fxmlLoader.load();
            ControladorVentanaDatosHabitaciones c=fxmlLoader.getController();

            Scene escena=new Scene(raiz);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Reservas Hotel V.5 - Gestión de Habitaciones");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void abrirVentanaDatosReservas(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("/recursos/vistas/VentanaDatosReservas.fxml"));
            Parent raiz=fxmlLoader.load();
            //ControladorVentanaDatosReservas c=fxmlLoader.getController();

            Scene escena=new Scene(raiz);
            Stage escenario=new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Reservas Hotel V.5 - Gestión de Reservas");
            escenario.setResizable(false);
            escenario.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
