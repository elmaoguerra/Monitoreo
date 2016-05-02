/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package maoguerra007.monitoreo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import maoguerra007.monitoreo.util.Util;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.AudioDevice;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 *
 * @author Mauricio Guerra Cubillos <maoguerra007 at gmail.com>
 */
public class Video extends JFrame {

    private JPanel contenedor = null;
    private EmbeddedMediaPlayerComponent mediaPlayerBanner;
    private EmbeddedMediaPlayerComponent mediaPlayer;
    private MediaPlayer mp;

    public Video() {
        this.setLayout(null);
        this.setTitle("Home");
        this.setBounds(0, 0, 1600, 900);
        this.setUndecorated(true);

        contenedor = new JPanel();
        contenedor.setLayout(null);
        this.setContentPane(contenedor);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel fondo = new JLabel(new javax.swing.ImageIcon(Util.rutaImagenes + "Fondos\\fondo.jpg"));
        fondo.setBounds(0, 0, 1600, 900);


        setVisible(true);
        mediaPlayerBanner = new EmbeddedMediaListPlayerComponent();
        mediaPlayerBanner.setBounds(0, 0, 1600, 200);
        mediaPlayerBanner.getMediaPlayer().setRepeat(true);
        mediaPlayerBanner.getMediaPlayer().mute(true);

        mediaPlayer = new EmbeddedMediaPlayerComponent() {
            @Override
            protected String[] onGetMediaPlayerFactoryExtraArgs() {
                return new String[]{"--no-osd"}; // Disables the display of the snapshot filename (amongst other things)
            }
        };
        mediaPlayer.setBounds(368, 270, 864, 486);
        //mediaPlayer = new EmbeddedMediaListPlayerComponent(); //validar esto

        contenedor.add(mediaPlayerBanner);
        contenedor.add(mediaPlayer);
        contenedor.add(fondo);//La imagen de fondo va de ultimo, ultima capa

        mp = mediaPlayer.getMediaPlayer();
        mp.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            @Override
            public void finished(MediaPlayer mp) {
                cerrarApp();
            }
        });
        for (AudioDevice audioDevice : mp.getAudioOutputDevices()) {
            if (audioDevice.getLongName().contains("Altavoces")) {
                mp.setAudioOutputDevice(null, audioDevice.getDeviceId());
            }
        }

        mediaPlayerBanner.getMediaPlayer().playMedia(Util.banner);

        abrirVideo("C:\\VKSavatar\\proyectos\\Avatar\\Temas\\Basico\\Archivos\\Videos Avatar\\video2.mp4");

        this.setVisible(true); //visible antes de reproducir
    }

    private void abrirVideo(String video) {


        mediaPlayer.getMediaPlayer().playMedia(video);
    }

    private void cerrarApp() {
        mediaPlayerBanner.release();
        mp.stop();
        mediaPlayer.release();
        System.exit(0);
    }

}
