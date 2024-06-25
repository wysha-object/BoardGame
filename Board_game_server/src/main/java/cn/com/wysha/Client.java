package cn.com.wysha;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static cn.com.wysha.Main.*;

public class Client implements Runnable {
    public static ArrayList<Client> clients = new ArrayList<>();
    public Integer id;
    public Socket socket;
    public Room room;
    public boolean run=true;
    @Override
    public void run() {
        try {
            socket= serverSocket.accept();
            executorService.execute(new Client());
            logger.info("accept"+socket.getInetAddress());
            Random random=new Random();
            int i = random.nextInt();
            boolean r=true;
            while (r){
                r=false;
                for (Client client : clients) {
                    if (client.id==i){
                        r=true;
                        i = random.nextInt();
                        break;
                    }
                }
            }
            this.id = i;
            clients.add(this);
            write(String.valueOf(this.id));
            while (run){
                String string=read();
                switch(string){
                    case "test"-> write("test");
                    case "exit"->{
                        socket.close();
                        run=false;
                    }
                    case "new_Room"->{
                        this.room=new Room(read(),this,Integer.parseInt(read()),Integer.parseInt(read()));
                        Client owner=room.owner;
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
                            if (room.GID!=null){
                                break;
                            }
                            write("test");
                            read();
                        }
                        Client c=null;
                        for (Client client:Client.clients){
                            if (client.id==id){
                                c = client;
                                break;
                            }
                        }
                        room.guest=c;
                        try {
                            write("GID");
                            owner.write(String.valueOf(room.GID));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        Client guest=room.guest;
                        Room.rooms.remove(room);
                        game(owner, guest);
                        this.room=null;
                    }
                    case "room_List"->{
                        write(String.valueOf(Room.rooms.size()));
                        for (Room room : Room.rooms) {
                            write(room.toString());
                        }
                    }
                    case "join_Room"->{
                        int index= Integer.parseInt(read());
                        Room.rooms.get(index).setGID(Integer.valueOf(read()));
                        this.room= Room.rooms.get(index);
                        write(String.valueOf(room.name));
                        write(String.valueOf(room.width));
                        write(String.valueOf(room.height));
                        Client owner=room.owner;
                        Client guest=room.guest;
                        game(guest, owner);
                        this.room=null;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            clients.remove(this);
            room.removeClient(this);
        }
    }

    private void game(Client user, Client rival) throws Exception {
        boolean winner=false;
        while (true){
            if (user.read().equals("win")){
                winner=true;
            }
            int x = Integer.parseInt(user.read());
            int y = Integer.parseInt(user.read());
            if (winner){
                rival.write("lost");
                rival.write(String.valueOf(x));
                rival.write(String.valueOf(y));
                break;
            }else {
                rival.write(String.valueOf(x));
                rival.write(String.valueOf(y));
            }
        }
    }

    public void write(String string) throws Exception {
        byte[] bytes=string.getBytes();
        socket.getOutputStream().write(
                bytes.length
        );
        socket.getOutputStream().write(
                bytes
        );
    }

    public String read() throws Exception {
        int length=socket.getInputStream().read();
        byte[] data = new byte[length];
        socket.getInputStream().read(data);
        return new String(data, StandardCharsets.UTF_8);
    }
}
