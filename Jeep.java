import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

public class Main
{
    /**
     * Jeep Problem, задача о джипе в пустыне
     * https://mathworld.wolfram.com/JeepProblem.html
     * https://acm.timus.ru/problem.aspx?space=1&num=1113
     */
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        //String str = reader.readLine();
        
        int n = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        
        if (m >= n)
            out.println(n);
        else
        {
            double k = 1.0;
            double kp = 1.0;
            int i = 1;
            while (k * m < n)
            {
                i++;
                kp = k;
                k += (1.0 / (double)(2*i - 1));
            }

            // Дальше бинарным поиском
            int r = i*m;
            i--;
            int l = i*m;
            while (l < r)
            {
                int mid = l  + ((r - l) / 2);
                double f = (double)(mid - i*m) / (double)m;
                double d = f / (double)(2*i + 1) + kp;
                /*System.out.println("l = " + l + "; r = " + r + "; f = " + f
                    + "; d = " + d + "; d*m = " + (d * m));*/
                if (d * m < n)
                    l = mid;
                else
                    r = mid;
                if (r-l <= 1)
                    break;
            }
            out.println(r);
        }

        out.flush();
    }
}
