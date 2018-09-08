/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizaini.bootcamp.mp3player3;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

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
    private SimpleIntegerProperty currentSeletedList = new SimpleIntegerProperty(-1);
    private SimpleObjectProperty<Duration> currentDuration = new SimpleObjectProperty<>();
    private Duration totalDuration;
    private final double DEFAULT_VOLUME = 21d;
    
    @FXML
    private ListView<String> listViewLagu;
    
    @FXML
    private Button buttonPlayOrPause;
    
    @FXML
    private Label labelTitle, labelArtis, labelYear, labelVolume;
    
    @FXML
    private ToggleButton buttonRandom, buttonRepeat, buttonMute;

    @FXML
    private Button buttonProvious;

    @FXML
    private Button buttonNext;
    
    @FXML
    private ImageView imgAlbum;

    @FXML
    private Slider sliderCurrentTime, sliderVolume;

    @FXML
    private Label labelCurrentDuration;

    @FXML
    private Label labelTotalDuration;
    
    @FXML
    private BorderPane mainContainer;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Pilih direktori");
        File initDir = new File(System.getProperty("user.home")+File.separator+"Music");
        if(initDir.exists() && initDir.canRead()){
            directoryChooser.setInitialDirectory(initDir);
        }
        listViewLagu.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                playMediaPlayer(currentSeletedList.get());
            }
        });
        currentSeletedList.bind(listViewLagu.selectionModelProperty().get().selectedIndexProperty()); 
        labelVolume.textProperty().bind(sliderVolume.valueProperty().asString("%.0f%%"));
        sliderVolume.setValue(DEFAULT_VOLUME);
        sliderVolume.disableProperty().bind(buttonMute.selectedProperty());
    }    
    
    public void pilihDirektory(ActionEvent ae){
        File file = directoryChooser.showDialog(mainContainer.getScene().getWindow());
        if(file != null){
            listViewLagu.getItems().clear();
            Runnable run = () -> {
                //file filter
                FileFilter filter = (File pathname) -> {
                    String fileName = pathname.getName();
                    return fileName.endsWith(".mp3");
                };
                File[] listFiles = file.listFiles(filter);
                allSong = listFiles;
                List<String> listFileName = new ArrayList<>();
                for(File f: listFiles){
                    try {
                        Mp3File mp3file = new Mp3File(f);
                        if(mp3file.hasId3v2Tag()){
                            ID3v2 tag = mp3file.getId3v2Tag();
                            listFileName.add(tag.getArtist()+" - "+tag.getTitle()+" - "+tag.getAlbum());
                        }
//                        System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
//                        System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
//                        System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
//                        System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
//                        System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
//                        System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
                    } catch (UnsupportedTagException | InvalidDataException | IOException ex) {
                        System.out.println("Error: "+ex.getMessage());
                    }
                   // listFileName.add(f.getName());
                }
                Platform.runLater(()->{
                    if(mediaPlayer != null){
                        mediaPlayer.stop();
                    }
                    listViewLagu.getItems().addAll(listFileName);
                    //select first data
                    listViewLagu.getSelectionModel().selectFirst();
                });
            };
            //Thread
            Thread bgTask = new Thread(run);
            bgTask.setDaemon(true);
            bgTask.start();
        }
    }
    
    private void createMediaPlayer(String fullPath){
        try {
            this.media = new Media(new File(fullPath).toURI().toURL().toExternalForm());
            this.mediaPlayer = new MediaPlayer(media);
            buttonPlayOrPause.textProperty().bind(Bindings.when(mediaPlayer.statusProperty().isEqualTo(MediaPlayer.Status.PLAYING)).then("||").otherwise(">"));
            media.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> obj) -> {
                if(obj.wasAdded()){
                    String key = obj.getKey();
                    Object val = obj.getValueAdded();
                    switch(key){
                        case "image":
                            imgAlbum.setImage((Image) val);
                            break;
                        case "artist":
                            labelArtis.setText(String.valueOf(val));
                            break;
                        case "year":
                            labelYear.setText(String.valueOf(val));
                            break;
                        case "title":
                            labelTitle.setText(String.valueOf(val));
                            break;  
                    }
                }
            });
            mediaPlayer.currentTimeProperty().addListener((observable)->{
                updateProgress(mediaPlayer.getCurrentTime());
            });
            //event listener for progress bar
            sliderCurrentTime.valueProperty().addListener((observable) -> {
                if(sliderCurrentTime.isValueChanging()){
                    mediaPlayer.seek(totalDuration.multiply(sliderCurrentTime.getValue()/100));
                }
            });
            mediaPlayer.setOnReady(()->{
                currentDuration.set(mediaPlayer.getCurrentTime());
                totalDuration = mediaPlayer.getTotalDuration();
                labelTotalDuration.setText(formatTime(totalDuration));
            });
            mediaPlayer.setOnEndOfMedia(()->{
                this.goToNextList();
            });
            mediaPlayer.setOnHalted(()->{
                System.out.println("setOnHalted");
            });
            mediaPlayer.setOnStopped(()->{
                System.out.println("setOnStopped");
            });
            // PLAY media
            mediaPlayer.play();
            //binding status
            mediaPlayer.muteProperty().bind(buttonMute.selectedProperty());
            //buttonNext disabled if: current is in last song + Random NOT selected + Repeat NOT selected
            buttonNext.disableProperty().bind(Bindings.when(new SimpleBooleanProperty(currentSeletedList.get() == allSong.length - 1).and(buttonRandom.selectedProperty().not().and(buttonRepeat.selectedProperty().not()))).then(true).otherwise(false));
            mediaPlayer.volumeProperty().bind(sliderVolume.valueProperty().divide(100));
        
        } catch (MalformedURLException ex) {
            System.out.println("ERROR: "+ex.getMessage());
        }
    }
    
    private void playMediaPlayer(int selectedIndex){
        if(selectedIndex > -1){
            String pathName = allSong[selectedIndex].getAbsolutePath();
            pathName = pathName.replace("\\", "/");
            //belum ada player
            if(media != null && mediaPlayer != null){
                mediaPlayer.stop();
            }
            createMediaPlayer(pathName);
        }
    }
    
    public void playOrPause(){
        if(media != null || mediaPlayer != null){
            MediaPlayer.Status currentStatus = mediaPlayer.getStatus();
            switch (currentStatus) {
                case PLAYING:
                    //jika sedang PLAY, lakukan PAUSE
                    mediaPlayer.pause();
                    break;
                case PAUSED:
                    //jika sedang PAUSE, lakukan PLAY
                    mediaPlayer.play();
                    break;
                case UNKNOWN:
                case HALTED:
                    mediaPlayer.stop();
                    break;
                default:
                    this.playMediaPlayer(currentSeletedList.get());
                    break;
            }
        }else{
            playMediaPlayer(currentSeletedList.get());
        }
    }
    
    public void goToNextList(){
        //jika random, pilih berdasarkan random index
        if(buttonRandom.isSelected()){
            int randomIndex = new Random().nextInt(this.allSong.length-1);
            this.listViewLagu.getSelectionModel().clearAndSelect(randomIndex);
            this.listViewLagu.scrollTo(randomIndex);
            //play selected
            playMediaPlayer(currentSeletedList.get());
            //scroll to selected index
            this.listViewLagu.scrollTo(currentSeletedList.get());
        }else if(this.mediaPlayer != null){
            //jika list terpilih adalah last song
            if(currentSeletedList.get() == this.allSong.length - 1){
                //jika repeat selected
                if(buttonRepeat.isSelected()){
                    this.listViewLagu.getSelectionModel().selectFirst();
                    //play selected
                    playMediaPlayer(currentSeletedList.get());
                    //scroll to selected index
                    this.listViewLagu.scrollTo(currentSeletedList.get());
                }else{
                    //stop player, scroll to first, select first
                    this.mediaPlayer.stop();
                    this.listViewLagu.getSelectionModel().selectFirst();
                    this.listViewLagu.scrollTo(0);
                }
            }else{
                //Next play
                this.listViewLagu.getSelectionModel().selectNext();
                //play selected
                playMediaPlayer(currentSeletedList.get());
                //scroll to selected index
                this.listViewLagu.scrollTo(currentSeletedList.get());
            }
        }
        
    }
    
    public void goToProviousList(){
        if(currentSeletedList.get() != 0){
            this.listViewLagu.getSelectionModel().selectPrevious();
            playMediaPlayer(currentSeletedList.get());
            this.listViewLagu.scrollTo(currentSeletedList.get());
        }
    }
    
    private void updateProgress(Duration currentTime){
        sliderCurrentTime.setValue(currentTime.divide(totalDuration.toMillis()).toMillis()*100);
        labelCurrentDuration.setText(formatTime(currentTime));
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
