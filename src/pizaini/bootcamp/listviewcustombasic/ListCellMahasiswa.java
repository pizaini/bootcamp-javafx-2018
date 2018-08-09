/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.listviewcustombasic;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import pizaini.bootcamp.tablemahasiswa.Mahasiswa;

/**
 *
 * @author Pizaini
 */
public class ListCellMahasiswa extends ListCell<Mahasiswa>{
    private FXMLLoader loader;
    @FXML
    private Label labelNama;
    @FXML
    private Label labelNim;
    @FXML
    private Label labelUmur;

    public ListCellMahasiswa() {
        
    }
    

    @Override
    protected void updateItem(Mahasiswa item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null){
            setText(null);
        }else{
            //contrainer VBOX harus sama dengan contrainer di FXML yang akan diload
            VBox vbox = null;
            if(loader == null){
                loader = new FXMLLoader(getClass().getResource("ListCellMahasiswa.fxml"));
                loader.setController(this);
                try {
                    vbox = loader.load();
                } catch (IOException ex) {
                }
            }
            labelNama.setText(item.getNama());
            labelNim.setText(item.getNim());
            labelUmur.setText(String.valueOf(item.getUmum()));
            //set container ke listCell
            setGraphic(vbox);
        }
        
    }
    
}
