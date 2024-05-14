import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/**
* На формулы расстояния от точки до прямой, координаты ближайшей точки на прямой
* https://acm.timus.ru/status.aspx?space=1&num=1348&author=342042
*/
public class Task
{
    //public static String str;
    //public static int[][] p;
    //public static int cur;
    //public static PrintWriter out;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        int x1 = Integer.parseInt(parts[0]);
        int y1 = Integer.parseInt(parts[1]);
        int x2 = Integer.parseInt(parts[2]);
        int y2 = Integer.parseInt(parts[3]);
        parts = reader.readLine().split(" ");
        int x0 = Integer.parseInt(parts[0]);
        int y0 = Integer.parseInt(parts[1]);
        int l = Integer.parseInt(parts[2]);
        
        int dx1 = x1-x0;
        int dx2 = x2-x0;
        int dy1 = y1-y0;
        int dy2 = y2-y0;
        double l1 = Math.sqrt(dx1*dx1 + dy1*dy1);
        double l2 = Math.sqrt(dx2*dx2 + dy2*dy2);
        double lmax = l1 > l2 ? l1 : l2;
        double lmin = l1 > l2 ? l2 : l1;
        double dlmax = lmax > l ? lmax - l : 0.0;
        double dlmin = 0.0;
        if (l1 > l && l2 > l)
        {
            int a = y2 - y1;
            int b = x1 - x2;
            int c = x2*y1-y2*x1;
            int ab2 = a*a + b*b;
            //out.println("a = " + a + "; b = " + b + "; c = " + c + "; ab2 = " + ab2);
            if (ab2 > 0)
            {
                // Координаты ближайшей точки на прямой
                double xm = ((double)b*((double)b*x0 - (double)a*y0) - (double)a*c) / (double)ab2;
                double ym = ((double)a*((double)-b*x0 + (double)a*y0) - (double)b*c) / (double)ab2;
                //out.format("xm = %.2f; ym = %.2f\n", xm, ym);
                boolean byx = (x1 == x2) || (x1 < x2 && x1 <= xm && xm <= x2) || (x1 > x2 && x1 >= xm && xm >= x2);
                boolean byy = (y1 == y2) || (y1 < y2 && y1 <= ym && ym <= y2) || (y1 > y2 && y1 >= ym && ym >= y2);
                if (byx && byy)
                    // Расстояние от точки до прямой
                    lmin = (double)Math.abs(a*x0+b*y0+c) / Math.sqrt(ab2);
            }
            dlmin = lmin > l ? lmin - l : 0.0;
        }
        
        out.format("%.2f\n%.2f\n", dlmin, dlmax);

        out.flush();
    }
}
