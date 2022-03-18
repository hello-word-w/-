package tankgame2;

public class Shot implements Runnable{
    int x;//子弹的x坐标
    int y;//子弹的y坐标
    int direct = 0;//子弹的方向
    int speed = 5;//子弹的速度
    boolean isLive = true;//子弹是否存活


    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {//射击行为
        while (true){
            //休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变下，y坐标
            //根据方向来改变x，y坐标
            switch (direct){
                case 0://向上
                    y -= speed;
                    break;
                case 1://向右
                    x += speed;
                    break;
                case 2://向下
                    y += speed;
                    break;
                case 3://向左
                    x -= speed;
                    break;
            }

            //测试
            System.out.println("子弹 x=" + x + " y=" + y);
            //当子碰到敌人坦克时，退出
            if (!(x > 0 && x <1000 && y > 0 && y <750)){
                isLive = false;
                break;
            }
        }
    }
}
