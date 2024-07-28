import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

/**
 * Алгоритм Беллмана-Форда
 * http://e-maxx.ru/algo/ford_bellman
 */
public class Task
{
    public static int[][] r = new int[4][4];
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        int s = Integer.parseInt(parts[2]);
        double sum = Double.parseDouble(parts[3]);
        
        Edge[] e = new Edge[m*2];
        
        for (int i = 0; i < m; i++)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            double r12 = Double.parseDouble(parts[2]);
            double c12 = Double.parseDouble(parts[3]);
            double r21 = Double.parseDouble(parts[4]);
            double c21 = Double.parseDouble(parts[5]);
            e[i] = new Edge(n1, n2, r12, c12);
            e[m+i] = new Edge(n2, n1, r21, c21);
        }
        
        double d[] = new double[n+1];
        for (int i = 0; i <= n; i++)
            d[i] = -1.0;
        
        d[s] = sum;
        boolean relaxed = false;
        for (int i = 0; i < n; i++)
        {
            relaxed = false;
            for (int j = 0; j < 2*m; j++)
            {
                Edge ed = e[j];
                if (d[ed.n1] >= ed.c)
                {
                    double ns = (d[ed.n1] - ed.c) * ed.r;
                    if (d[ed.n2] < ns)
                    {
                        d[ed.n2] = ns;
                        relaxed = true;
                    }
                }
            }
            if (!relaxed)
                break;
        }
        
        boolean res = relaxed || d[s] > sum;

        out.println(res ? "YES" : "NO");

        out.flush();
    }
    
    public static class Edge
    {
        public int n1;
        public int n2;
        public double r;
        public double c;
        
        public Edge(int n1, int n2, double r, double c)
        {
            this.n1 = n1;
            this.n2 = n2;
            this.r = r;
            this.c = c;
        }
    }
}
