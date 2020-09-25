package touro.snake;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

public class BackgroundSound {

    Clip clip;

    public void playMusic() {

        try {

            String musicPath = "TheSpectre.wav";

            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(musicPath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(inputStream);
            this.clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }
}
