package cn.com.wysha;

import cn.com.wysha.GUI.MainForm;
import cn.com.wysha.online.Client;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Main {
    public final static MainForm mainForm;
    private static final String DATA_FILE="./data.xml";
    public static final Client client;
    public static String name;
    public static String serverIP;
    public static Integer serverPort;
    static {
        Client c;
        File file=new File(DATA_FILE);
        if (file.exists()) {
            try {
                Document document=new SAXReader().read(file);
                Element root=document.getRootElement();
                Element settings=root.element("settings");
                name=settings.elementText("name");
                serverIP=settings.elementText("serverIP");
                serverPort= Integer.valueOf(settings.elementText("serverPort"));
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }else {
            name = "user"+new Random().nextInt();
            serverIP="127.0.0.1";
            serverPort=7700;
        }
        try {
            c =new Client(new Socket(serverIP,serverPort));
        } catch (IOException e) {
            c =null;
        }
        client = c;
        mainForm = new MainForm();
    }
    public static void main(String[] args) {
        mainForm.setVisible(true);
        mainForm.addWindowListener(new WindowAdapter() {
            {
                try {
                    File file=new File(DATA_FILE);
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(file));
                    Document document= DocumentHelper.createDocument();
                    Element root=document.addElement("data");
                    Element settings=root.addElement("settings");
                    settings.addElement("name").setText(name);
                    settings.addElement("serverIP").setText(serverIP);
                    settings.addElement("serverPort").setText(String.valueOf(serverPort));
                    xmlWriter.write(document);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
