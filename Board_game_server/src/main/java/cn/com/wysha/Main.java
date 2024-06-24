package cn.com.wysha;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {
    static final int PORT;
    static Logger logger = Logger.getLogger("Main");
    static FileHandler fileHandler;
    static ServerSocket serverSocket;
    static ExecutorService executorService = Executors.newCachedThreadPool();

    static {
        try {
            logger.setUseParentHandlers(true);
            fileHandler = new FileHandler("./Server.log");
            logger.addHandler(fileHandler);
            logger.info("start");
            System.out.println("port:");
            PORT = new Scanner(System.in).nextInt();
            serverSocket = new ServerSocket(PORT);
            logger.info("server start");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            executorService.execute(new Client());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}