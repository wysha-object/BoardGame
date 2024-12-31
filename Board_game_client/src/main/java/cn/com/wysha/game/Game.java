package cn.com.wysha.game;

public class Game {
    //black:127,write:-1,null:0
    public final byte[][] grid;
    public Game(int width, int height){
        this.grid = new byte[width][height];
    }
    public int getW() {
        return grid.length;
    }
    public int getH() {
        return grid[0].length;
    }
    public byte down(byte id,int x, int y){
        if (id==0){
            throw new RuntimeException("Fucking you!我操你妈,你个B!别你妈乱搞啊!");
        }
        try {
            if (grid[x][y] != 0) {
                return -1;
            }else {
                grid[x][y] = id;
                for (int X=0;X<getW();X++){
                    for (int Y=0;Y<getH();Y++){
                        if (grid[X][Y] == 0){
                            continue;
                        }
                        for (int xx = -1; xx < 2; xx++) {
                            for (int yy = -1; yy < 2; yy++) {
                                if (xx == 0 && yy == 0) continue;
                                int xxx=X;
                                int yyy=Y;
                                for (int i = 0; i < 4; i++) {
                                    xxx+=xx;
                                    yyy+=yy;
                                    if (xxx<0||xxx>=getW()||yyy<0||yyy>=getH()) break;
                                    if (grid[xxx][yyy] != id){
                                        break;
                                    }
                                    if (i==3){
                                        return 1;
                                    }
                                }
                            }
                        }
                    }
                }
                return 0;
            }
        }catch (Exception ignored){
            return -1;
        }
    }
}
