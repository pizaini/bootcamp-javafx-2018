/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.mp3player1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 *
 * @author Pizaini
 */
public class Player1Controller implements Initializable {
    @FXML
    private Button buttonPlay;

    @FXML
    private Button buttonPause;

    @FXML
    private Button playStop;
    
    @FXML
    private Label labelStatus;
    
    private Media media;
    private MediaPlayer mediaPlayer;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //load file mp3 di dalam package
        //InputStream resource = getClass().getResourceAsStream("/pizaini/bootcamp/mp3player1/file1.mp3");
        URL res = getClass().getResource("/pizaini/bootcamp/mp3player1/file1.mp3");
        //set media source
        this.media = new Media(res.toExternalForm());
        //set media player
        this.mediaPlayer = new MediaPlayer(media);
        this.labelStatus.textProperty().bind(this.mediaPlayer.statusProperty().asString());
    }    
    
    public void play(){
        this.mediaPlayer.play();
    }
    
    public void pause(){
        this.mediaPlayer.pause();
    }
    
    public void stop(){
        this.mediaPlayer.stop();
    }
    
}
