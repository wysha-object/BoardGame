package cn.com.wysha.online;

import cn.com.wysha.GUI.GameView;
import cn.com.wysha.GUI.WaitView;
import cn.com.wysha.Main;
import cn.com.wysha.game.Game;

public class Room {

    public Room(String name, int width, int height) {
        Game game = new Game(width, height);
        try {
            WaitView waitView=new WaitView("等待对手进入房间");
            waitView.setVisible(true);
            Main.client.write("new_Room");
            Main.client.write(name);
            Main.client.write(String.valueOf(width));
            Main.client.write(String.valueOf(height));
            int ID = Integer.parseInt(Main.client.read());

            while (true){
                if (Main.client.read().equals("GID")){
                    break;
                }else {
                    Main.client.write("test");
                }
            }

            String guestName = Main.client.read();

            waitView.dispose();
            new GameView(name, ID,Main.name,guestName, game,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
