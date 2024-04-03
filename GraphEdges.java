import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
   public static void main(String[] args) throws IOException
   {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String[] parts = reader.readLine().split(" ");
      int n = Integer.parseInt(parts[0]);
      int k = Integer.parseInt(parts[1]);
      
      Set<Integer> el = new HashSet<>();
      parts = reader.readLine().split(" ");
      for (int i = 0;  i < k; i++)
      {
          el.add(Integer.parseInt(parts[i]));
      }
      
      PrintWriter out = new PrintWriter(System.out);
      Set<Edge> edges = new HashSet<>();;
      for (int i = 1;  i <= n; i++)
      {
          parts = reader.readLine().split(" ");
          for (int j = i+1;  j <= n; j++)
          {
              int price = Integer.parseInt(parts[j-1]);
              edges.add(new Edge(i, j, price));
          }
      }
      
      int res = 0;
      while (el.size() < n)
      {
          Edge ed = edges.stream().filter(e -> (el.contains(e.n1) && !el.contains(e.n2)) || (!el.contains(e.n1) && el.contains(e.n2)))
             .sorted(Comparator.comparingInt(e -> e.price)).findFirst().get();
          el.add(ed.n1);
          el.add(ed.n2);
          res += ed.price;
          //out.println("size = " + el.size() + ", n1 = " + ed.n1 + ", n2 = " + ed.n2 + ", res = " + res);
      }
      
      out.println(res);

      out.flush();
   }
   
   public static class Edge
   {
       int n1;
       int n2;
       int price;
       
       public Edge(int n1, int n2, int price)
       {
           this.n1 = n1;;
           this.n2 = n2;
           this.price = price;
       }
   }
}
