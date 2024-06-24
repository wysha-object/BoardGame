package cn.com.wysha.game;

public class Game {
    //black:127,write:-1,null:0
    public final byte[][] grid;
    public Game(int width, int height){
        this.grid = new byte[height][width];
    }
    public byte down(byte id,int x, int y){
        if (id==0){
            throw new RuntimeException("Fucking you!我操你妈,你个B!别你妈乱搞啊!");
        }
        if (grid[x][y] != 0) {
            return -1;
        }else {
            grid[x][y] = id;
            for (int xx = -1; xx < 2; xx++) {
                for (int yy = -1; yy < 2; yy++) {
                    if (xx == 0 && yy == 0) continue;
                    int xxx=0;
                    int yyy=0;
                    for (int i = 0; i < 4; i++) {
                        xxx+=xx;
                        yyy+=yy;
                        if (xxx<0||xxx>=grid[0].length||yyy<0||yyy>=grid.length) break;
                        if (grid[xxx][yyy] != id){
                            break;
                        }
                        if (i==3){
                            return 1;
                        }
                    }
                }
            }
            return 0;
        }
    }
}
