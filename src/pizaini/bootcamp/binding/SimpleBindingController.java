/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.binding;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Pizaini
 */
public class SimpleBindingController implements Initializable {
    @FXML
    private TextArea textInput;
    @FXML
    private TextArea textOutput;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //menggunakan data binding
        textOutput.textProperty().bind(textInput.textProperty());
    }    
    
}
