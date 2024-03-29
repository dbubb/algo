import java.io.*;
import java.lang.*;
import java.util.*;

public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        int n = Integer.parseInt(reader.readLine());
        List<Integer> num = new ArrayList(n);
        SegmentsTree st = new SegmentsTree(n, 0);
        //st.print(out);
        int curPos = 0;
        for (int i = 0; i < n; i++)
        {
            String[] parts = reader.readLine().split(" ");
            int x = Integer.parseInt(parts[1]);
            if ("+".equals(parts[0]))
            {
                num.add(x);
                st.update(curPos, x);
                curPos++;
            }
            else
            {
                int indx = num.indexOf(x);
                num.set(indx, 0);
                st.update(indx, 0);
            }
            //out.println(num);
            //st.print(out);
            out.println(st.getAll());
        }

        out.flush();
    }
    
    public static class SegmentsTree
    {
        public int[] arr;
        public int n;
        public int defElem;
        
        public SegmentsTree(int sz, int initVal)
        {
            n = closestDeg2(sz);
            defElem = initVal;
            arr = new int[2 * n];
            Arrays.fill(arr, initVal);
        }
        
        public int getAll()
        {
            return (arr[1] == 0) ? 1 : arr[1];
        }
        
        public int get(int l, int r)
        {
            return get(1, 0, n-1, l, r);
        }
        public int get(int v, int tl, int tr, int l, int r)
        {
            if (l > r)
                return defElem;
            if (l == tl && r == tr)
                return arr[v];
            int tm = (tl + tr) / 2;
            return calc(get(v*2, tl, tm, l, Math.min(r,tm)), get(v*2+1, tm+1, tr, Math.max(l,tm+1), r));
        }
        
        public void update(int pos, int val)
        {
            update(1, 0, n-1, pos, val);
        }
        
        public boolean update(int v, int tl, int tr, int pos, int val)
        {
            if (tl == tr)
            {
                boolean res = arr[v] != val;
		        arr[v] = val;
		        return res;
            }
	        else
	        {
		        int tm = (tl + tr) / 2;
		        boolean updated = (pos <= tm) ?
			        update(v*2, tl, tm, pos, val) :
			        update(v*2+1, tm+1, tr, pos, val);
			    if (updated)
			    {
			        int nv = calc(arr[v*2], arr[v*2+1]);
			        boolean res = arr[v] != nv;
		            arr[v] = nv;
		            return res;
			    }
		        return false;
        	}
        }
        
        public int calc(int n1, int n2)
        {
            if (n1 == 0)
                return n2;
            if (n2 == 0)
                return n1;
            if (n1 == n2)
                return n1;
            if (n1 == 1 || n2 == 1)
                return 1;
            if ((n1 & 1) == 0)
                return ((n2 & 1) == 0)
                    ? calc(n1 >> 1, n2 >> 1) << 1
                    : calc(n1 >> 1, n2);
            else
                return ((n2 & 1) == 0)
                    ? calc(n1, n2 >> 1)
                    : calc(n2, n1 > n2 ? n1 - n2 : n2 - n1);
        }
        
        public void print(PrintWriter out)
        {
            for (int i = 0; i < arr.length; i++)
            {
                out.print(arr[i]);
                out.print(" ");
            }
            out.println("");
        }
        
        public static int closestDeg2(int n)
        {
            int res = 1;
            while (res < n)
                res *= 2;
            return res;
        }
    }
}
