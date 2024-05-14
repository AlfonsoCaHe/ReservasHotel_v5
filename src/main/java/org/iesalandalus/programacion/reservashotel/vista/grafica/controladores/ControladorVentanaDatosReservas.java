package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class ControladorVentanaDatosReservas {
    @FXML
    private TextField tfNombre;
    @FXML private TextField tfDNI;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfFiltro;

    @FXML private Label lTitulo;

    @FXML private DatePicker dpFechaNacimiento;

    @FXML private Button botonInsertar;
    @FXML public Button botonBorrar;
    @FXML public Button botonBuscar;
    @FXML public Button botonRFuturas;

    @FXML public TableView<Reserva> tvReservas;
    @FXML public TableColumn colHuesped;
    @FXML public TableColumn colNombre;
    @FXML public TableColumn colHabitacion;
    @FXML public TableColumn colTipoHabitacion;
    @FXML public TableColumn colRegimen;
    @FXML public TableColumn colFechaInicio;
    @FXML public TableColumn colFechaFin;
    @FXML public TableColumn colNumPersonas;
    @FXML public TableColumn colCheckIn;
    @FXML public TableColumn colCheckOut;
    @FXML public TableColumn colPrecio;


    private Controlador controlador;
    private ObservableList<Reserva> listadoReservas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        try{
            controlador = VistaGrafica.getInstancia().getControlador();
            cargarDatosReserva();
            //dpFechaNacimiento.setValue(LocalDate.now());
            //tfFiltro.textProperty().addListener((observable, oldValue, newValue) -> filtraHuespedes(newValue));
            muestraTablaReservas();
        }
        catch(Exception e){
            //e.printStackTrace();
        }
    }

    private void muestraTablaReservas()
    {
        /*colHuesped.setCellFactory(column -> new TableCell<Huesped, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText(getTableRow().getItem().getDni());
                }
            }
        });
        */
        colHuesped.setCellValueFactory(new PropertyValueFactory<Huesped, String>("dni"));
        /*colNombre.setCellFactory(column -> new TableCell<Huesped, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText(getTableRow().getItem().getNombre());
                }
            }
        });
        */
        colNombre.setCellValueFactory(new PropertyValueFactory<Huesped, String>("nombre"));
        /*
        colHabitacion.setCellFactory(column -> new TableCell<Habitacion, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText(getTableRow().getItem().getIdentificador());
                }
            }
        });*/
        colHabitacion.setCellValueFactory(new PropertyValueFactory<Habitacion, String>("identificador"));
        /*colTipoHabitacion.setCellFactory(column -> new TableCell<Habitacion, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText(getTableRow().getItem().getClass().getSimpleName());
                }
            }
        });*/
        colTipoHabitacion.setCellValueFactory(new PropertyValueFactory<Habitacion, String>("tipo"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Reserva, String>("fechaInicioReserva"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<Reserva, String>("fechaFinReserva"));
        colRegimen.setCellValueFactory(new PropertyValueFactory<Reserva, LocalDate>("regimen"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<Reserva, String>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<Reserva, String>("checkOut"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Reserva, LocalDate>("precio"));
        colNumPersonas.setCellValueFactory(new PropertyValueFactory<Reserva, LocalDate>("numeroPersonas"));

        tvReservas.setItems(listadoReservas);
        tvReservas.getSelectionModel().selectedItemProperty().addListener((observale,oldValue, newValue)-> muestraReservaSeleccionada(newValue));
    }

    public void ventanaInsertarReserva(ActionEvent event){
        try {
            FXMLLoader fxmlLoader=new FXMLLoader(LocalizadorRecursos.class.getResource("/recursos/vistas/VentanaInsertarReserva.fxml"));
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

    /*public void insertarReserva(javafx.event.ActionEvent actionEvent) {
        try {
            Reserva reserva = new Reserva(tfNombre.getText(), tfDNI.getText(), tfCorreo.getText(), tfTelefono.getText(), dpFechaNacimiento.getValue());
            if(reserva != null && !controlador.getHuespedes().contains(reserva)) {
                controlador.insertar(reserva);
                System.out.println("La reserva: [" + reserva + "] ha sido insertada");
                cargarDatosReserva();
            }else{
                ventanaAlerta("Reservas Hotel V.5 - Insertar Reserva", "ERROR: La reserva introducida ya existe.");
            }
        }catch(OperationNotSupportedException | NullPointerException | IllegalArgumentException e){
            ventanaAlerta("Reservas Hotel V.5 - Insertar Reserva", "Reserva no introducida, revise los datos.");
        }
    }*/

    public void borrarReserva(javafx.event.ActionEvent actionEvent){
        try{
            Reserva reserva = tvReservas.getSelectionModel().getSelectedItem();
            if(reserva == null){
                ventanaAlerta("Reservas Hotel V.5 - Borrar Reserva", "No ha seleccionado ningún huésped");
            }else {
                controlador.borrar(reserva);
                System.out.println("La reserva: [" + reserva + "] ha sido borrada");
                cargarDatosReserva();
            }
        } catch (OperationNotSupportedException e) {
            ventanaAlerta("Reservas Hotel V.5 - Borrar Reserva", "No se ha podido borrar la reserva");
        }
    }

    public void mostrarReservas(javafx.event.ActionEvent actionEvent){

    }

    private void cargarDatosReserva(){
        listadoReservas.setAll(controlador.getReservas());
        tvReservas.setItems(listadoReservas);
        tvReservas.refresh();
    }

    private void muestraReservaSeleccionada(Object newValue)
    {
        System.out.println(newValue);
    }

    private void ventanaAlerta(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("¡Atención!");
        alert.setHeaderText(titulo +" : " + mensaje);

        ButtonType volver = new ButtonType("Volver");
        alert.getButtonTypes().setAll(volver);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == volver) {
            alert.close();
        }
    }

    /*@FXML
    void buscar(ActionEvent event) {
        List<Reserva> reservasFiltrados=new ArrayList<>();
        if (tfNombre.getText().isBlank() || tfNombre.getText().isEmpty())
        {
            listadoReservas.setAll(controlador.getReservas());
        }
        else
        {
            String cadenaFiltrado=tfNombre.getText().toLowerCase();
            for(Reserva reserva: controlador.getReservas())
            {
                if (reserva.getNombre().toLowerCase().contains(cadenaFiltrado))
                {
                    reservasFiltrados.add(reserva);
                }
            }
            listadoReservas.setAll(reservasFiltrados);
        }
    }*/
}
