package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorVentanaDatosHuesped {

    @FXML private TextField tfNombre;
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

    @FXML public TableView<Huesped> tvHuespedes;
    @FXML public TableColumn colNombre;
    @FXML public TableColumn colDNI;
    @FXML public TableColumn colCorreo;
    @FXML public TableColumn colTelefono;
    @FXML public TableColumn colFechaNac;


    private Controlador controlador;
    private ObservableList<Huesped> listadoHuespedes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        controlador = VistaGrafica.getInstancia().getControlador();
        cargarDatosHuesped();
        dpFechaNacimiento.setValue(LocalDate.now());
        tfFiltro.textProperty().addListener((observable, oldValue, newValue) -> filtraHuespedes(newValue));
        muestraTablaHuespedes();
    }

    private void muestraTablaHuespedes()
    {
        colNombre.setCellValueFactory(new PropertyValueFactory<Huesped,String>("nombre"));
        colDNI.setCellValueFactory(new PropertyValueFactory<Huesped,String>("dni"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<Huesped, String>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Huesped, String>("telefono"));
        colFechaNac.setCellValueFactory(new PropertyValueFactory<Huesped, LocalDate>("fechaNacimiento"));
        tvHuespedes.setItems(listadoHuespedes);
        tvHuespedes.getSelectionModel().selectedItemProperty().addListener((observale,oldValue, newValue)-> muestraHuespedSeleccionado(newValue));
    }

    public void insertarHuesped(javafx.event.ActionEvent actionEvent) {
        try {
            Huesped huesped = new Huesped(tfNombre.getText(), tfDNI.getText(), tfCorreo.getText(), tfTelefono.getText(), dpFechaNacimiento.getValue());
            if(huesped != null && !controlador.getHuespedes().contains(huesped)) {
                controlador.insertar(huesped);
                System.out.println("El huesped: [" + huesped + "] ha sido insertado");
                cargarDatosHuesped();
            }else{
                ventanaAlerta("Reservas Hotel V.5 - Insertar Huésped", "ERROR: El Huésped introducido ya existe.");
            }
        }catch(OperationNotSupportedException | NullPointerException | IllegalArgumentException e){
            ventanaAlerta("Reservas Hotel V.5 - Insertar Huésped", "Huésped no introducido, revise los datos.");
        }
    }

    public void borrarHuesped(javafx.event.ActionEvent actionEvent){
        try{
            Huesped huesped = tvHuespedes.getSelectionModel().getSelectedItem();
            if(huesped == null){
                ventanaAlerta("Reservas Hotel V.5 - Borrar Huésped", "No ha seleccionado ningún huésped");
            }else {
                controlador.borrar(huesped);
                System.out.println("El huesped: [" + huesped + "] ha sido borrado");
                cargarDatosHuesped();
            }
        } catch (OperationNotSupportedException e) {
            ventanaAlerta("Reservas Hotel V.5 - Borrar Huésped", "No se ha podido borrar el huésped");
        }
    }

    public void mostrarHuespedes(javafx.event.ActionEvent actionEvent){

    }

    private void cargarDatosHuesped(){
        listadoHuespedes.setAll(controlador.getHuespedes());
        tvHuespedes.setItems(listadoHuespedes);
        tvHuespedes.refresh();
    }

    private void muestraHuespedSeleccionado(Object newValue)
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

    private void filtraHuespedes(String newValue)
    {
        FilteredList<Huesped> filtradoHuespedes=new FilteredList<>(listadoHuespedes, huesped-> true);

        filtradoHuespedes.setPredicate(huesped -> {

            if (newValue.isBlank() || newValue.isEmpty() || newValue==null)
                return true;

            String cadenaFiltrado=newValue.toLowerCase();

            if (huesped.getNombre().toLowerCase().indexOf(cadenaFiltrado)> -1 )
                return true;
            else if (huesped.getDni().toLowerCase().indexOf(cadenaFiltrado)>-1)
                return true;
            else if (huesped.getCorreo().toLowerCase().indexOf(cadenaFiltrado)>-1)
                return true;
            else if (huesped.getTelefono().toLowerCase().indexOf(cadenaFiltrado)>-1)
                return true;
            else
                return false;
        });
        tvHuespedes.setItems(filtradoHuespedes);
    }

    @FXML
    void buscar(ActionEvent event) {
        List<Huesped> huespedesFiltrados=new ArrayList<>();
        if (tfNombre.getText().isBlank() || tfNombre.getText().isEmpty())
        {
            listadoHuespedes.setAll(controlador.getHuespedes());
        }
        else
        {
            String cadenaFiltrado=tfNombre.getText().toLowerCase();
            for(Huesped huesped: controlador.getHuespedes())
            {
                if (huesped.getNombre().toLowerCase().contains(cadenaFiltrado))
                {
                    huespedesFiltrados.add(huesped);
                }
            }
            listadoHuespedes.setAll(huespedesFiltrados);
        }
    }

    public void reservasFuturas(ActionEvent event){
        
    }
}
