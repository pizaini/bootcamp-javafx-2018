/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.tablemahasiswa;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Pizaini
 */
public class MainController implements Initializable {
    @FXML
    private TableView<Mahasiswa> tableMahasiswa;

    @FXML
    private TableColumn<Mahasiswa, String> colNama;

    @FXML
    private TableColumn<Mahasiswa, String> colNIM;
    
    @FXML
    private TableColumn<Mahasiswa, String> colUmur;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNIM.setCellValueFactory(data -> data.getValue().nimProperty());
        colNama.setCellValueFactory(data -> data.getValue().namaProperty());
        colUmur.setCellValueFactory(data -> data.getValue().umumProperty().asString());
        Mahasiswa mahasiswa = new Mahasiswa();
       mahasiswa.setNama("Nama1");
       mahasiswa.setNim("2342342");
       mahasiswa.setUmum(12);
        tableMahasiswa.getItems().add(mahasiswa);
        
    }    
    
}
