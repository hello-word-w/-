package tankgame2;

import java.util.Vector;

@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {
    //在敌人坦克类，使用Vector，保存多个Shot
    Vector<Shot> shots = new Vector<>();
    //增加成员，EnemyTank 可以得到敌人坦克的Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();

    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //在MyPanel调用setEnemyTank()，把Vector<EnemyTank> enemyTanks成员
    //给到每个敌人坦克对象
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //编写方法，判断当前敌人的坦克是否和enemyTanks 中的其他坦克发生重叠或者碰撞
    public boolean isTouchEnemyTank() {
        //判断当前敌人坦克（this）方向
        switch (this.getDirect()) {
            case 0://向上
                //让当前敌人坦克和其他所有的敌人坦克比较，不和自己比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中，取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//不和自己比较
                        //如果敌人坦克方向是上/下
                        //1.如果敌人的坦克方向是上/下
                        //敌人坦克的X范围是    [enemyTank.getX(),enemyTank.getX() + 40]
                        //敌人坦克的Y范围是    [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前this.坦克左上角的坐标[this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前this.坦克右上角的坐标[this.getX()+40,this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 < enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人的坦克是右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 < enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://向右
                //让当前敌人坦克和其他所有的敌人坦克比较，不和自己比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中，取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//不和自己比较
                        //如果敌人坦克方向是上/下
                        //1.如果敌人的坦克方向是上/下
                        //敌人坦克的X范围是    [enemyTank.getX(),enemyTank.getX() + 40]
                        //敌人坦克的Y范围是    [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前this.坦克左上角的坐标[this.getX() +60,this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 < enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前this.坦克右下角的坐标[this.getX()+60,this.getY() +40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 < enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人的坦克是右/左
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 < enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标[this.getX() + 60,this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 < enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://向下
                //让当前敌人坦克和其他所有的敌人坦克比较，不和自己比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中，取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//不和自己比较
                        //如果敌人坦克方向是上/下
                        //1.如果敌人的坦克方向是上/下
                        //敌人坦克的X范围是    [enemyTank.getX(),enemyTank.getX() + 40]
                        //敌人坦克的Y范围是    [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前this.坦克左下角的坐标[this.getX(),this.getY()+60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前this.坦克右下角的坐标[this.getX()+40,this.getY() +60]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 < enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人的坦克是右/左
                        //1.敌人[x,x+60
                        //      y,y+40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标[this.getX() + 40,this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 < enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://向左
                //让当前敌人坦克和其他所有的敌人坦克比较，不和自己比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //从Vector中，取出一个敌人坦克
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {//不和自己比较
                        //如果敌人坦克方向是上/下
                        //1.如果敌人的坦克方向是上/下
                        //敌人坦克的X范围是    [enemyTank.getX(),enemyTank.getX() + 40]
                        //敌人坦克的Y范围是    [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前this.坦克左下角的坐标[this.getX(),this.getY()+60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前this.坦克右下角的坐标[this.getX()this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }

                        }
                        //如果敌人的坦克是右/左
                        //1.敌人[x,x+60
                        //      y,y+40]
                        //当前坦克左上角坐标[this.getX(),this.getY()]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标[this.getX()、,this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() < enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override

    public void run() {
        while (true) {
            //这里判断如果shots size = 0,就创建一颗子弹放入到shots集合并启动
            if (isLive && shots.size() == 0) {
                Shot s = null;
                //判断坦克的方向，创建对应的子弹
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }

            //根据坦克的方向继续移动，随机改变方向
            switch (getDirect()) {
                case 0://向上
                    //让坦克保持一个方向走30步以内的随机步数
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1://向右
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2://向下
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://向左
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDirect((int) (Math.random() * 4));
            if (!isLive) {
                break;//退出线程
            }
        }
    }
}
