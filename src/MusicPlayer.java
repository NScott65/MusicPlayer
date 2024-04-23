import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    //Class Variables
    private Clip clip;
    private Timer timer;
    private JLabel currentTime;
    private JLabel totalTime;
    private long time = 0;
    private String currentSong;



    //constructor - building the music player
    public MusicPlayer(){

        //Song Options
        String[] songList = {"GORG.wav", "Code Kings.wav","Dumbbells.wav","Fire in My Belly.wav","For Loop.wav", "GUI Mastermind.wav", "Hashin_in the Code.wav",
        "Hey Papi.wav","Mr.Scott.wav","Programming.wav","The Boolean Blues.wav","The Codebreaker_s Fury.wav"};

        //Frame
        JFrame frame = new JFrame("Music Player");
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1,10,10));

        //Buttons
        JPanel buttonPanel = new JPanel();
        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");
        JButton forwardButton = new JButton("Forward 15s");
        JButton backwardButton = new JButton("Backward 15s");
        buttonPanel.add(backwardButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(forwardButton);



        //labels
        JPanel labelPanel = new JPanel();
        currentTime = new JLabel("Current Time: 0");
        totalTime = new JLabel("Total Time: 0");
        labelPanel.add(currentTime);
        labelPanel.add(totalTime);

        //Dropdown
        JPanel dropDown = new JPanel();
        JComboBox<String> songSelector = new JComboBox<>(songList);
        dropDown.add(songSelector);

        //Button Function
        playButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){


                if (clip == null) {
                    playMusic(songSelector.getSelectedItem().toString());
                    startTimer();
                    updateLabels();
                    currentSong = songSelector.getSelectedItem().toString();
                }else if(!clip.isRunning()){
                    if(currentSong.equals(songSelector.getSelectedItem().toString())){

                            if (clip.getMicrosecondPosition() == clip.getMicrosecondLength()) {
                                playMusic(songSelector.getSelectedItem().toString());
                                clip.setMicrosecondPosition(0);
                                startTimer();
                                updateLabels();
                                currentSong = songSelector.getSelectedItem().toString();
                            } else {
                                playMusic(songSelector.getSelectedItem().toString());
                                startTimer();
                                updateLabels();
                                currentSong = songSelector.getSelectedItem().toString();
                            }
                    }else{
                        currentSong = songSelector.getSelectedItem().toString();
                    }
                }else {
                    clip.stop();
                    playMusic(songSelector.getSelectedItem().toString());
                    clip.setMicrosecondPosition(0);
                    startTimer();
                    updateLabels();
                    currentSong = songSelector.getSelectedItem().toString();
                }
            }
        });

        pauseButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                pauseMusic(songSelector.getSelectedItem().toString());
                time = clip.getMicrosecondPosition();
                stopTimer();
                updateLabels();
            }
        });

        forwardButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                fastForward();
            }
        });

        backwardButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                fastBackward();
            }
        });





        //Main panel Order
        panel.add(dropDown);
        panel.add(buttonPanel);
        panel.add(labelPanel);

        //Main frame visible
        frame.add(panel);
        frame.setVisible(true);
    }

    public void playMusic(String selectedSong){

        try{
           File file = new File(System.getProperty("user.dir") + "\\src\\audio\\" + selectedSong);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(time);
            clip.start();
        }
        catch(IOException | UnsupportedAudioFileException | LineUnavailableException e){
            e.printStackTrace();
        }
    }

    public void pauseMusic(String selectedSong){
        if(clip != null && clip.isRunning()){
            clip.stop();
        }
    }

    public void startTimer(){
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateLabels();
            }
        });
        timer.start();
    }

    public void fastForward(){
        if(clip != null && clip.getMicrosecondLength() - clip.getMicrosecondPosition() > 15000000){
            clip.setMicrosecondPosition(clip.getMicrosecondPosition() + 15000000);
        }else if(clip != null && clip.getMicrosecondLength() - clip.getMicrosecondPosition() < 15000000){
            clip.setMicrosecondPosition(clip.getMicrosecondLength());
            updateLabels();
        }
    }

    public void fastBackward(){
        if(clip != null ){
            clip.setMicrosecondPosition(clip.getMicrosecondPosition() - 15000000);
        }
    }

    public void updateLabels(){
        if(clip != null && clip.isOpen()){
            if (clip.isRunning()){
                currentTime.setText("Current Time: " + clip.getMicrosecondPosition() / 1000000 + "s");
                totalTime.setText("Total Time: " + clip.getMicrosecondLength() / 1000000 + "s");
            }
        }
    }

    public void stopTimer(){
        if(timer != null){
            timer.stop();
        }
    }



    public static void main(String[] args) {
        new MusicPlayer();
    }
}