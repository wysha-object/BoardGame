package cn.com.wysha;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    public static ArrayList<Room> rooms = new ArrayList<>();
    public final int id;
    public final String name;
    public final Client owner;
    public Client guest;
    public Integer GID=null;
    public final Integer width;
    public final Integer height;

    public Room(String name, Client owner, Integer width, Integer height) {
        this.width = width;
        this.height = height;
        rooms.add(this);
        Random random=new Random();
        int i = random.nextInt();
        boolean run=true;
        while (run){
            run=false;
            for (Room room : rooms) {
                if (room.id==i){
                    run=true;
                    i = random.nextInt();
                    break;
                }
            }
        }
        this.id = i;
        this.name = name;
        this.owner = owner;
    }
    public void setGID(Integer GID){
        this.GID = GID;
    }
    public void removeClient(Client client){
        if (client == guest||client==owner){
            rooms.remove(this);
            try {
                guest.socket.close();
                owner.socket.close();
            } catch (Exception ignored) {
            }
        }
    }
    @Override
    public String toString() {
        return "房间名:"+name+"ID:"+id;
    }
}
