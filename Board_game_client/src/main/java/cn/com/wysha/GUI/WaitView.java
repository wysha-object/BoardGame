package cn.com.wysha.GUI;

import javax.swing.*;
import java.awt.*;

public class WaitView extends JDialog {
    private JPanel contentPane;
    private JLabel label;
    public WaitView(String title){
        //setAlwaysOnTop(true);
        setModal(true);
        setContentPane(contentPane);
        label.setText(title);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setUndecorated(true);
        setOpacity(0.5f);
        setLocationRelativeTo(null);
    }
}
