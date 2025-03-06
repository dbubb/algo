import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
 * Работа с деревьями: DFS, поиск вершины на указанном расстоянии
 * https://ru.algorithmica.org/cs/trees/tree-queries/
 */
public class Main
{
    public static int nlog2, maxLvl, maxLvlNode, rootNode;
    public static Node[] nodes;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int n = Integer.parseInt(parts[0]);
        int qq = Integer.parseInt(parts[1]);
        nlog2 = 31 - Integer.numberOfLeadingZeros(n+1);
        nodes = new Node[n+1];
        for (int i = 1; i < n; i++)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            if (nodes[n1] == null)
                nodes[n1] = new Node();
            nodes[n1].edges.add(n2);
            if (nodes[n2] == null)
                nodes[n2] = new Node();
            nodes[n2].edges.add(n1);
        }
        if (n == 1)
            nodes[1] = new Node();
        maxLvl = 0;
        maxLvlNode = 1;
        dfsSimple(1, 1, 0);
        
        rootNode = maxLvlNode;
        maxLvl = 0;
        maxLvlNode = 1;
        dfs(rootNode, rootNode, 0);
        
        int cn = nodes[rootNode].bottom;
        int lvl = nodes[cn].lvl;
        nodes[cn].middle = cn;
        while (lvl > 0)
        {
            cn = nodes[cn].parents[0];
            nodes[cn].middle = cn;
            lvl = nodes[cn].lvl;
        }
        
        dfsMiddle(rootNode, rootNode);
        
        /*for (int i = 1; i <= n; i++)
            System.out.println(i + ": lvl=" + nodes[i].lvl + ", p=" + nodes[i].parents[0]
                + ", b=" + nodes[i].bottom + ", m=" + nodes[i].middle);*/
        
        for (int i = 0; i < qq; i++)
        {
            parts = reader.readLine().split(" ");
            int v = Integer.parseInt(parts[0]);
            int d = Integer.parseInt(parts[1]);
            
            if (nodes[v].lvl >= d)
            {
                /*System.out.println("v=" + v + ", d=" + d
                    + ", lvl=" + nodes[v].lvl + ", tl=" + (nodes[v].lvl - d));*/
            
                int tl = nodes[v].lvl - d;
                for (int j = nlog2; j >= 0; j--)
                    if (nodes[nodes[v].parents[j]].lvl >= tl)
                    {
                        v = nodes[v].parents[j];
                    }
                out.println(v);
            }
            else if (nodes[nodes[v].bottom].lvl - nodes[v].lvl >= d)
            {
                /*System.out.println("v=" + v + ", d=" + d
                        + ", lvl=" + nodes[v].lvl + ", tl=" + (nodes[v].lvl + d));*/
                        
                int tl = nodes[v].lvl + d;
                v = nodes[v].bottom;
                for (int j = nlog2; j >= 0; j--)
                    if (nodes[nodes[v].parents[j]].lvl >= tl)
                    {
                        v = nodes[v].parents[j];
                    }
                out.println(v);
            }
            else
            {
                d = d - (nodes[v].lvl - nodes[nodes[v].middle].lvl);
                v = nodes[v].middle;
                if (nodes[nodes[v].bottom].lvl - nodes[v].lvl >= d)
                {
                    /*System.out.println("v=" + v + ", d=" + d
                            + ", lvl=" + nodes[v].lvl + ", tl=" + (nodes[v].lvl + d));*/
                            
                    int tl = nodes[v].lvl + d;
                    v = nodes[v].bottom;
                    for (int j = nlog2; j >= 0; j--)
                        if (nodes[nodes[v].parents[j]].lvl >= tl)
                        {
                            v = nodes[v].parents[j];
                        }
                    out.println(v);
                }
                else
                    out.println("0");
            }
        }
        
        out.flush();
    }
    
    public static void dfsSimple(int n, int parent, int lvl)
    {
        for (int en : nodes[n].edges)
        {
            if (en != parent)
            {
                dfsSimple(en, n, lvl+1);
            }
        }
        if (lvl > maxLvl)
        {
            maxLvl = lvl;
            maxLvlNode = n;
        }
    }
    
    public static int dfs(int n, int parent, int lvl)
    {
        nodes[n].fillParents(parent);
        nodes[n].lvl = lvl;
        for (int en : nodes[n].edges)
        {
            if (en != parent)
            {
                int cur = dfs(en, n, lvl+1);
                if (nodes[n].bottom == -1 || nodes[cur].lvl > nodes[nodes[n].bottom].lvl)
                {
                    nodes[n].bottom = cur;
                }
            }
        }
        if (nodes[n].bottom == -1)
            nodes[n].bottom = n;
        if (lvl > maxLvl)
        {
            maxLvl = lvl;
            maxLvlNode = n;
        }
        return nodes[n].bottom;
    }
    
    public static void dfsMiddle(int n, int parent)
    {
        if (nodes[n].middle == -1)
            nodes[n].middle = nodes[parent].middle;
        for (int en : nodes[n].edges)
        {
            if (en != parent)
            {
                dfsMiddle(en, n);
            }
        }
    }
   
    public static class Node
    {
        int[] parents = new int[nlog2+1];
        int lvl = -1;
        int bottom = -1;
        int middle = -1;
        List<Integer> edges = new ArrayList<>();
       
        public void fillParents(int p)
        {
            parents[0] = p;
            for (int i = 1; i <= nlog2; i++)
               parents[i] = nodes[parents[i-1]].parents[i-1];
        }
    }
}
