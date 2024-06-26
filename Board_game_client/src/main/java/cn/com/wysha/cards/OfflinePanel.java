package cn.com.wysha.cards;

import cn.com.wysha.GUI.GameView;
import cn.com.wysha.GUI.WarnView;
import cn.com.wysha.Main;
import cn.com.wysha.game.Game;

import javax.swing.*;

public class OfflinePanel {
    public final String cardName=this.getClass().getName();
    public JPanel panel;
    private JSpinner width;
    private JSpinner height;
    private JButton startButton;

    public OfflinePanel() {
        startButton.addActionListener(e -> {
            int w=(Integer) width.getValue();
            int h=(Integer) height.getValue();
            if (w>=5&&h>=5){
                Main.mainForm.setEnabled(false);
                new GameView(new Game(w,h));
            }else {
                new WarnView("无效的棋盘大小,棋盘大小至少为5*5").setVisible(true);
            }
        });
    }
}
