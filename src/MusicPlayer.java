import javax.swing.*;
import java.awt.*;

public class MusicPlayer {

    //constructor - building the music player
    public MusicPlayer(){
        //Song Options
        String[] songList = {"GORG.wav", "Code Kings.wave"};

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
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);


        //labels
        JPanel labelPanel = new JPanel();
        JLabel currentTime = new JLabel("Current Time: 0");
        JLabel totalTime = new JLabel("Total Time: 0");
        labelPanel.add(currentTime);
        labelPanel.add(totalTime);

        //Dropdown
        JPanel dropDown = new JPanel();
        JComboBox<String> songSelector = new JComboBox<>(songList);
        dropDown.add(songSelector);

        //Main panel Order
        panel.add(dropDown);
        panel.add(buttonPanel);
        panel.add(labelPanel);

        //Main frame visible
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MusicPlayer();
    }
}