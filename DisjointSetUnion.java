import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
   public static void main(String[] args) throws IOException
   {
      //Scanner in = new Scanner(System.in);
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String[] str = reader.readLine().split(" ");
      int n = Integer.parseInt(str[0]);
      int m = Integer.parseInt(str[1]);
      
      int mn1[] = new int[m];
      int mn2[] = new int[m];
      
      for (int i = 0;  i < m; i++)
      {
          str = reader.readLine().split(" ");;
          mn1[i] = Integer.parseInt(str[0]);
          mn2[i] = Integer.parseInt(str[1]);
      }
      
      int q = Integer.parseInt(reader.readLine());
      int qn[] = new int[q];
      Set<Integer> qnums = new HashSet<Integer>();

      str = reader.readLine().split(" ");
      for (int i = 0;  i < q; i++)
      {
          qn[i] = Integer.parseInt(str[i]) - 1;
          qnums.add(qn[i]);
      }
      
      PrintWriter out = new PrintWriter(System.out);
      int tops[] = new int[n+1];
      int ranks[] = new int[n+1];
      int parts = n;
      
      for (int i = 0;  i < m; i++)
      {
          if (qnums.contains(i))
             continue;
          if (union(tops, ranks, mn1[i], mn2[i]))
             parts--;
          //out.println(Arrays.stream(tops).boxed().map(String::valueOf).collect(Collectors.joining(" ")));
          //out.println("parts = " + parts + "; " + mn1[i] + " - " + mn2[i]);
      }
      
      List<Integer> res = new ArrayList<Integer>();
      for (int i = q-1;  i >=0; i--)
      {
          res.add(parts);
          if (union(tops, ranks, mn1[qn[i]], mn2[qn[i]]))
             parts--;
          //out.println(Arrays.stream(tops).boxed().map(String::valueOf).collect(Collectors.joining(" ")));
          //out.println("parts = " + parts + "; " + mn1[qn[i]] + " - " + mn2[qn[i]]);
      }
      
      Collections.reverse(res);
      out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));

      out.flush();
   }
   
   private static int findTop(int tops[], int n)
   {
       if (tops[n] == 0)
          return n;
       int t = findTop(tops, tops[n]);
       tops[n] = t;
       return t;
   }
   
   private static boolean union(int tops[], int ranks[], int n1, int n2)
   {
       int tn1 = findTop(tops, n1);
       int tn2 = findTop(tops, n2);
       if (tn1 == tn2)
          return false;
       if (ranks[tn1] < ranks[tn2])
       {
           int tmp = tn1;
           tn1 = tn2;
           tn2 = tmp;
       }
       tops[tn2] = tn1;
       if (ranks[tn1] == ranks[tn2])
          ranks[tn1]++;
       return true;
   }
}
