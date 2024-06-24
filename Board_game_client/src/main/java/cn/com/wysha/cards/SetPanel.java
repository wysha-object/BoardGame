package cn.com.wysha.cards;

import cn.com.wysha.Main;

import javax.swing.*;

public class SetPanel {
    public final String cardName=this.getClass().getName();
    public JPanel panel;
    private JTextField serverIPTextField;
    private JTextField portTextField;
    private JTextField nameTextField;
    private JLabel name;
    private JTextField serverPortTextField;

    public SetPanel(){
        name.setText("当前服务器分配给本机的ID:"+ Main.client.ID);
        serverIPTextField.setText(Main.serverIP);
        serverPortTextField.setText(String.valueOf(Main.serverPort));
        portTextField.setText(Main.port);
        nameTextField.setText(Main.name);
    }
}
