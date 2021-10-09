package com.example.a30797.hljunavigationsystem.schoolgate;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

public class MyGraph {
    public static Point[] points=new Point[]{
            new Point(0,0,"目前位置",9999),//0
            //主节点(景点介绍中的景点）
            new Point(37.532649,122.06709,"南门",0),//0
            new Point(37.536654,122.069487,"艺术学院（音乐舞蹈楼）",1),//1
            new Point(37.533603,122.068323,"知行会堂",2),//2
            new Point(37.534088,122.068973,"知行楼",3),//3
            new Point(37.534817,122.067462,"海洋学院",4),//4
            new Point(37.535754,122.067528,"商学院",5),//5
            new Point(37.533389,122.06406,"外籍专家公寓（快递收发点）",6),//6

            new Point(37.53347,122.06009,"外卖点",7),//7
            new Point(37.534052,122.062932,"浴池",8),//8
            new Point(37.53545,122.063721,"荟园餐厅",9),//9
            new Point(37.535502,122.064274,"馨园餐厅",10),//6
            new Point(37.535994,122.062419,"泰园餐厅",11),//6
            new Point(37.537339,122.064141,"雀园餐厅(大学生活动中心)",12),//6

            new Point(37.537552,122.065818,"法学院",13),//6
            new Point(37.538067,122.066675,"图书馆",14),//6
            new Point(37.53747,122.067496,"数学与统计学院",15),//6
            new Point(37.538369,122.066743,"玲珑学堂",16),//6
            new Point(37.538783,122.066193,"电子楼（联合理学院）",17),//6
            new Point(37.538727,122.068747,"文学楼（文化传播学院）",18),//6
            new Point(37.533076,122.068246,"机电与信息工程学院",19),//6
            new Point(37.537189,122.069009,"科学实验楼",20),//6

            new Point(37.538523,122.0694,"闻外楼（翻译学院、马列部）",21),//6
            new Point(37.538924,122.064647,"田径场",22),//6
            new Point(37.539924,122.062113,"东北亚学院",23),//6
            new Point(37.539689,122.062753,"体育馆",24),//6
            new Point(37.539113,122.063027,"游泳馆",25),//6
            new Point(37.540902,122.063019,"闻天楼（空间科学与物理学院）",26),//6
            new Point(37.538743,122.067219,"网络楼",27),//6
            new Point(37.542967,122.062226,"天文台",28),//6
//至此共29个景点，呼应全局视图中的drawPoints以及Attractions_info中的景点信息
            //辅助点（路径） index以1开头，共39个
            new Point(37.540741,122.062425,"辅助点",101),//6
            new Point(37.540716,122.063723,"辅助点",102),//6
            new Point(37.540462,122.065048,"辅助点",103),//6
            new Point(37.540462,122.065048,"辅助点",104),//6
            new Point(37.538448,122.062995,"辅助点",105),//6
            new Point(37.539593,122.062456,"辅助点",106),//6
            new Point(37.53759,122.063107,"辅助点",107),//6
            new Point(37.537679,122.064253,"辅助点",108),//6
            new Point(37.538613,122.065308,"辅助点",109),//6
            new Point(37.536731,122.063197,"辅助点",110),//6
            new Point(37.536789,122.064343,"辅助点",111),//6
            new Point(37.535723,122.062789,"辅助点",112),//6
            new Point(37.535741,122.063597,"辅助点",113),//6
            new Point(37.535837,122.064504,"辅助点",114),//6
            new Point(37.534757,122.064617,"辅助点",115),//6
            new Point(37.534632,122.062901,"辅助点",116),//6
            new Point(37.53412,122.062973,"辅助点",117),//6
            new Point(37.533859,122.064042,"辅助点",118),//6
            new Point(37.534439,122.064679,"辅助点",119),//6
            new Point(37.534442,122.066674,"辅助点",120),//6
            new Point(37.534721,122.067154,"辅助点",121),//6
            new Point(37.536005,122.067118,"辅助点",122),//6
            new Point(37.537247,122.066651,"辅助点",123),//6
            new Point(37.537286,122.067662,"辅助点",124),//6
            new Point(37.537393,122.065353,"辅助点",125),//6
            new Point(37.538041,122.068704,"辅助点",126),//6
            new Point(37.538041,122.068704,"辅助点",127),//6
            new Point(37.536152,122.068704,"辅助点",128),//6
            new Point(37.536199,122.069661,"辅助点",129),//6
            new Point(37.534557,122.068735,"辅助点",130),//6
            new Point(37.534496,122.068084,"辅助点",131),//6
            new Point(37.533806,122.068026,"辅助点",132),//6
            new Point(37.533233,122.068066,"辅助点",133),//6
            new Point(37.538041,122.068704,"辅助点",134),//6
            new Point(37.538631,122.066993,"辅助点",135),//6
            new Point(37.538627,122.066166,"辅助点",136),//6
            new Point(37.53739,122.065789,"辅助点",137),//6
            new Point(37.534313,122.062973,"辅助点",138),//6
            new Point(37.534464,122.064644,"辅助点",139),//6
    };
//步行边：
    public static Edge[] edges=new Edge[]{
            //78条边 index 1~78
            new Edge(101,28,520,1),
            new Edge(101,102,100,2),
            new Edge(102,26,20,3),
            new Edge(102,103,140,4),
            new Edge(103,104,120,5),
            new Edge(104,22,10,6),
            new Edge(22,105,70,7),
            new Edge(105,106,140,8),
            new Edge(106,25,10,9),
            new Edge(106,23,10,10),
            new Edge(101,106,120,11),
            new Edge(105,107,240,12),
            new Edge(107,108,100,13),
            new Edge(108,12,10,14),
            new Edge(108,109,110,15),
            new Edge(104,109,90,16),
            new Edge(109,125,100,17),
            new Edge(111,125,110,18),
            new Edge(108,111,100,19),
            new Edge(111,110,110,20),
            new Edge(110,107,100,21),
            new Edge(110,112,150,22),
            new Edge(112,11,30,23),
            new Edge(112,113,70,24),
            new Edge(113,114,80,25),
            new Edge(111,114,110,26),
            new Edge(114,10,20,27),
            new Edge(114,115,120,28),
            new Edge(115,116,150,29),
            new Edge(112,116,120,30),
            new Edge(116,138,30,31),
            new Edge(117,8,10,32),
            new Edge(117,118,130,33),
            new Edge(118,6,50,34),
            new Edge(118,119,120,35),
            new Edge(119,115,30,36),
            new Edge(119,120,190,37),
            new Edge(120,0,200,38),
            new Edge(120,121,60,39),
            new Edge(121,131,100,40),
            new Edge(131,132,100,41),
            new Edge(132,133,50,42),
            new Edge(132,2,10,43),
            new Edge(133,19,10,44),
            new Edge(131,130,70,45),
            new Edge(130,3,30,46),
            new Edge(130,128,180,47),
            new Edge(128,129,80,48),
            new Edge(129,1,10,49),
            new Edge(122,128,140,50),
            new Edge(122,5,10,51),
            new Edge(122,121,140,52),
            new Edge(121,4,10,53),
            new Edge(114,122,240,54),
            new Edge(122,123,130,55),
            new Edge(123,137,60,56),
            new Edge(125,137,40,57),
            new Edge(137,13,10,58),
            new Edge(123,14,50,59),
            new Edge(123,124,60,60),
            new Edge(124,15,10,61),
            new Edge(124,126,150,62),
            new Edge(126,127,110,63),
            new Edge(127,20,10,64),
            new Edge(127,128,100,65),
            new Edge(126,134,40,66),
            new Edge(134,21,60,67),
            new Edge(134,18,20,68),
            new Edge(134,135,130,69),
            new Edge(135,16,10,70),
            new Edge(135,27,10,71),
            new Edge(135,136,80,72),
            new Edge(136,17,10,73),
            new Edge(109,136,80,74),
            new Edge(138,117,20,75),
            new Edge(138,119,150,76),
            new Edge(24,25,10,77),
            new Edge(113,9,10,78),
            new Edge(117,7,240,79),
            new Edge(111,113,136,80),
            new Edge(111,109,148,81),
            new Edge(130,132,160,82),
            new Edge(114,125,156,83)
    };
    //车行边
    public static Edge[] bikeEdges=new Edge[]{
            //78条边 index 1~78
            new Edge(101,28,520,1),
            new Edge(101,102,100,2),
            new Edge(102,26,20,3),
            new Edge(102,103,140,4),
            new Edge(103,104,120,5),
            new Edge(104,22,10,6),
            new Edge(22,105,70,7),
            new Edge(105,106,140,8),
            new Edge(106,25,10,9),
            new Edge(106,23,10,10),
            new Edge(101,106,120,11),
            new Edge(105,107,240,12),
            new Edge(107,108,100,13),
            new Edge(108,12,10,14),
            new Edge(108,109,110,15),
            new Edge(104,109,90,16),
            new Edge(109,125,100,17),
            new Edge(111,125,110,18),
            new Edge(108,111,100,19),
            new Edge(111,110,110,20),
            new Edge(110,107,100,21),
            new Edge(110,112,150,22),
            new Edge(112,11,30,23),
            new Edge(112,113,70,24),
            new Edge(113,114,80,25),
            new Edge(111,114,110,26),
            new Edge(114,10,20,27),
            new Edge(114,115,120,28),
            new Edge(115,116,150,29),
            new Edge(112,116,120,30),
            new Edge(116,138,30,31),
            new Edge(117,8,10,32),
            new Edge(117,118,130,33),
            new Edge(118,6,50,34),
            new Edge(118,119,120,35),
            new Edge(119,115,30,36),
            new Edge(119,120,190,37),
            new Edge(120,0,200,38),
            new Edge(120,121,60,39),
            new Edge(121,131,100,40),
            new Edge(132,133,50,42),
            new Edge(132,2,10,43),
            new Edge(133,19,10,44),
            new Edge(131,130,70,45),
            new Edge(130,3,30,46),
            new Edge(130,128,180,47),
            new Edge(128,129,80,48),
            new Edge(129,1,10,49),
            new Edge(122,128,140,50),
            new Edge(122,5,10,51),
            new Edge(122,121,140,52),
            new Edge(121,4,10,53),
            new Edge(114,122,240,54),
            new Edge(123,137,60,56),
            new Edge(125,137,40,57),
            new Edge(137,13,10,58),
            new Edge(123,14,50,59),
            new Edge(123,124,60,60),
            new Edge(124,15,10,61),
            new Edge(124,126,150,62),
            new Edge(126,127,110,63),
            new Edge(127,20,10,64),
            new Edge(127,128,100,65),
            new Edge(134,21,60,67),
            new Edge(134,18,20,68),
            new Edge(134,135,130,69),
            new Edge(135,16,10,70),
            new Edge(135,27,10,71),
            new Edge(135,136,80,72),
            new Edge(136,17,10,73),
            new Edge(109,136,80,74),
            new Edge(138,117,20,75),
            new Edge(138,119,150,76),
            new Edge(24,25,10,77),
            new Edge(113,9,10,78),
            new Edge(117,7,240,79),
            new Edge(130,132,160,82),
    };

