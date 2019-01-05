import java.util.*;

public class Main {

    static int len = 8; //地板长度，得是2的幂，这里取8做个demo
    static int[][] LG = new int[len][len]; //模拟地板的二维数组，初始化时的水泥点为-1，其余都是0，而后将所有0都变为砖块的序号来表示已经铺完
    static int index = 1; //全局铺砖的序号，每铺一块就加1
	//坐标顶点类，用于存储二维信息
    private static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        Random r = new Random();
        int x0 = r.nextInt(len);
        int y0 = r.nextInt(len);
        //p0是任意选定的唯一水泥点
        Point p0 = new Point(x0, y0);
        Point ps = new Point(0, 0); //ps是选定初始坐标原点，即（start点）
        LG[x0][y0] = -1; // 已经铺好水泥点的地方；
		
		//打印初始地板信息
        for (int i=0; i<LG.length; i++)
            System.out.println(Arrays.toString(LG[i]));
        System.out.println();
        
        lgImplement(len, p0, ps); //递归铺砖
        
		//打印铺完后的地板信息
        for (int i=0; i<LG.length; i++)
            System.out.println(Arrays.toString(LG[i]));
    }

    private static void lgImplement(int l, Point p0, Point ps) {
        if (l <= 1) return; //默认每次调用这个函数时，都有一个水泥点，所以当只有1个格子时候，必定不用再铺砖了
        // 下面求出4个中间接点坐标；
        Point[][] p = new Point[2][2];
        p[0][1] = new Point(ps.x + l / 2 - 1, ps.y + l / 2); //第一象限点
        p[0][0] = new Point(p[0][1].x, p[0][1].y - 1); //第二象限点
        p[1][0] = new Point(p[0][1].x + 1, p[0][1].y - 1); // 第三象限点
        p[1][1] = new Point(p[0][1].x + 1, p[0][1].y); // 第四象限点

		// 求算水泥点是在第几象限；（默认二维数组的行对应x轴，列对应y轴）
        int i = (p0.x - ps.x) * 2 / l; 
        int j = (p0.y - ps.y) * 2 / l;
        int temp = LG[p[i][j].x][p[i][j].y]; //先取出原本该水泥点上的值（可能是0，也可能是-1）

		// 全部赋值为砖序号数，避免了做判断
        LG[p[0][0].x][p[0][0].y] = index;
        LG[p[0][1].x][p[0][1].y] = index;
        LG[p[1][0].x][p[1][0].y] = index;
        LG[p[1][1].x][p[1][1].y] = index ++; //方砖序号自增

        LG[p[i][j].x][p[i][j].y] = temp; // 然后将原来值取回，不能改变原水泥点信息；
        p[i][j] = p0; //并且为了避免接下来的递归做判断，将原水泥点赋值给p二维数组；

		// 递归进行铺砖；
        lgImplement(l/2, p[0][1], new Point(ps.x, ps.y + l/2));
        lgImplement(l/2, p[0][0], ps);
        lgImplement(l/2, p[1][0], new Point(ps.x + l/2, ps.y));
        lgImplement(l/2, p[1][1], new Point(ps.x + l/2, ps.y + l/2));

    }


}

