import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String[] parts = reader.readLine().split(" ");
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        List<Integer>[] up = new ArrayList[n+1];
        List<Integer>[] down = new ArrayList[n+1];
        for (int i = 0; i < m; i++)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            if (up[n1] == null)
                up[n1] = new ArrayList<>();
            up[n1].add(n2);
            if (down[n2] == null)
                down[n2] = new ArrayList<>();
            down[n2].add(n1);
        }
        parts = reader.readLine().split(" ");
        int from = Integer.parseInt(parts[0]);
        int to = Integer.parseInt(parts[1]);
        
        int p_up[] = new int[n+1];
        int p_down[] = new int[n+1];
        for (int i = 1; i <= n; i++)
        {
            p_up[i] = Integer.MAX_VALUE;
            p_down[i] = Integer.MAX_VALUE;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>((Node n1, Node n2) -> Integer.compare(n1.p, n2.p));
        
        p_up[from] = 0;
        p_down[from] = 0;
        pq.add(new Node(from, 0));
        while (!pq.isEmpty())
        {
            Node nd = pq.poll();
            int ru = Math.min(p_up[nd.n], p_down[nd.n] + 1);
            int rd = Math.min(p_up[nd.n] + 1, p_down[nd.n]);
            if (up[nd.n] != null)
            {
                for (Integer dest : up[nd.n])
                {
                    boolean upd = false;
                    if (ru < p_up[dest])
                    {
                        p_up[dest] = ru;
                        upd = true;
                    }
                    if (ru+1 < p_down[dest])
                    {
                        p_down[dest] = ru+1;
                        upd = true;
                    }
                    if (upd)
                        pq.add(new Node(dest, Math.min(p_up[dest], p_down[dest])));
                }
            }
            if (down[nd.n] != null)
            {
                for (Integer dest : down[nd.n])
                {
                    boolean upd = false;
                    if (rd < p_down[dest])
                    {
                        p_down[dest] = rd;
                        upd = true;
                    }
                    if (rd+1 < p_up[dest])
                    {
                        p_up[dest] = rd+1;
                        upd = true;
                    }
                    if (upd)
                        pq.add(new Node(dest, Math.min(p_up[dest], p_down[dest])));
                }
            }
        }
        
        out.println(Math.min(p_up[to], p_down[to]));

        out.flush();
   }
   
   public static class Node
   {
       int n;
       int p;
       
       public Node(int n, int p)
       {
           this.n = n;
           this.p = p;
       }
   }
}
