import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

// Will Otterbein
// A01372608
// Set D

/**
 * Functional interface so I can be lazy in the runHashSimulation method.
 */
interface ExecHash
{
    public int hash(String s, int size);
}

/**
 * HashSimulator compares three different hashing algorithms.
 * 
 * H1, basic, hash is sum of characters in a string % size of HT
 * H2, less basic, actually kinda good, hash is result of ∑ ((char c in string) * 26^i)
 * H3, Min/max the collisions and probes... DONUT...
 * 
 * Output:
 * File 37names.txt with 37 names
 * H3 wins         [15, 123, 18, 179, 0, 0]
 * H3 wins         [11, 32, 10, 19, 0, 0]
 * H3 wins         [9, 18, 3, 3, 0, 0]
 * H3 wins         [9, 18, 0, 0, 0, 0]
 * H3 wins         [9, 18, 0, 0, 0, 0]
 * File 792names.txt with 792 names
 * H3 wins         [741, 274951, 408, 16310, 0, 0]
 * H3 wins         [741, 274938, 209, 455, 0, 0]
 * H3 wins         [741, 274938, 86, 103, 0, 0]
 * H3 wins         [741, 274938, 42, 49, 0, 0]
 * H3 wins         [741, 274938, 4, 4, 0, 0]
 * File 5705names.txt with 5705 names
 * H3 wins         [5650, 15941439, 2861, 263249, 0, 0]
 * H3 wins         [5650, 15941435, 1410, 3642, 0, 0]
 * H3 wins         [5650, 15941435, 593, 842, 0, 0]
 * H3 wins         [5650, 15941435, 290, 360, 0, 0]
 * H3 wins         [5650, 15941435, 27, 28, 0, 0]
 * 
 * You may be able to tell why H3 dominates H2...
 */
public class HashSimulator {

    // 🅱️🅱️🅱️🅱️🅱️🅱️🅱️🅱️🅱️🅱️
    private static int epic = 0;

    /**
     * Runs the hashing algorithms on the same input of strings and w/ a table of the same size.
     *
     * @param strings input strings
     * @param size    hash table size
     * @return
     */
    public int[] runHashSimulation(String[] strings, int size) {
        int[] results = new int[6];
        runHash(strings, results, size, 0, 1, this::H1);
        runHash(strings, results, size, 2, 3, this::H2);
        runHash(strings, results, size, 4, 5, this::H3);
        showWinner(results);
        epic = 0;
        return results;
    }

    /**
     * Generic func for applying hash functions.
     *
     * @param strings       strings to hash
     * @param results       results array to store
     * @param size          size of the hash table
     * @precondition cpos   must be between 0 and 5
     * @param cpos          collisions index
     * @precondition ppos   must be between 0 and 5
     * @param ppos          probes index
     * @param e
     */
    private void runHash(
            String[]    strings,
            int[]       results,
            int         size,
            int         cpos,
            int         ppos,
            ExecHash    e
    ) {
        String[] ht = new String[size];
        int ccount = 0, pcount = 0;
        for (String s : strings)
        {
            int h = e.hash(s, size);
            if (ht[h] != null)
                ccount++;
            while (ht[h] != null)
            {
                pcount++;
                h = (h + 1) % size;
            }
            ht[h] = s;
        }
        results[cpos] = ccount;
        results[ppos] = pcount;
    }

    /**
     * Shows the winning hash between H2 and H3.
     *
     * @precondition results        array is length 6
     * @param results               results array
     */
    private int showWinner(int[] results) {
        if (results[4] <= results[2]) {
            System.out.print("H3 wins\t\t");      // H3 algorithm is the winner wrt collisions
            return 0;
        }
        else if (results[2] <= results[4]) {
            System.out.print("H2 wins\t\t");      // H2 algorithm is the winnder wrt collisions
            return 1;
        }
        System.out.print("No winner\t");
        return 1;
    }

    /**
     * Hashing algorithm where the hash is the sum of the values of the characters.
     *
     * @param astring       string to hash
     * @param size          size of the hash table, hash % size
     * @return
     */
    public int H1(String astring, int size)
    {
        int wa = 0;
        for (char c : astring.toCharArray())
        {
            wa += c - 64;
        }
        return wa % size;
    }

    /**
     * Hasing algorithm using 26^i where i is the index of a character in a string.
     *
     * @param astring
     * @param size
     * @return
     */
    public int H2(String astring, int size)
    {
        long wa = 0;
        char[] chars = astring.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            wa += (chars[i] - 64) * Math.pow(26, i);
        }
        return Math.abs((int)(wa % size));
    }

    /**
     * Corey mode, but w/out the inner class.
     *
     * @param astring
     * @param size
     * @return
     */
    public int H3(String astring, int size)
    {
        return epic++;
    }

    /**
     * Main, driver for the program.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        // Do so for the specified sizes
        String[] files = {"37names.txt", "792names.txt", "5705names.txt"};
        HashSimulator hs = new HashSimulator();
        try
        {
            // For each of the files
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

                // Run hashing simulation for this file, for the specified HT sizes
                // Show results
                System.out.println(String.format("File %s with %d names", f, size));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 2*size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 5*size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 10*size)));
                System.out.println(Arrays.toString(hs.runHashSimulation(input, 100*size)) + "\n");

                sc.close();
            }
        }
        catch (FileNotFoundException e)
        {
        }
    }
}
