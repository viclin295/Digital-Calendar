/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digitalcalendarteam3vw;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Wesley Zheng, Victor Lin - Team 3
 */
public class Alarm {
    
    public Alarm()
    {
    }
    
    public void ringAlarm() throws FileNotFoundException, IOException
    {

        // Input sound file
        String songFile = "Alarm.wav";
        InputStream in = new FileInputStream(songFile);

        // Add the InputStream to an audioStream
        AudioStream audioStream = new AudioStream(in);

        // Play the sound
        AudioPlayer.player.start(audioStream);

    }
}
