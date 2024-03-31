import java.io.*;
import java.lang.*;
import java.util.*;

public class Task
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      String num = in.nextLine();
      int a1 = num.charAt(0) - '0';
      int a2 = num.charAt(1) - '0';
      int a3 = num.charAt(2) - '0';
      int b1 = num.charAt(3) - '0';
      int b2 = num.charAt(4) - '0';
      int b3 = num.charAt(5) - '0';
      int a = a1 + a2 + a3;
      int b = b1 + b2 + b3;


      PrintWriter out = new PrintWriter(System.out);
      //out.println(b3);
      if ((b < a && b3 == 9) || (b > a && b3 == 0))
          out.println("No");
      else
          out.println("Yes");

      out.flush();
   }
}
