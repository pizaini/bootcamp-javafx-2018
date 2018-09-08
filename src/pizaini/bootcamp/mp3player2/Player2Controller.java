/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.mp3player2;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Pizaini
 */
public class Player2Controller implements Initializable {
    @FXML
    private Button buttonChoose;

    @FXML
    private Label labelFIleName, labelVolume, labelDuration, labelCurrentTime;

    @FXML
    private Button buttonPlayPause;

    @FXML
    private Slider progressMedia;

    @FXML
    private Slider progressVolume;
    
    private FileChooser fileChooser;
    private String choosenFilePath;
    private Media media;
    private MediaPlayer mediaPlayer;
    private SimpleObjectProperty<Duration> currentDuration = new SimpleObjectProperty<>();
    private Duration totalDuration;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.fileChooser = new FileChooser();
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3", "*.mp3"));
        //binding
        labelVolume.textProperty().bind(progressVolume.valueProperty().asString("%.0f"));
    }    
    
    public void openFile(){
        File file = this.fileChooser.showOpenDialog(null);
        if(file != null){
            this.choosenFilePath = file.getAbsolutePath();
            this.choosenFilePath = this.choosenFilePath.replace("\\", "/");
            try {
                //Stop media player (jika tersedia)
                if(mediaPlayer != null){
                    this.mediaPlayer.stop();
                }
                this.media = new Media(new File(choosenFilePath).toURI().toURL().toExternalForm());
                this.mediaPlayer = new MediaPlayer(media);
                labelFIleName.setText(file.getName());
                mediaPlayer.setVolume(0.5);
                
                //data binding for BUTTON TEXT
                buttonPlayPause.textProperty().bind(Bindings.when(mediaPlayer.statusProperty().isEqualTo(MediaPlayer.Status.PLAYING)).then("PAUSE").otherwise("PLAY"));
                
                //data binding for VOLUME
                mediaPlayer.volumeProperty().bind(progressVolume.valueProperty().divide(100));
                currentDuration.set(mediaPlayer.getCurrentTime());
                mediaPlayer.setOnReady(() -> {
                    totalDuration = mediaPlayer.getTotalDuration();
                    labelDuration.setText(formatTime(totalDuration));
                });
                mediaPlayer.currentTimeProperty().addListener(((observable) -> {
                    updateProgress(mediaPlayer.getCurrentTime());
                }));
                //event listener for progress bar
                progressMedia.valueProperty().addListener((Observable observable) -> {
                    if(progressMedia.isValueChanging()){
                        mediaPlayer.seek(totalDuration.multiply(progressMedia.getValue()/100));
                    }
                });
            } catch (MalformedURLException ex) {
                System.out.println("ERROR: "+ex.getMessage());
            }
        }
    }
    
    public void playOrPause(){
        try{
            //jika sedang PLAY, lakukan PAUSE
            if(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING){
                mediaPlayer.pause();
            }else{
                //selain itu PLAY
                mediaPlayer.play();
            }
            
        }catch(Exception ex){
            System.err.println("ERROR "+ex.getMessage());
        }
    }
    
    private void updateProgress(Duration currentTime){
        progressMedia.setValue(currentTime.divide(totalDuration.toMillis()).toMillis()*100);
        labelCurrentTime.setText(formatTime(currentTime));
    }
    
    private String formatTime(Duration elapsed) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
        if (elapsedHours > 0) {
            return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
        } else {
             return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
        }
    }
    
}
