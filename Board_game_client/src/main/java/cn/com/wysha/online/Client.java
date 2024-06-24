package cn.com.wysha.online;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private final Socket socket;
    public final Integer ID;
    public Client(Socket socket) {
        this.socket = socket;
        try {
            ID= Integer.valueOf(read());
        } catch (Exception e) {
            throw new RuntimeException(e);
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
