package cn.com.wysha.GUI;

import cn.com.wysha.cards.*;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame{
    private JPanel contentPane;
    private JPanel buttonsPanel;
    private JButton robotButton;
    private JButton roomListButton;
    private JButton newRoomButton;
    private JPanel mainPanel;
    private JButton homeButton;
    private JButton setButton;
    private final HomePanel homePanel=new HomePanel();
    private final RobotPanel robotPanel=new RobotPanel();
    private final RoomListPanel roomListPanel=new RoomListPanel();
    private final NewRoomPanel newRoomPanel=new NewRoomPanel();
    private final SetPanel setPanel=new SetPanel();
    private final CardLayout cardLayout=new CardLayout();
    public MainForm(){
        setTitle("棋类游戏:五子棋");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(dimension.width/3,dimension.height/3);
        homeButton.setText("主页");
        robotButton.setText("单机模式");
        roomListButton.setText("房间列表");
        newRoomButton.setText("新建房间");
        setButton.setText("设置");
        setLocationRelativeTo(null);
        mainPanel.setLayout(cardLayout);

        mainPanel.add(homePanel.panel,homePanel.cardName);
        mainPanel.add(robotPanel.panel,robotPanel.cardName);
        mainPanel.add(roomListPanel.panel,roomListPanel.cardName);
        mainPanel.add(newRoomPanel.panel,newRoomPanel.cardName);
        mainPanel.add(setPanel.panel,setPanel.cardName);
        homeButton.addActionListener(e -> cardLayout.show(mainPanel,homePanel.cardName));
        robotButton.addActionListener(e -> cardLayout.show(mainPanel,robotPanel.cardName));
        roomListButton.addActionListener(e -> cardLayout.show(mainPanel,roomListPanel.cardName));
        newRoomButton.addActionListener(e -> cardLayout.show(mainPanel,newRoomPanel.cardName));
        setButton.addActionListener(e -> cardLayout.show(mainPanel,setPanel.cardName));
        cardLayout.show(mainPanel,homePanel.cardName);
    }
}
