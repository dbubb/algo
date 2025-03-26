import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
   public static void main(String[] args)
   {
      Scanner in = new Scanner(System.in);
      int n = in.nextInt();
      int k = in.nextInt();
      
      PrintWriter out = new PrintWriter(System.out);

      FenwickTree t = new FenwickTree(n + 1);
      t.add(1, n, 1);
      
      int cur = k;
      t.add(cur, -1);
      out.print(k);
      
      for (int i = 2;  i <= n; i++)
      {
          int sum = (t.sum(cur) + k) % (n - i + 1);
          if (sum == 0)
              sum = n - i + 1;
          cur = t.findGreatestIndex(sum - 1) + 1;
          t.add(cur, -1);
          out.print(" ");
          out.print(cur);
      }
      out.flush();
   }
   
   // https://www.topcoder.com/thrive/articles/Binary%20Indexed%20Trees
   // Не использовать индекс == 0 !!!
   public static class FenwickTree
   {
       private int[] tree;
       
       public FenwickTree(int size)
       {
           tree = new int[size];
       }
       
       public int sum(int index)
       {
           int sum = 0;
           while (index > 0)
           {
               sum += tree[index];
               index -= (index & -index);
           }
           return sum;
       }
       
       public int sum(int indexFrom, int indexTo)
       {
           return sum(indexTo) - sum(indexFrom - 1);
       }
       
       public void add(int index, int val)
       {
           while (index < tree.length)
           {
               tree[index] += val;
               index += (index & -index);
           }
       }
       
       public void add(int indexFrom, int indexTo, int val)
       {
           for (int i = indexFrom; i <= indexTo; i++)
               add(i, val);
       }
       
       public int findGreatestIndex(int sum)
       {
           int bitMask = tree.length;
           int bitCount = 0;
           while (bitMask > 0)
           {
               bitMask >>= 1;
               bitCount++;
           }
           bitMask = 1 << (bitCount - 1);
           
           int index = 0;
           while (bitMask != 0)
           {
               int tIdx = index + bitMask;
               bitMask >>= 1;
               if (tIdx >= tree.length)
                   continue;
               if (sum >= tree[tIdx])
               {
                   index = tIdx;
                   sum -= tree[tIdx];
               }
           }
           
           return (sum != 0) ? -1 : index;
       }
   }
}
