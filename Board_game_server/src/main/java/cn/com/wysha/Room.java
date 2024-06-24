package cn.com.wysha;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    public static ArrayList<Room> rooms = new ArrayList<>();
    public final int id;
    public final String name;
    private final Client owner;
    private final Client guest;
    public Integer GID;
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
        try {
            owner.write(String.valueOf(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (GID!=null){
                break;
            }
        }
        Client c=null;
        for (Client client:Client.clients){
            if (client.id==id){
                c = client;
                break;
            }
        }
        this.guest=c;
        try {
            owner.write(String.valueOf(GID));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(666);
    }
    public void setGID(Integer GID){
        this.GID = GID;
    }
    @Override
    public String toString() {
        return "房间名:"+name+"ID:"+id;
    }
}
