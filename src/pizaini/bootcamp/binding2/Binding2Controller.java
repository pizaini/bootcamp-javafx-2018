/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.binding2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;

/**
 * FXML Controller class
 *
 * @author Pizaini
 */
public class Binding2Controller implements Initializable {
    @FXML
    private Slider sliderVolume;

    @FXML
    private Label labelVolume;
    
    @FXML
    private Arc arcLingkaran;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Double derajat = 3.6;
        labelVolume.textProperty().bind(sliderVolume.valueProperty().multiply(derajat).asString("%.2f%n"));
        arcLingkaran.lengthProperty().bind(sliderVolume.valueProperty().multiply(derajat));
    }    
    
}
