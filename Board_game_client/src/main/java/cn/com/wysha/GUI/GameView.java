package cn.com.wysha.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameView extends JFrame {
    public final static Image black;
    public final static Image white;
    static {
        try {
            black = ImageIO.read(new File("./resources/png/black.png"));
            white = ImageIO.read(new File("./resources/png/white.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JPanel contentPane;
    private JLabel roomName;
    private JLabel roomID;
    private JButton stopButton;
    private JPanel draw;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JButton sureButton;

    public GameView(){
        setContentPane(contentPane);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setUndecorated(true);
        setLocationRelativeTo(null);
    }
}
