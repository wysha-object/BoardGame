package cn.com.wysha.cards;

import cn.com.wysha.GUI.GameView;
import cn.com.wysha.Main;
import cn.com.wysha.game.Game;

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
                Main.mainForm.setVisible(false);
                Main.client.write("join_Room");
                Main.client.write(String.valueOf(index));
                Main.client.write(String.valueOf(Main.client.ID));
                String name=Main.client.read();
                String ownerName=Main.client.read();
                Integer ID= Integer.valueOf(Main.client.read());
                int width=Integer.parseInt(Main.client.read());
                int height=Integer.parseInt(Main.client.read());
                Game game=new Game(width,height);

                new GameView(name,ID,ownerName,Main.name,game,false);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}