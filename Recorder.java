package tankgame2;

import java.io.*;
import java.util.Vector;

@SuppressWarnings({"all"})
//用于记录相关信息
public class Recorder {
    //定义变量，记录我方击毁敌人坦克数量
    private static int allEnemyTankNum = 0;
    //定义IO对象,准备写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "f:\\myRecord.txt";
    //定义一个Vector，指向MyPanel对象的敌人坦克Vector
    private static Vector<EnemyTank> enemyTanks = null;

    //定义一个Node 的Vector,用于保存敌人的信息
    private static Vector<Node> nodes  = new Vector<>();
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
    //增加一个方法，用于读取record File，恢复相关信息
    //该方法在继续上局的时候调用
    public static Vector<Node> getNodesAndAllEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            //循环读取文件，生成Node集合
            String line = "";
            while ((line = br.readLine()) != null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),
                                     Integer.parseInt(xyd[1]),
                                     Integer.parseInt(xyd[2]));
               nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return nodes;
    }

    //增加一个方法，当游戏退出时，我们将allEnemyTank保存到recordFile
    //保存地敌人坦克的坐标和方向
    public static void keepRecord(){
        try {
             bw = new BufferedWriter(
                                new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            //遍历敌人坦克的Vector,然后根据情况保存
            //OOP,定义一个属性，然后通过setXxx得到敌人坦克的Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出敌人坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive){//判断敌人坦克是否存活
                    //保存该enemyTank信息
                    String record = enemyTank.getX() + " "
                                  + enemyTank.getY() + " "
                                  + enemyTank.getDirect();
                    //写入到文件
                    bw.write(record + "\r\n");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
    //当我方坦克击毁一辆敌方坦克，就应当 allEnemyTankNum++
    public static void addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
