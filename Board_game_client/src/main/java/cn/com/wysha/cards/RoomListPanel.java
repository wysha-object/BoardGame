package cn.com.wysha.cards;

import cn.com.wysha.Main;
import cn.com.wysha.game.Game;
import cn.com.wysha.online.Room;

import javax.swing.*;

public class RoomListPanel{
    public final String cardName=this.getClass().getName();
    public JPanel panel;
    private JList<String> list;
    private JButton joinButton;
    private JButton flushButton;
    int index;
    public RoomListPanel(){
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(e -> {
            index = list.getLeadSelectionIndex();
            joinButton.setEnabled(index != -1);
        });
        flushButton.addActionListener(e -> {
            try {
                Main.client.write("room_List");
                int size= Integer.parseInt(Main.client.read());
                String[] rooms = new String[size];
                for(int i=0;i<size;i++){
                    rooms[i]=Main.client.read();
                }
                list.setListData(rooms);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        joinButton.addActionListener(e -> {
            try {
                System.out.println("已进入房间");
                Main.mainForm.setEnabled(false);
                Main.client.write("join_Room");
                Main.client.write(String.valueOf(index));
                Main.client.write(String.valueOf(Main.client.ID));
                String name=Main.client.read();
                int width=Integer.parseInt(Main.client.read());
                int height=Integer.parseInt(Main.client.read());
                Game game=new Game(width,height);
                System.out.println(666);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}