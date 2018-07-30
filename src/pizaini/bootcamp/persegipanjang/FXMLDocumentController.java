/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.persegipanjang;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Pizaini
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private TextField textPanjang;
    @FXML
    private TextField textLebar;
    @FXML
    private Label labelKeliling;
    @FXML
    private Label labelLuas;
    private Button buttonHitung;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void hitungPersegiPanjang(){
        //mengambil nilai panjang
        String panjangString = textPanjang.getText();
        //mengambil nilai lebar
        String lebarString = textLebar.getText();
        //konversi string ke int
        int panjangInt = Integer.parseInt(panjangString);
        int lebarInt = Integer.parseInt(lebarString);
        //membuat instance persegi panjang
        PersegiPanjang persegiPanjang = new PersegiPanjang(panjangInt, lebarInt);
        double keliling = persegiPanjang.hitungKeliling();
        double luas = persegiPanjang.hitungLuas();
        //menampilkan hasil luas
        labelLuas.setText("Luas = "+luas);
        //menampilkan hasil keliling
        labelKeliling.setText("Keliling = "+keliling);
    }
    
}
