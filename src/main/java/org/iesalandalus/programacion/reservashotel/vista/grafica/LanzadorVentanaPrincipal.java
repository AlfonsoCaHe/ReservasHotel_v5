package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;

import java.util.Optional;

public class LanzadorVentanaPrincipal extends Application {

    public static void comenzar(){
        launch();
    }

    public void start(Stage escenarioPrincipal){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("/recursos/vistas/VentanaPrincipal.fxml"));
            Parent raiz=fxmlLoader.load();
            Scene scene = new Scene(raiz);
            escenarioPrincipal.setTitle("Reservas Hotel V.5");
            escenarioPrincipal.setScene(scene);
            escenarioPrincipal.setOnCloseRequest(e->confirmarSalida(escenarioPrincipal,e));
            escenarioPrincipal.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar salida");
        alert.setHeaderText("¿Está seguro de que desea salir?");
        alert.setContentText("Seleccione una opción.");

        ButtonType bSi = new ButtonType("Sí");
        ButtonType bNo = new ButtonType("No");

        alert.getButtonTypes().setAll(bSi, bNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == bSi) {
            escenarioPrincipal.close();
            System.exit(0);
        } else {
            e.consume();
        }
    }
}
