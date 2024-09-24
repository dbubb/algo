import java.io.*;
import java.lang.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;

/** https://acm.timus.ru/status.aspx?space=1&num=1205&author=342042 */
public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //int n = Integer.parseInt(reader.readLine());
        String[] parts = reader.readLine().split(" ");
        double vp = Double.parseDouble(parts[0]);
        double vm = Double.parseDouble(parts[1]);
        int n = Integer.parseInt(reader.readLine());
        //String str = reader.readLine();
        
        double[] x = new double[n+2];
        double[] y = new double[n+2];
        double[][] w = new double[n+2][n+2];
        
        for (int i = 1; i <= n; i++)
        {
            parts = reader.readLine().split(" ");
            x[i] = Double.parseDouble(parts[0]);
            y[i] = Double.parseDouble(parts[1]);
        }
        for (int i = 1; i <= n; i++)
        {
            for (int j = i + 1; j <= n; j++)
            {
                double dx = x[i] - x[j];
                double dy = y[i] - y[j];
                w[i][j] = Math.sqrt(dx * dx + dy * dy) / vp;
                w[j][i] = w[i][j];
            }
        }
        while (true)
        {
            parts = reader.readLine().split(" ");
            int n1 = Integer.parseInt(parts[0]);
            int n2 = Integer.parseInt(parts[1]);
            if (n1 == 0 && n2 == 0)
                break;
            double dx = x[n2] - x[n1];
            double dy = y[n2] - y[n1];
            w[n1][n2] = Math.sqrt(dx * dx + dy * dy) / vm;
            w[n2][n1] = w[n1][n2];
        }
        parts = reader.readLine().split(" ");
        x[0] = Double.parseDouble(parts[0]);
        y[0] = Double.parseDouble(parts[1]);
        for (int i = 1; i <= n; i++)
        {
            double dx = x[i] - x[0];
            double dy = y[i] - y[0];
            w[0][i] = Math.sqrt(dx * dx + dy * dy) / vp;
        }
        
        parts = reader.readLine().split(" ");
        x[n+1] = Double.parseDouble(parts[0]);
        y[n+1] = Double.parseDouble(parts[1]);
        for (int i = 1; i <= n; i++)
        {
            double dx = x[i] - x[n+1];
            double dy = y[i] - y[n+1];
            w[i][n+1] = Math.sqrt(dx * dx + dy * dy) / vp;
        }
        
        double dx = x[0] - x[n+1];
        double dy = y[0] - y[n+1];
        w[0][n+1] = Math.sqrt(dx * dx + dy * dy) / vp;

        double[] r = new double[n+2];
        int[] prev = new int[n+2];
        Arrays.fill(r, Double.MAX_VALUE);
        r[0] = 0.0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(
            (Integer n1, Integer n2) -> Double.compare(r[n1], r[n2]));
        pq.add(0);
        while (!pq.isEmpty())
        {
            int nn = pq.poll();
            for (int i = 1; i <= n+1; i++)
            {
                if (nn != i)
                {
                    double nw = r[nn] + w[nn][i];
                    if (nw < r[i])
                    {
                        r[i] = nw;
                        prev[i] = nn;
                        pq.add(i);
                    }
                }
            }
        }
        
        ArrayList<Integer> st = new ArrayList<>();
        int previ = prev[n+1];
        while (previ != 0)
        {
            st.add(previ);
            previ = prev[previ];
        }
        out.format("%.7f\n", r[n+1]);
        out.print(st.size());
        for (int i = st.size() - 1; i >= 0; i--)
            out.print(" " + st.get(i));
        out.println("");

        out.flush();
    }
}
