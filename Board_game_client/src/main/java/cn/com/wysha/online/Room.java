package cn.com.wysha.online;

import cn.com.wysha.GUI.GameView;
import cn.com.wysha.Main;
import cn.com.wysha.game.Game;

public class Room {
    private final String name;
    private final int ID;
    private final Integer owner;
    private final Integer guestID;
    private final Game game;

    public Room(String name,Integer owner, int width, int height) {
        this.name = name;
        this.owner = owner;
        this.game = new Game(width, height);
        try {
            Main.client.write("new_Room");
            Main.client.write(name);
            Main.client.write(String.valueOf(width));
            Main.client.write(String.valueOf(height));
            ID= Integer.parseInt(Main.client.read());

            while (true){
                if (Main.client.read().equals("GID")){
                    break;
                }
            }

            guestID = Integer.parseInt(Main.client.read());

            GameView gameView = new GameView();
            gameView.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
