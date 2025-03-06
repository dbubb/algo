import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

/**
 * Ближайший общий предок методом двоичного подъема
 * https://neerc.ifmo.ru/wiki/index.php?title=%D0%9C%D0%B5%D1%82%D0%BE%D0%B4_%D0%B4%D0%B2%D0%BE%D0%B8%D1%87%D0%BD%D0%BE%D0%B3%D0%BE_%D0%BF%D0%BE%D0%B4%D1%8A%D1%91%D0%BC%D0%B0
 */
public class Task
{
    public static int nlog2;
    public static Node[] nodes;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        String[] parts;// = reader.readLine().split(" ");
        int n = Integer.parseInt(reader.readLine());
        nlog2 = 31 - Integer.numberOfLeadingZeros(n);
        nodes = new Node[n];
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
        
        if (nodes[0] == null)
            nodes[0] = new Node();
        nodes[0].fillParents(0);
        //out.println("0 : " + IntStream.of(nodes[0].parents).boxed().map(String::valueOf).collect(Collectors.joining(", ")));
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
                        tond.fillParents(num);
                        //out.println(ed.n + " : " + IntStream.of(tond.parents).boxed().map(String::valueOf).collect(Collectors.joining(", ")));
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

            // Ближайший общий предок методом двоичного подъема
            Node nn1 = (n1.lvl <= n2.lvl) ? n1 : n2;
            Node nn2 = (n1.lvl > n2.lvl) ? n1 : n2;
            //out.println("n1 " + n1 + ", n2 " + n2 + ", nn1 " + nn1 + ", nn2 " + nn2);
            for (int j = nlog2; j >= 0; j--)
                if (nodes[nn2.parents[j]].lvl >= nn1.lvl)
                {
                    //out.println("nn2 " + nn2.parents[j]);
                    nn2 = nodes[nn2.parents[j]];
                }
            if (nn1 != nn2)
            {
                for (int j = nlog2; j >= 0; j--)
                    if (nn1.parents[j] != nn2.parents[j])
                    {
                        //out.println("nn1 " + nn1.parents[j] + ", nn2 " + nn2.parents[j]);
                        nn1 = nodes[nn1.parents[j]];
                        nn2 = nodes[nn2.parents[j]];
                    }
                nn1 = nodes[nn1.parents[0]];
            }

            //out.println("w1 " + n1.w + ", w2 " + n2.w + ", wc " + nn1.w);
            int res = n1.w + n2.w - (nn1.w * 2);
            out.println(res);
        }

        out.flush();
   }
   
   public static class Node
   {
       int[] parents = new int[nlog2+1];
       int lvl = -1;
       int w;
       List<Edge> edges = new ArrayList<>();
       
       public void fillParents(int p)
       {
           parents[0] = p;
           for (int i = 1; i <= nlog2; i++)
               parents[i] = nodes[parents[i-1]].parents[i-1];
       }
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