    private int startArrayIndex;
    private int endArrayIndex;
    private static double distance;

    private class Node{
        int to;
        double dis;
        public Node(int to, double dis) {
            this.to = to;
            this.dis = dis;
        }
    }
//构造函数
    public MyGraph(int startIndex, int endIndex) {
        this.startArrayIndex = getArrayIndex(startIndex);
        this.endArrayIndex = getArrayIndex(endIndex);
    }
//由点的index属性定位点在points[]数组中的下标（因为数组下标和index不完全对应）
    public static int getArrayIndex(int index){
        for(int i=0;i<points.length;i++){
            if(index==points[i].index){
                return i;
            }
        }
        return 0;
    }

    public static Comparator<Node> comparator = new Comparator<Node>(){
        @Override
        public int compare(Node n1, Node n2) {
            return (int)(n1.dis-n2.dis);
        }
    };

    private void link(Vector<Node>[] G){
        Point temp=null;
        //先将min设置为∞
        double minn=Double.MAX_VALUE;
        Point startPoint=points[startArrayIndex];
        for(int i=1;i<points.length;i++){
            //计算startPoint到其他点的距离
            double dis=DistanceUtil.getDistance(new LatLng(startPoint.latitude,startPoint.longitude),new LatLng(points[i].latitude,points[i].longitude));
            //找startpoint到其他某点的最小距离
            //temp存点；minn存最小distance
            if(dis<minn){
                minn=dis;
                temp=points[i];
            }
        }
        //存为Node（彼此的距离为10）
        G[startArrayIndex].add(new Node(getArrayIndex(temp.index),10));
        G[getArrayIndex(temp.index)].add(new Node(startArrayIndex,10));
    }
    //计算edge两端点间distance
    private double getDistance(Edge e){
        Point p1=points[getArrayIndex(e.from)];
        Point p2=points[getArrayIndex(e.to)];
        return DistanceUtil.getDistance(new LatLng(p1.latitude,p1.longitude),new LatLng(p2.latitude,p2.longitude));
    }

