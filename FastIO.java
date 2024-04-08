import java.io.*;
import java.lang.*;
import java.util.*;

public class Task
{
   public static void main(String[] args) throws IOException
   {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      int n = Integer.parseInt(reader.readLine());
      Set<String> p = new HashSet<>();

      for (int i = 0;  i < n; i++)
          p.add(reader.readLine());
          
      int m = Integer.parseInt(reader.readLine());
      int cnt = 0;
      for (int i = 0;  i < m; i++)
      {
          String d = reader.readLine();
          if (p.contains(d))
              cnt++;
      }

      PrintWriter out = new PrintWriter(System.out);
      out.println(cnt);

      out.flush();
   }
}
