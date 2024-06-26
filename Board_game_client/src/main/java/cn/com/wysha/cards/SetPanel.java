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
    private JButton save;

    public SetPanel(){
        if (Main.client!=null){
            name.setText("当前服务器分配给本机的ID:"+ Main.client.ID);
        }else {
            name.setText("离线模式");
        }
        serverIPTextField.setText(Main.serverIP);
        serverPortTextField.setText(String.valueOf(Main.serverPort));
        nameTextField.setText(Main.name);
        save.addActionListener(e->{
            Main.serverIP=serverIPTextField.getText();
            Main.serverPort=Integer.parseInt(portTextField.getText());
            Main.name=nameTextField.getText();
        });
    }
}
