import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
   public static void main(String[] args) throws IOException
   {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      int n = Integer.parseInt(reader.readLine());
      
      Node[] nodes = new Node[n+1];
      for (int i = 1;  i <= n; i++)
         nodes[i] = new Node(i);
      ArrayDeque<Integer> q = new ArrayDeque<Integer>(n);

      PrintWriter out = new PrintWriter(System.out);
      for (int i = 1;  i <= n; i++)
      {
         String[] parts = reader.readLine().split(" ");
         int childs = 0;
         for (int j = 0;  j < parts.length; j++)
         {
            int num = Integer.parseInt(parts[j]);
            if (num == 0)
               break;
            nodes[num].addParent(i);
            childs++;
         }
         if (childs == 0)
         {
            nodes[i].level = 1;
            q.addLast(i);
         }
      }

      do
      {
          Node c_nd = nodes[q.pollFirst()];
          int lvl = c_nd.level + 1;
          for (int num : c_nd.parents)
          {
              nodes[num].level = lvl;
              q.addLast(num);
          }
      }
      while (!q.isEmpty());
      
      Comparator<Node> lvlComporator = Comparator.<Node>comparingInt(nd -> nd.level).reversed();
      String res = Arrays.stream(nodes).skip(1).sorted(lvlComporator).map(nd -> String.valueOf(nd.num)).collect(Collectors.joining(" "));
      out.println(res);

      out.flush();
   }
   
   public static class Node
   {
       Set<Integer> parents;
       int level;
       int num;
       
       public Node(int num)
       {
           parents = new HashSet<>();
           this.num = num;
           level = 0;
       }
       
       public void addParent(int num)
       {
           parents.add(num);
       }
   }
}
