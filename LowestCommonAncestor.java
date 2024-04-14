import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

/** Ближайший общий предок и поиск в ширину */
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String[] parts;// = reader.readLine().split(" ");
        int n = Integer.parseInt(reader.readLine());
        Node[] nodes = new Node[n];
        for (int i = 0; i < n-1; i++)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            int w = Integer.parseInt(parts[2]);
            if (nodes[n1] == null)
                nodes[n1] = new Node();
            nodes[n1].edges.add(new Edge(n2, w));
            if (nodes[n2] == null)
                nodes[n2] = new Node();
            nodes[n2].edges.add(new Edge(n1, w));
        }
        
      // Поиск в ширину для разметки дерева  
      if (nodes[0] == null)
            nodes[0] = new Node();
        nodes[0].parent = -1;
        nodes[0].lvl = 0;
        nodes[0].w = 0;
        Queue<Integer> q = new ArrayDeque<>(n);
        q.add(0);
        while (!q.isEmpty())
        {
            int num = q.remove();
            Node nd = nodes[num];
            if (nd != null)
            {
                for (Edge ed : nd.edges)
                {
                    Node tond = nodes[ed.n];
                    if (tond.lvl == -1)
                    {
                        tond.parent = num;
                        tond.lvl = nd.lvl + 1;
                        tond.w = nd.w + ed.w;
                        q.add(ed.n);
                    }
                }
            }
        }
        
        int m = Integer.parseInt(reader.readLine());
        for (int i = 0; i < m; i++)
        {
            parts = reader.readLine().split(" ");
            Node n1 = nodes[Integer.parseInt(parts[0])];
            Node n2 = nodes[Integer.parseInt(parts[1])];
            int res = n1.w + n2.w;

            // Ближайший общий предок
            while (n1.lvl != n2.lvl)
            {
                if (n1.lvl < n2.lvl)
                    n2 = nodes[n2.parent];
                else
                    n1 = nodes[n1.parent];
            }
            while (n1 != n2)
            {
                n1 = nodes[n1.parent];
                n2 = nodes[n2.parent];
            }

            res -= (n1.w * 2);
            out.println(res);
        }

        out.flush();
   }
   
   public static class Node
   {
       int parent;
       int lvl = -1;
       int w;
       List<Edge> edges = new ArrayList<>();
   }
   
   public static class Edge
   {
       int n;
       int w;
       
       public Edge(int n, int w)
       {
           this.n = n;
           this.w = w;
       }
   }
}
