package tankgame2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

@SuppressWarnings({"all"})
/**
 * 坦克大战的绘图区域
 */
//面板
//为了监听键盘事件，实现KeyListener
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;
    //定义敌人的坦克
    //说明，当子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向
    Vector<Node> nodes = new Vector<>();
    //定义一个Vector，用于存放炸弹
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 8;
    //定义三张图片，用于显示爆炸
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;


    public MyPanel(String key) {
        nodes = Recorder.getNodesAndAllEnemyTankRec();
        //将MyPanel对象的enemyTanks 设置给Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(200, 700);//初始化自己的坦克
        switch (key) {
            case "1":
                //初始化敌人的坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将enemyTanks 设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);

                    //设置方向
                    enemyTank.setDirect(2);
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该enemyTank，加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, 2);
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2"://继续上局游戏
                //初始化敌人的坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks 设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);

                    //设置方向
                    enemyTank.setDirect(node.getDirect());
                    //启动敌人坦克线程
                    new Thread(enemyTank).start();
                    //给该enemyTank，加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, 2);
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误...");



        }

        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/a.jpg"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/b.jpg"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/c.jpg"));
    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累计击毁敌方坦克:", 1030, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + " ", 1080, 100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充举行
        showInfo(g);
        //画出自己的坦克-封装方法
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //画出hero射击的子弹
        if (hero.shot != null && hero.shot.isLive == true) {
            System.out.println("重绘");
            g.fill3DRect(hero.shot.x, hero.shot.y, 2, 2, false);
        }
        //如果bombs集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //让炸弹生命值减少
            bomb.lifeDown();
            //如果bomb life为0，就从bombs的集合中删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //画出敌人的坦克,遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前坦克是否存活
            if (enemyTank.isLive) {//当敌人坦克是活的才画出该坦克
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出enemyTank 所有的子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.fill3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }

    /**
     * @param x      坦克的左上角x坐标
     * @param y      坦克的左上角y坐标
     * @param g      画笔
     * @param direct 坦克的方向(上下左右)
     * @param type   坦克的类型
     */

    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {

            //根据不同类型设置不同的颜色
            case 0://我们的坦克
                g.setColor(Color.cyan);
                break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);
                break;
        }
        //根据坦克的方向来绘制坦克
        switch (direct) {
            //direct 表示方向（0：向上，1向右，2向下，3向左）
            case 0://向上的
                g.draw3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.draw3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.draw3DRect(x + 10, y + 10, 20, 40, false);//画出坦克子的盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y, x + 20, y + 30);
                break;
            case 1://向右的
                g.draw3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.draw3DRect(x, y + 30, 60, 10, false);//画出坦克右边的轮子
                g.draw3DRect(x + 10, y + 10, 40, 20, false);//画出坦克子的盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2://向下的
                g.draw3DRect(x, y, 10, 60, false);//画出坦克左边的轮子
                g.draw3DRect(x + 30, y, 10, 60, false);//画出坦克右边的轮子
                g.draw3DRect(x + 10, y + 10, 20, 40, false);//画出坦克子的盖子
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3://向右的
                g.draw3DRect(x, y, 60, 10, false);//画出坦克左边的轮子
                g.draw3DRect(x, y + 30, 60, 10, false);//画出坦克右边的轮子
                g.draw3DRect(x + 10, y + 10, 40, 20, false);//画出坦克子的盖子
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void hitEnemyTank() {
        if (hero.shot != null && hero.shot.isLive) {//当前我的子弹还存活
            //遍历敌人所有的坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(hero.shot, enemyTank);
            }
        }
    }

    //编写方法，判断敌人坦克是否击中我的坦克
    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank对象的所有子弹
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                //取出子弹
                Shot shot = enemyTank.shots.get(j);
                //判断shot是否击中我的坦克
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }


    //编写方法，判断我方子弹是否击中敌人坦克
    public void hitTank(Shot s, Tank enemyTank) {
        //判断s击中坦克
        switch (enemyTank.getDirect()) {
            case 0:
            case 2:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当我方的子弹击中敌人的坦克后，将enemyTank从Vector拿掉
                    enemyTanks.remove(enemyTank);
                    //当我们的子弹击毁一个敌人坦克后，将allEnemyTankNum++
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);//将bomb添加到集合bombs
                }
                break;
            case 1:
            case 3:
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //当我方的子弹击中敌人的坦克后，将enemyTank从Vector拿掉
                    enemyTanks.remove(enemyTank);
                    //当我们的子弹击毁一个敌人坦克后，将allEnemyTankNum++
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);//将bomb添加到集合bombs
                }
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {//按下W键
            //就改变坦克的方向
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }
        //如果用户按下的时J就发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //判断当前hero的子弹是否消亡
            if (hero.shot == null || !hero.shot.isLive) {
                hero.shotEnemyTank();
            }

        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔100毫秒，重回区域
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中了敌人坦克
            hitEnemyTank();
            hitHero();
            this.repaint();
        }
    }
}
