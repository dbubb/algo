import java.io.*;
import java.lang.*;
import java.util.*;

public class Task
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      int n = in.nextInt();
      int m = n * (n + 1) / 2;
      
      PrintWriter out = new PrintWriter(System.out);
      for (int y = 0; y < n; y++)
      {
          for (int x = 0; x < n; x++)
          {
              int d = n - x + y;
              int s = 1;
              if (d > 1 && d <= n)
                  s = d * (d - 1) / 2 + 1;
              else if (d > n)
              {
                  int d2 = d - n;
                  if (d2 == 1)
                      s = m + 1;
                  else
                      s = m + ((n + n - d2) * (d2 - 1) / 2) + 1;
              }
              int val = s + (d > n ? x : y);
              out.print(val);
              if (x < n - 1)
                  out.print(" ");
              else
                  out.println("");
          }
      }
      //out.println(res);

      out.flush();
   }
}
