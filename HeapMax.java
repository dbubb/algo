import java.io.*;
import java.lang.*;
import java.util.*;

/**
* Двоичная куча для нахождения максимума
* https://javarush.com/groups/posts/3083-strukturih-dannihkh-piramida-dvoichnaja-kucha-v-java
*/
public class Task
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        //String[] parts = reader.readLine().split(" ");
        int n = Integer.parseInt(reader.readLine());
        Map<String, Person> persons = new HashMap<>(n * 3 / 2);
        Heap cities = new Heap(60002);
        for (int i = 0; i < n; i++)
        {
            String[] parts = reader.readLine().split(" ");
            long sum = Long.parseLong(parts[2]);
            City c = cities.addOrUpdate(parts[1], sum);

            Person p = new Person(parts[0], c, sum);
            persons.put(p.name, p);
        }
        
        //Arrays.stream(cities.arr, 0, cities.size).forEach(c -> out.println(c.name + " " + c.sum + " " + c.leader));
        
        String[] parts = reader.readLine().split(" ");
        int days = Integer.parseInt(parts[0]);
        int k = Integer.parseInt(parts[1]);
        int cur = 0;
        for (int i = 0; i < k; i++)
        {
            parts = reader.readLine().split(" ");
            int d = Integer.parseInt(parts[0]);
            if (d > cur)
            {
                City leader = cities.getLeader();
                if (leader != null)
                    leader.leader += (d - cur);
                cur = d;
            }
            Person p = persons.get(parts[1]);
            City c = cities.addOrUpdate(parts[2], p.sum);
            cities.addOrUpdate(p.city.name, -p.sum);
            //out.println(i + " " + p.name);
            //out.println(parts[2] + " " + p.sum);
            //out.println(p.city.name + " " + -p.sum);

            p.city = c;
            
            //Arrays.stream(cities.arr, 0, cities.size).forEach(ct -> out.println(ct.name + " " + ct.sum + " " + ct.leader));
        }
        if (days > cur)
        {
            City leader = cities.getLeader();
            if (leader != null)
                leader.leader += (days - cur);
        }

        Arrays.stream(cities.arr, 0, cities.size)
                .filter(c -> c.leader > 0)
                .sorted((c1, c2) -> c1.name.compareTo(c2.name))
                .forEach(c -> out.println(c.name + " " + c.leader));

        out.flush();
    }

    public static class Person
    {
        public String name;
        public City city;
        public long sum;
        
        public Person(String name, City city, long sum)
        {
            this.name = name;
            this.city = city;
            this.sum = sum;
        }
    }
    
    public static class City
    {
        public String name;
        public long sum;
        public int leader;
        
        public City(String name)
        {
            this.name = name;
            this.sum = 0;
            this.leader = 0;
        }
    }
    
    public static class Heap
    {
        public City[] arr;
        public int maxSize;
        public int size;
        
        public Map<String, Integer> cityToIndex;
        
        public Heap(int maxSize)
        {
            this.maxSize = maxSize;
            size = 0;
            arr = new City[maxSize];
            cityToIndex = new HashMap<>(maxSize * 3 / 2);
        }
        
        public void add(City c)
        {
            arr[size] = c;
            shiftUp(size++);
        }
        
        public void update(int index, long value)
        {
            long oldValue = arr[index].sum;
            arr[index].sum = value;
            if (oldValue < value)
                shiftUp(index);
            else
                shiftDown(index);
        }
        
        public void shiftUp(int index)
        {
            int parentIndex = (index - 1) / 2;
            City c = arr[index];
            while (index > 0 && arr[parentIndex].sum < c.sum)
            {
                arr[index] = arr[parentIndex];
                cityToIndex.put(arr[index].name, index);
                index = parentIndex;
                parentIndex = (index - 1) / 2;
            }
            arr[index] = c;
            cityToIndex.put(arr[index].name, index);
        }
        
        public void shiftDown(int index)
        {
            City c = arr[index];
            while (index < size / 2)
            {
                int child1 = 2 * index + 1;
                int child2 = child1 + 1;
                int childLarger = (child2 < size && arr[child1].sum < arr[child2].sum) ? child2 : child1;
                if (c.sum >= arr[childLarger].sum)
                    break;
                arr[index] = arr[childLarger];
                cityToIndex.put(arr[index].name, index);
                index = childLarger;
            }
            arr[index] = c;
            cityToIndex.put(arr[index].name, index);
        }
        
        public City addOrUpdate(String name, long sum)
        {
            Integer idx = cityToIndex.get(name);
            City c;
            if (idx == null)
            {
                c = new City(name);
                c.sum = sum;
                add(c);
            }
            else
            {
                c = arr[idx];
                update(idx, c.sum + sum);
            }
            return c;
        }
        
        public City getLeader()
        {
            if (size == 0)
                return null;
            if (size == 1)
                return arr[0];
            long place1 = arr[0].sum;
            long place2;
            if (size > 2)
            {
                place2 = (arr[1].sum > arr[2].sum) ? arr[1].sum : arr[2].sum;
            }
            else
                place2 = arr[1].sum;
            return (place1 == place2) ? null : arr[0];
        }
    }
}
