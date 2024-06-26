package cn.com.wysha.GUI;

import javax.swing.*;
import java.awt.*;

public class WarnView extends JDialog {
    private JPanel contentPane;
    private JLabel label;
    private JButton OKButton;

    public WarnView(String title){
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setAlwaysOnTop(true);
        setModal(true);
        setContentPane(contentPane);
        label.setText(title);
        setSize(dimension.width/5,dimension.height/5);
        setLocationRelativeTo(null);
        OKButton.addActionListener(e -> dispose());
    }
}
