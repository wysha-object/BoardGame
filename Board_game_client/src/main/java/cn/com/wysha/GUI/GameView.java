package cn.com.wysha.GUI;

import cn.com.wysha.Main;
import cn.com.wysha.game.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameView {
    public final static Image black;
    public final static Image white;
    static {
        try {
            black = ImageIO.read(new File("./resources/png/black.png"));
            white = ImageIO.read(new File("./resources/png/white.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JPanel contentPane;
    private JLabel roomName;
    private JButton stopButton;
    private JPanel draw;
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JSpinner spinnerX;
    private JSpinner spinnerY;
    private JButton sureButton;
    private JLabel ownerNameLabel;
    private JLabel guestNameLabel;
    private JLabel roomID;
    private JPanel panel;
    private JPanel doPanel;
    final int chessWidth;
    final int chessHeight;
    final int xMax;
    final int yMax;
    final Game game;
    public GameView(String name, Integer ID, String ownerName, String guestName, Game game, boolean owner){
        this.game = game;
        JFrame frame = new JFrame();
        frame.setContentPane(contentPane);
        roomName.setText("房间名:"+name);
        roomID.setText("ID:"+ID);
        ownerNameLabel.setText(ownerName);
        guestNameLabel.setText(guestName);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        xMax=game.getW();
        yMax=game.getH();
        chessWidth= panel.getWidth()/xMax;
        chessHeight= panel.getHeight()/yMax;

        new Thread(() -> {
            while(true){
                frame.repaint();
            }
        }).start();

        if (!owner){
            try {
                doPanel.setEnabled(false);
                if (Main.client.read().equals("lost")){
                    Main.mainForm.setVisible(true);
                }
                int x = Integer.parseInt(Main.client.read());
                int y = Integer.parseInt(Main.client.read());
                game.down((byte) 127, x,y);
                doPanel.setEnabled(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        sureButton.addActionListener(e -> {
            int i=game.down((byte) (owner?127:-1), (Integer) spinnerX.getValue(), (Integer) spinnerY.getValue());
            try {
                if (i!=-1){
                    switch(i){
                        case 1->{
                            WaitView waitV=new WaitView("你赢了");
                            doPanel.setEnabled(false);
                            waitV.setVisible(true);
                            Main.client.write("win");
                            Main.client.write(String.valueOf(spinnerX.getValue()));
                            Main.client.write(String.valueOf(spinnerY.getValue()));
                            Thread.sleep(5000);
                            waitV.dispose();
                            frame.dispose();
                            Main.mainForm.setVisible(true);
                            return;
                        }
                        case 0-> {
                            Main.client.write("test");
                            Main.client.write(String.valueOf(spinnerX.getValue()));
                            Main.client.write(String.valueOf(spinnerY.getValue()));
                        }
                    }
                    WaitView waitView=new WaitView("等待对手下棋");
                    waitView.setVisible(true);
                    doPanel.setEnabled(false);
                    boolean b=false;
                    WaitView waitV=new WaitView("你输了");
                    if (Main.client.read().equals("lost")){
                        doPanel.setEnabled(false);
                        waitView.dispose();
                        waitV.setVisible(true);
                        b=true;
                    }
                    int x = Integer.parseInt(Main.client.read());
                    int y = Integer.parseInt(Main.client.read());
                    game.down((byte) (owner?-1:127), x,y);
                    if (b){
                        Main.client.write("lost");
                        Thread.sleep(5000);
                        waitV.dispose();
                        frame.dispose();
                        Main.mainForm.setVisible(true);
                        return;
                    }
                    doPanel.setEnabled(true);
                    waitView.dispose();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        upButton.addActionListener(e -> spinnerY.setValue((Integer) spinnerY.getValue()-1));
        downButton.addActionListener(e -> spinnerY.setValue((Integer) spinnerY.getValue()+1));
        leftButton.addActionListener(e -> spinnerX.setValue((Integer) spinnerX.getValue()-1));
        rightButton.addActionListener(e -> spinnerX.setValue((Integer) spinnerX.getValue()+1));
    }

    private void createUIComponents() {
        draw = new JPanel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                int w=panel.getWidth();
                int h=panel.getHeight();
                int halfW=chessWidth/2;
                int halfH=chessHeight/2;
                int x;
                int y;
                y=h-halfH;
                for (int i = 0; i <= xMax; i++) {
                    x=i*chessWidth-halfW;
                    g.drawLine(x,halfH,x,y);
                }
                x=w-halfW;
                for (int j = 0; j <= yMax; j++) {
                    y=j*chessHeight-halfH;
                    g.drawLine(halfW,y,x,y);
                }
                for (int i = 0; i < xMax; i++) {
                    for (int j = 0; j < yMax; j++) {
                        if (game.grid[i][j]>0){
                            g.drawImage(black,i*chessWidth+halfW-5,j*chessHeight+halfH-5,null);
                        }else if (game.grid[i][j]<0){
                            g.drawImage(white,i*chessWidth+halfW-5,j*chessHeight+halfH-5,null);
                        }
                    }
                }
            }
        };
        // TODO: place custom component creation code here
    }
}
