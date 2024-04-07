import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;

public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        //String[] parts = reader.readLine().split(" ");
        int n = Integer.parseInt(reader.readLine());
        List<Integer> nums = new ArrayList<>(n);
        Map<String, Integer> mem = new HashMap<>();
        for (int i = 0; i < n; i++)
        {
            String[] parts = reader.readLine().split(" ");
            int x = Integer.parseInt(parts[1]);
            if ("+".equals(parts[0]))
            {
                nums.add(x);
            }
            else
            {
                nums.remove((Integer)x);
            }
            out.println(nod(nums, mem));
        }
        
        out.flush();
    }
    
    public static int nod(List<Integer> nums, Map<String, Integer> mem)
    {
        List<Integer> unums = nums.stream().distinct().sorted().collect(Collectors.toList());
        return nodSorted(unums, unums.size(), mem);
    }
    
    public static int nodSorted(List<Integer> unums, int len, Map<String, Integer> mem)
    {
        String key = unums.stream().limit(len).map(i -> String.valueOf(i)).collect(Collectors.joining("_"));
        if (mem.containsKey(key))
            return mem.get(key);
        int s = unums.size();
        int res = 1;
        if (s == 0)
            res = 1;
        else if (s == 1)
            res = unums.get(0);
        else if (len == 2)
        {
            res = nod(unums.get(0), unums.get(1));
        }
        else
        {
            res = nod(nodSorted(unums, len - 1, mem), unums.get(len - 1));
        }
        mem.put(key, res);
        return res;
    }
    
    public static int nod(int n1, int n2)
    {
        while (n2 != 0)
        {
            int tmp = n1 % n2;
            n1 = n2;
            n2 = tmp;
        }
        return n1;
    }
}
