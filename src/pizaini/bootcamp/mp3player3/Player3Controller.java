/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.mp3player3;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;

/**
 * FXML Controller class
 *
 * @author Pizaini
 */
public class Player3Controller implements Initializable {
    private DirectoryChooser directoryChooser;
    private File[] allSong;
    private Media media;
    private MediaPlayer mediaPlayer;
    
    @FXML
    private ListView<String> listViewLagu;
    @FXML
    private Button buttonPlayOrPause;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        directoryChooser = new DirectoryChooser();
    }    
    
    public void pilihDirektory(ActionEvent ae){
        File file = directoryChooser.showDialog(null);
        if(file != null){
            listViewLagu.getItems().clear();
            //file filter
            FileFilter filter = (File pathname) -> {
                String fileName = pathname.getName();
                return fileName.endsWith(".mp3");
            };
            File[] listFiles = file.listFiles(filter);
            allSong = listFiles;
            for(File f: listFiles){
                listViewLagu.getItems().add(f.getName());
            }
        }
    }
    
    public void playOrPause(){
        try{
            //jika sedang PLAY, lakukan PAUSE
            int selectedIndex = listViewLagu.getSelectionModel().getSelectedIndex();
            if(selectedIndex > -1){
                String pathName = allSong[selectedIndex].getAbsolutePath();
                pathName = pathName.replace("\\", "/");
                this.media = new Media(new File(pathName).toURI().toURL().toExternalForm());
                this.mediaPlayer = new MediaPlayer(media);
                // PLAY media
                mediaPlayer.play();
            }
        }catch(MalformedURLException ex){
            System.err.println("ERROR "+ex.getMessage());
        }
    }
    
}