    public static double getDistance() {
        return distance;
    }
    //迪杰斯特拉算法 步行
    public Point[] dijkstra(boolean flag){
        long time=System.currentTimeMillis();
        Vector<Node>[] G=new Vector[points.length];
        for(int i=0;i<points.length;i++)
            G[i]=new Vector<>();
        //初始化各个edge的distance
        for(Edge e:edges) {
            double dis=getDistance(e);
            G[getArrayIndex(e.from)].add(new Node(getArrayIndex(e.to), dis));
            G[getArrayIndex(e.to)].add(new Node(getArrayIndex(e.from), dis));
        }
        if(flag) link(G);
        double[] d=new double[points.length];

        for(int i=0;i<d.length;i++)
            d[i]=Double.MAX_VALUE;
        d[startArrayIndex]=0;

        int[] pre=new int[points.length];

        PriorityQueue<Node> q=new PriorityQueue<>(999,comparator);
        q.add(new Node(startArrayIndex,0));
        while (!q.isEmpty()){
            Node t=q.remove();
            int v=t.to;
            if(d[v]< t.dis)continue;
            for(Node u:G[v]){
                if(d[u.to]>d[v]+u.dis){
                    d[u.to]=d[v]+u.dis;
                    q.add(new Node(u.to,d[u.to]));
                    pre[u.to]=v;
                }
            }
        }

        distance=d[endArrayIndex];

        Vector<Point> path=new Vector<>();
        int temp=endArrayIndex;
        while(temp!=startArrayIndex){
            path.add(points[temp]);
            temp=pre[temp];
        }
        path.add(points[temp]);

        Point[] tempPath=new Point[path.size()];
        return path.toArray(tempPath);
    }

