import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class HashSimulator
{
    public int[] runHashSimulation(String[] strings, int size)
    {
        int[] results = new int[6]; 
        int ccount = 0, pcount = 0;

        // H1
        String[] ht = new String[size];
        for (String s : strings)
        {
            int h = H1(s, size);
            if (ht[h] != null)
                ccount++;
            while (ht[h] != null)
            {
                pcount++;
                h++;
                if (h == size)
                    h = 0;
            }
            ht[h] = s;
        }
        results[0] = ccount;
        results[1] = pcount;
        ccount = 0; pcount = 0;
        // H2
        ht = new String[size];
        for (String s : strings)
        {
            int h = H2(s, size);
            if (ht[h] != null)
                ccount++;
            while (ht[h] != null)
            {
                pcount++;
                h++;
                if (h == size)
                    h = 0;
            }
            ht[h] = s;
        }
        results[2] = ccount;
        results[3] = pcount;
        ccount = 0; pcount = 0;
        // H3
        ht = new String[size];
        for (String s : strings)
        {
            int h = H3(s, size);
            if (ht[h] != null)
                ccount++;
            while (ht[h] != null)
            {
                pcount++;
                h++;
                if (h == size)
                    h = 0;
            }
            ht[h] = s;
        }
        results[4] = ccount;
        results[5] = pcount;

        return results;
    }

    public int H1(String astring, int size)
    {
        int wa = 0;
        for (char c : astring.toCharArray())
        {
            wa += c - 64;
        }
        return wa % size;
    }

    public int H2(String astring, int size)
    {
        long wa = 0;
        char[] chars = astring.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            wa += (chars[i] - 64) * Math.pow(26, i);
        }
        return (int)(wa % (long) size);
    }

    public int H3(String astring, int size)
    {
        long wa = 0;
        char[] chars = astring.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            wa += (chars[i] - 64) * Math.pow(chars[i], i);

        }
        return (int)(wa % (long) size);
    }
    
    public static void main(String[] args)
    {
        String[] files = {"37names.txt", "792names.txt", "5705names.txt"};
        HashSimulator hs = new HashSimulator();
        try
        {
            for (String f : files)
            {
                // Setup for file reading
                int size = Integer.parseInt(f.split("n")[0]), track = 0;
                String[] input = new String[size];
                Scanner sc = new Scanner(new File(f));
                // Read the names from the file
                while(sc.hasNextLine())
                {
                    input[track++] = sc.nextLine();
                }
                // Run hashing simulation for this file
                System.out.println(String.format("File %s with %d names", f, size));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 2*size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 5*size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 10*size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 100*size)) + "\n");
            }
        }
        catch (FileNotFoundException e)
        {
        }
    }
}
