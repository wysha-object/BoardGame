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
            boolean run=true;
            while (run){
                String string=read();
                switch(string){
                    case "exit"->{
                        socket.close();
                        run=false;
                    }
                    case "new_Room"-> this.room=new Room(read(),this,Integer.parseInt(read()),Integer.parseInt(read()));
                    case "room_List"->{
                        write(String.valueOf(Room.rooms.size()));
                        for (Room room : Room.rooms) {
                            write(room.toString());
                        }
                    }
                    case "join_Room"->{
                        int index= Integer.parseInt(read());
                        Room.rooms.get(index).setGID(Integer.valueOf(read()));
                        Room room= Room.rooms.get(index);
                        write(String.valueOf(room.name));
                        write(String.valueOf(room.width));
                        write(String.valueOf(room.height));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            clients.remove(this);
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
