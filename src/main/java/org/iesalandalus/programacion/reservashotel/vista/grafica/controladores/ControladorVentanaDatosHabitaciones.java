package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorVentanaDatosHabitaciones {

    @FXML private Label lTitulo;
    @FXML private TextField tfPlanta;
    @FXML private TextField tfPuerta;
    @FXML private TextField tfPrecio;
    @FXML private TextField tfMaxNumPersonas;
    @FXML private TextField tfNumCamasIndividuales;
    @FXML private TextField tfNumCamasDobles;
    @FXML private TextField tfNumBanos;
    @FXML private TextField tfIdentificador;
    @FXML private TextField tfTipoHabitacion;

    @FXML private CheckBox cbTieneJacuzzi;
    @FXML private ChoiceBox cbTipoHabitacion;

    @FXML private Button bInsertar;
    @FXML private Button bBorrar;
    @FXML private Button bBuscar;

    @FXML private TableView<Habitacion> tvHabitaciones;
    @FXML private TableColumn colIdentificador;
    @FXML private TableColumn colTipo;
    @FXML private TableColumn colPlanta;
    @FXML private TableColumn colPuerta;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colNumPersonas;
    @FXML private TableColumn colCamasIndividuales;
    @FXML private TableColumn colCamasDobles;
    @FXML private TableColumn colNumBanos;
    @FXML private TableColumn colTieneJacuzzi;

    private Controlador controlador;
    private ObservableList<Habitacion> listadoHabitaciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        controlador = VistaGrafica.getInstancia().getControlador();
        cbTipoHabitacion.getItems().addAll("Simple", "Doble", "Triple", "Suite");
        cargarDatosHabitacion();
        muestraTablaHabitaciones();
        tfPlanta.setDisable(true);
        tfPuerta.setDisable(true);
        tfPrecio.setDisable(true);
        tfNumCamasIndividuales.setDisable(true);
        tfNumCamasDobles.setDisable(true);
        tfNumBanos.setDisable(true);
        cbTieneJacuzzi.setDisable(true);
        tfMaxNumPersonas.setEditable(false);
    }

    private void muestraTablaHabitaciones()
    {
        colIdentificador.setCellValueFactory(new PropertyValueFactory<Habitacion,String>("identificador"));

        colTipo.setCellFactory(column -> new TableCell<Habitacion, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    setText(getTableRow().getItem().getClass().getSimpleName());
                }
            }
        });

        colPlanta.setCellValueFactory(new PropertyValueFactory<Habitacion,String>("planta"));
        colPuerta.setCellValueFactory(new PropertyValueFactory<Habitacion, String>("puerta"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Habitacion, String>("precio"));
        if(!colTipo.equals("Simple")){
            colCamasIndividuales.setCellValueFactory(new PropertyValueFactory<Habitacion, LocalDate>("numCamasIndividuales"));
            colCamasDobles.setCellValueFactory(new PropertyValueFactory<Habitacion, LocalDate>("numCamasDobles"));
            if(colTipo.equals("Triple")) {
                colNumBanos.setCellValueFactory(new PropertyValueFactory<Habitacion, LocalDate>("numBanos"));
            }
            if(colTipo.equals("Suite")){
                colNumBanos.setCellValueFactory(new PropertyValueFactory<Habitacion, LocalDate>("numBanos"));
                colTieneJacuzzi.setCellValueFactory(new PropertyValueFactory<Habitacion, LocalDate>("tieneJacuzzi"));
            }
        }
        tvHabitaciones.setItems(listadoHabitaciones);
        tvHabitaciones.getSelectionModel().selectedItemProperty().addListener((observale,oldValue, newValue)-> muestraHabitacionSeleccionada(newValue));
    }

    public void insertarHabitacion(javafx.event.ActionEvent actionEvent) {
        try {
            Habitacion habitacion = null;
            switch (cbTipoHabitacion.getValue().toString()) {
                case "Simple":
                    habitacion = new Simple(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()));
                    break;
                case "Doble":
                    habitacion = new Doble(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()),
                            Integer.parseInt(tfNumCamasIndividuales.getText()), Integer.parseInt(tfNumCamasDobles.getText()));
                    break;
                case "Triple":
                    habitacion = new Triple(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()),
                            Integer.parseInt(tfNumBanos.getText()), Integer.parseInt(tfNumCamasIndividuales.getText()), Integer.parseInt(tfNumCamasDobles.getText()));
                    break;
                case "Suite":
                    boolean jacuzzi = cbTieneJacuzzi.isSelected();
                    habitacion = new Suite(Integer.parseInt(tfPlanta.getText()), Integer.parseInt(tfPuerta.getText()), Double.parseDouble(tfPrecio.getText()),
                            Integer.parseInt(tfNumBanos.getText()), jacuzzi);
                    break;
                default:
                    ventanaAlerta("Gestión de Habitaciones - InsertarHabitación", "No se ha seleccionado un tipo de habitación válido");
            }
            if(habitacion != null && !controlador.getHabitaciones().contains(habitacion)) {
                controlador.insertar(habitacion);
                System.out.println("La habitación: [" + habitacion + "] ha sido insertada");
                cargarDatosHabitacion();
            }else{
                ventanaAlerta("Reservas Hotel V.5 - Insertar Habitación", "ERROR: La habitación introducida ya existe.");
            }
        }catch(OperationNotSupportedException | NullPointerException | IllegalArgumentException e){
            ventanaAlerta("Reservas Hotel V.5 - Insertar Habitación", "Habitación no introducida, revise los datos.");
        }
    }

    public void borrarHabitacion(javafx.event.ActionEvent actionEvent){
        try{
            Habitacion habitacion = tvHabitaciones.getSelectionModel().getSelectedItem();
            if(habitacion == null){
                ventanaAlerta("Reservas Hotel V.5 - Borrar Habitación", "No ha seleccionado ninguna habitación");
            }else {
                controlador.borrar(habitacion);
                System.out.println("La Habitación: [" + habitacion + "] ha sido borrada");
                cargarDatosHabitacion();
            }
        } catch (OperationNotSupportedException e) {
            ventanaAlerta("Reservas Hotel V.5 - Borrar Habitación", "No se ha podido borrar la habitación");
        }
    }

    public void mostrarHabitaciones(javafx.event.ActionEvent actionEvent){

    }

    private void cargarDatosHabitacion(){
        listadoHabitaciones.setAll(controlador.getHabitaciones());
        tvHabitaciones.setItems(listadoHabitaciones);
        tvHabitaciones.refresh();
    }

    private void muestraHabitacionSeleccionada(Object newValue)
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

    @FXML
    void buscarHabitacion(ActionEvent event) {
        List<Habitacion> habitacionesFiltradas=new ArrayList<>();
        if (tfIdentificador.getText().isBlank() || tfIdentificador.getText().isEmpty())
        {
            listadoHabitaciones.setAll(controlador.getHabitaciones());
        }
        else
        {
            String cadenaFiltrado=tfIdentificador.getText();
            for(Habitacion habitacion: controlador.getHabitaciones())
            {
                if (habitacion.getIdentificador().contains(cadenaFiltrado))
                {
                    habitacionesFiltradas.add(habitacion);
                }
            }
            listadoHabitaciones.setAll(habitacionesFiltradas);
        }
    }

    @FXML
    void filtrarTipoHabitacion(ActionEvent event) {
        List<Habitacion> habitacionesFiltradas=new ArrayList<>();
        if (tfTipoHabitacion.getText().isBlank() || tfTipoHabitacion.getText().isEmpty())
        {
            listadoHabitaciones.setAll(controlador.getHabitaciones());
        }
        else
        {
            String cadenaFiltrado=tfTipoHabitacion.getText();
            for(Habitacion habitacion: controlador.getHabitaciones())
            {
                String tipo = habitacion.getClass().getSimpleName().toLowerCase();
                if (tipo.equals(tfTipoHabitacion.getText().toLowerCase()))
                {
                    habitacionesFiltradas.add(habitacion);
                }
            }
            listadoHabitaciones.setAll(habitacionesFiltradas);
        }
    }

    public void reservasFuturas(ActionEvent event){

    }

    public void actualizarInterfaz(ActionEvent actionEvent) {
        switch (cbTipoHabitacion.getValue().toString()) {
            case "Simple":
                tfPlanta.setText("");
                tfPuerta.setText("");
                tfNumCamasIndividuales.setText("");
                tfNumCamasDobles.setText("");
                tfNumBanos.setText("");

                tfPlanta.setDisable(false);
                tfPuerta.setDisable(false);
                tfPrecio.setDisable(false);
                tfPrecio.setText(Simple.MIN_PRECIO_HABITACION+"-"+Simple.MAX_PRECIO_HABITACION);
                tfNumCamasIndividuales.setDisable(true);
                tfNumCamasDobles.setDisable(true);
                tfNumBanos.setDisable(true);
                cbTieneJacuzzi.setDisable(true);
                tfMaxNumPersonas.setText("1");
                break;
            case "Doble":
                tfPlanta.setText("");
                tfPuerta.setText("");
                tfNumCamasIndividuales.setText("");
                tfNumCamasDobles.setText("");
                tfNumBanos.setText("");

                tfPlanta.setDisable(false);
                tfPuerta.setDisable(false);
                tfPrecio.setDisable(false);
                tfPrecio.setText(Doble.MIN_PRECIO_HABITACION+"-"+Doble.MAX_PRECIO_HABITACION);
                tfNumCamasIndividuales.setEditable(false);
                tfNumCamasDobles.setDisable(false);
                tfNumBanos.setDisable(true);
                cbTieneJacuzzi.setDisable(true);
                tfMaxNumPersonas.setText("2");
                tfMaxNumPersonas.setEditable(false);
                break;
            case "Triple":
                tfPlanta.setText("");
                tfPuerta.setText("");
                tfNumCamasIndividuales.setText("");
                tfNumCamasDobles.setText("");
                tfNumBanos.setText("");

                tfPlanta.setDisable(false);
                tfPuerta.setDisable(false);
                tfPrecio.setDisable(false);
                tfPrecio.setText(Triple.MIN_PRECIO_HABITACION+"-"+Triple.MAX_PRECIO_HABITACION);
                tfNumCamasIndividuales.setEditable(false);
                tfNumCamasDobles.setDisable(false);
                tfNumBanos.setDisable(false);
                cbTieneJacuzzi.setDisable(true);
                tfMaxNumPersonas.setText("3");
                tfMaxNumPersonas.setEditable(false);
                break;
            case "Suite":
                tfPlanta.setText("");
                tfPuerta.setText("");
                tfNumCamasIndividuales.setText("");
                tfNumCamasDobles.setText("");
                tfNumBanos.setText("");

                tfPlanta.setDisable(false);
                tfPuerta.setDisable(false);
                tfPrecio.setDisable(false);
                tfPrecio.setText(Suite.MIN_PRECIO_HABITACION+"-"+Suite.MAX_PRECIO_HABITACION);
                tfNumCamasIndividuales.setEditable(true);
                tfNumCamasDobles.setDisable(true);
                tfNumBanos.setDisable(false);
                cbTieneJacuzzi.setDisable(false);
                tfMaxNumPersonas.setText("4");
                tfMaxNumPersonas.setEditable(false);
                break;
        }
    }
}
