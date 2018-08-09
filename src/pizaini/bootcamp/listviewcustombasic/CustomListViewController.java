/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.listviewcustombasic;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import pizaini.bootcamp.tablemahasiswa.Mahasiswa;

/**
 * FXML Controller class
 *
 * @author Pizaini
 */
public class CustomListViewController implements Initializable {
    @FXML
    private ListView<Mahasiswa> listViewMahasiswa;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set cell fantory untuk memanipulasi cell
        listViewMahasiswa.setCellFactory(data -> new ListCellMahasiswa());
        // add dada to list view
        Mahasiswa m = new Mahasiswa();
        m.setNama("Test");
        m.setNim("335");
        m.setUmum(35);
        listViewMahasiswa.getItems().add(m);
        
        Mahasiswa m2 = new Mahasiswa();
        m2.setNama("Test");
        m2.setNim("335");
        m2.setUmum(35);
        listViewMahasiswa.getItems().add(m2);
        listViewMahasiswa.getItems().add(new Mahasiswa("Nama3", "NIM3", 35));
    }    
    
}
