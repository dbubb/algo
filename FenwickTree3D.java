import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Многомерное дерево Фенвика (Binary Indexed Tree, BIT)
 * https://www.topcoder.com/thrive/articles/Binary%20Indexed%20Trees
 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int n = Integer.parseInt(reader.readLine());
        //String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        //int[][][] s = new int[n][n][n];
        FenwickTree3D t = new FenwickTree3D(n + 1);

        while (true)
        {
            String[] parts = reader.readLine().split(" ");
            int m = Integer.parseInt(parts[0]);
            if (m == 1)
            {
                int x = Integer.parseInt(parts[1]) + 1;
                int y = Integer.parseInt(parts[2]) + 1;
                int z = Integer.parseInt(parts[3]) + 1;
                int k = Integer.parseInt(parts[4]);
                //s[x][y][z] += k;
                t.add(x, y, z, k);
                //System.out.println("add: x = " + x + "; y = " + y + "; z = " + z + "; k = " + k);
                //t.print();
            }
            else if (m == 2)
            {
                int x1 = Integer.parseInt(parts[1]);
                int y1 = Integer.parseInt(parts[2]);
                int z1 = Integer.parseInt(parts[3]);
                int x2 = Integer.parseInt(parts[4]) + 1;
                int y2 = Integer.parseInt(parts[5]) + 1;
                int z2 = Integer.parseInt(parts[6]) + 1;
        
                /*long cnt = 0;
                for (int ix = x1; ix <= x2; ix++)
                    for (int iy = y1; iy <= y2; iy++)
                        for (int iz = z1; iz <= z2; iz++)
                            cnt += s[ix][iy][iz];*/
                out.println(t.sum(x1, y1, z1, x2, y2, z2));
            }
            else
                break;
        }
        
        out.flush();
    }
    
    public static class FenwickTree3D
    {
        private int size;
        private int[][][] tree;
        
        public FenwickTree3D(int size)
        {
            this.size = size;
            tree = new int[size][size][size];
        }
        
        public long sum(int x, int y, int z)
        {
            long sum = 0;
            int y1, z1;
            while (x > 0)
            {
                y1 = y;
                while (y1 > 0)
                {
                    z1 = z;
                    while (z1 > 0)
                    {
                        //System.out.println("sum: x = " + x + "; y = " + y1 + "; z = " + z1);
                        sum += tree[x][y1][z1];
                        z1 -= (z1 & -z1);
                    }
                    y1 -= (y1 & -y1);
                }
                
                x -= (x & -x);
            }
            return sum;
        }
        
        public long sum(int x1, int y1, int z1, int x2, int y2, int z2)
        {
            /*System.out.println("sum = " + sum(x2, y2, z2)
                    + " - " + sum(x1, y2, z2)
                    + " - " + sum(x2, y1, z2)
                    + " - " + sum(x2, y2, z1)
                    + " + " + sum(x1, y1, z2)
                    + " + " + sum(x1, y2, z1)
                    + " + " + sum(x2, y1, z2)
                    + " - " + sum(x1, y1, z1)
                    );*/
            return sum(x2, y2, z2)
                - sum(x1, y2, z2) - sum(x2, y1, z2) - sum(x2, y2, z1)
                + sum(x1, y1, z2) + sum(x1, y2, z1) + sum(x2, y1, z1)
                - sum(x1, y1, z1);
        }
        
        public void add(int x, int y, int z, int val)
        {
            int y1, z1;
            while (x < size)
            {
                y1 = y;
                while (y1 < size)
                {
                    z1 = z;
                    while (z1 < size)
                    {
                        tree[x][y1][z1] += val;
                        //System.out.println("in add: x = " + x + "; y = " + y1 + "; z = " + z1 + "; val = " + tree[x][y1][z1]);
                        z1 += (z1 & -z1);
                    }
                    y1 += (y1 & -y1);
                }
                x += (x & -x);
            }
        }
        
        /*public void print()
        {
            for (int x = 0; x < size; x++)
            {
                for (int y = 0; y < size; y++)
                {
                    for (int z = 0; z < size; z++)
                    {
                        System.out.print(tree[x][y][z] + " ");
                    }
                    System.out.print("* ");
                }
                System.out.println("");
            }
        }*/
   }
}
