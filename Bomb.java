package tankgame2;

public class Bomb {
    int x, y;
    int life = 9;//炸弹生命周期
    boolean isLive = true;//是否还存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;

    }

    //减少生命值
    public void lifeDown() {
        if (life > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            life--;
        } else {
            isLive = false;
        }
    }
}