    //迪杰斯特拉算法(车行）
    public Point[] bikeDijkstra(boolean flag){
        long time=System.currentTimeMillis();
        Vector<Node>[] G=new Vector[points.length];
        for(int i=0;i<points.length;i++)
            G[i]=new Vector<>();
        //初始化各个edge的distance
        for(Edge e:bikeEdges) {
            double dis=getDistance(e);
            G[getArrayIndex(e.from)].add(new Node(getArrayIndex(e.to), dis));
            G[getArrayIndex(e.to)].add(new Node(getArrayIndex(e.from), dis));
        }
        if(flag) link(G);
        double[] d=new double[points.length];

        for(int i=0;i<d.length;i++)
            d[i]=Double.MAX_VALUE;
        d[startArrayIndex]=0;

        int[] pre=new int[points.length];

        PriorityQueue<Node> q=new PriorityQueue<>(999,comparator);
        q.add(new Node(startArrayIndex,0));
        while (!q.isEmpty()){
            Node t=q.remove();
            int v=t.to;
            if(d[v]< t.dis)continue;
            for(Node u:G[v]){
                if(d[u.to]>d[v]+u.dis){
                    d[u.to]=d[v]+u.dis;
                    q.add(new Node(u.to,d[u.to]));
                    pre[u.to]=v;
                }
            }
        }

        distance=d[endArrayIndex];

        Vector<Point> path=new Vector<>();
        int temp=endArrayIndex;
        while(temp!=startArrayIndex){
            path.add(points[temp]);
            temp=pre[temp];
        }
        path.add(points[temp]);

        Point[] tempPath=new Point[path.size()];
        return path.toArray(tempPath);
    }
}
