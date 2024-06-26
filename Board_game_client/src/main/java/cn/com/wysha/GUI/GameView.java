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
    public final static Image red;
    static {
        try {
            black = ImageIO.read(new File("./resources/png/black.png"));
            white = ImageIO.read(new File("./resources/png/white.png"));
            red = ImageIO.read(new File("./resources/png/red.png"));
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
    private JPanel left;
    private JPanel up;
    private JPanel down;
    final int chessWidth;
    final int chessHeight;
    final int xMax;
    final int yMax;
    final Game game;
    JFrame frame = new JFrame();
    public GameView(Game game) {
        this.game = game;
        frame.setContentPane(contentPane);
        up.setVisible(false);
        down.setVisible(false);
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

        upButton.addActionListener(e -> spinnerY.setValue((Integer) spinnerY.getValue()-1));
        downButton.addActionListener(e -> spinnerY.setValue((Integer) spinnerY.getValue()+1));
        leftButton.addActionListener(e -> spinnerX.setValue((Integer) spinnerX.getValue()-1));
        rightButton.addActionListener(e -> spinnerX.setValue((Integer) spinnerX.getValue()+1));
        stopButton.addActionListener(e -> {
            frame.dispose();
            Main.mainForm.setEnabled(true);
        });
        final boolean[] black = {true};
        sureButton.addActionListener(e -> {
            int i=game.down((byte) (black[0] ?127:-1), (Integer) spinnerX.getValue(), (Integer) spinnerY.getValue());
            if (i!=-1){
                black[0] =!black[0];
                if (i==1){
                    WaitView waitView=new WaitView("有一方赢了");
                    new Thread(() -> {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        waitView.dispose();
                        frame.dispose();
                        Main.mainForm.setEnabled(true);
                    }).start();
                    waitView.setVisible(true);
                }
            }else {
                new WarnView("无效的落子点").setVisible(true);
            }
        });
    }
    public GameView(String name, Integer ID, String ownerName, String guestName, Game game, boolean owner){
        this.game = game;
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

        sureButton.addActionListener(e -> {
            int i=game.down((byte) (owner?127:-1), (Integer) spinnerX.getValue(), (Integer) spinnerY.getValue());
            try {
                if (i!=-1){
                    switch(i){
                        case 1->{
                            WaitView waitV=new WaitView("你赢了");
                            doPanel.setEnabled(false);
                            new Thread(() -> {
                                try {
                                    Main.client.write("win");
                                    Main.client.write(String.valueOf(spinnerX.getValue()));
                                    Main.client.write(String.valueOf(spinnerY.getValue()));
                                    Thread.sleep(5000);
                                    waitV.dispose();
                                    frame.dispose();
                                    Main.mainForm.setEnabled(true);
                                } catch (Exception ex) {
                                    throw new RuntimeException(ex);
                                }
                            }).start();
                            waitV.setVisible(true);
                            return;
                        }
                        case 0-> {
                            Main.client.write("test");
                            Main.client.write(String.valueOf(spinnerX.getValue()));
                            Main.client.write(String.valueOf(spinnerY.getValue()));
                        }
                    }
                    WaitView waitView=new WaitView("等待对手下棋");
                    new Thread(() -> {
                        try {
                            doPanel.setEnabled(false);
                            WaitView waitV=new WaitView("你输了");
                            String s=Main.client.read();
                            if (s.equals("lost")){
                                doPanel.setEnabled(false);
                                waitView.dispose();
                                new Thread(() -> {
                                    try {
                                        int x = Integer.parseInt(Main.client.read());
                                        int y = Integer.parseInt(Main.client.read());
                                        game.down((byte) (owner?-1:127), x,y);
                                        Main.client.write("lost");
                                        Thread.sleep(5000);
                                        waitV.dispose();
                                        frame.dispose();
                                        Main.mainForm.setEnabled(true);
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }).start();
                                waitV.setVisible(true);
                                return;
                            }else if (s.equals("stop")){
                                waitView.dispose();
                                stop(true);
                                return;
                            }
                            int x = Integer.parseInt(Main.client.read());
                            int y = Integer.parseInt(Main.client.read());
                            game.down((byte) (owner?-1:127), x,y);
                            doPanel.setEnabled(true);
                            waitView.dispose();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                    waitView.setVisible(true);
                }else {
                    new WarnView("无效的落子点").setVisible(true);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        upButton.addActionListener(e -> spinnerY.setValue((Integer) spinnerY.getValue()-1));
        downButton.addActionListener(e -> spinnerY.setValue((Integer) spinnerY.getValue()+1));
        leftButton.addActionListener(e -> spinnerX.setValue((Integer) spinnerX.getValue()-1));
        rightButton.addActionListener(e -> spinnerX.setValue((Integer) spinnerX.getValue()+1));
        stopButton.addActionListener(e -> stop(false));
        if (!owner){
            try {
                WaitView waitView=new WaitView("等待对手下棋");
                new Thread(() -> {
                    try {
                        doPanel.setEnabled(false);
                        if (Main.client.read().equals("stop")){
                            waitView.dispose();
                            stop(true);
                            return;
                        }
                        int x = Integer.parseInt(Main.client.read());
                        int y = Integer.parseInt(Main.client.read());
                        game.down((byte) 127, x,y);
                        doPanel.setEnabled(true);
                        waitView.dispose();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                waitView.setVisible(true);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void stop(boolean END){
        try {
            frame.dispose();
            WaitView waitView=new WaitView("有人结束了对局");
            new Thread(() -> {
                try {
                    Main.client.write("stop" + (END?"END":""));
                    Thread.sleep(5000);
                    waitView.dispose();
                    Main.mainForm.setEnabled(true);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }).start();
            waitView.setVisible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
                g.drawImage(red,(Integer) spinnerX.getValue()*chessWidth+halfW-5,(Integer) spinnerY.getValue()*chessHeight+halfH-5,null);
            }
        };
        // TODO: place custom component creation code here
    }
}
