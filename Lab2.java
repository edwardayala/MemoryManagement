
import java.util.*;
import java.io.*;
import java.lang.Integer;

import static java.util.Collections.min;

public class Lab2 {
    private int [] memorySpace;
    private int [] virtualDisk;

    public Lab2(String s, int a) throws FileNotFoundException{
        File file = new File(s);                                // Create file object - string.txt
        Scanner reader = new Scanner(file);                     // Use scanner to parse file
        String fileText = reader.nextLine();                    // Read line of numbers
        String [] stringArr = fileText.split(", ");       // Split line of numbers at ', ' -> string array
        virtualDisk = new int[stringArr.length];                // Initialize virtualDisk (int array to hold string values)
        for (int i = 0; i < stringArr.length; i++) {            // Copy string array to int array
            virtualDisk[i] = Integer.parseInt(stringArr[i]);    // int element[i] = parsed int of string element[i]
        }
        memorySpace = new int[a];           // Initialize memorySpace (int array to hold frames)
        for(int i = 0; i < a; i++){         // Traverse memorySpace
            memorySpace[i] = -1;            // Populate memorySpace with -1 (placeholder)
        }
        reader.close();
    }

    public static void main(String [] args) throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter the file name: (string.txt)");
//        String inputFileName = input.nextLine();
        String inputFileName = "string.txt";

        System.out.println("Enter the how many frames do you wnat");
//        int num = input.nextInt();
        int num = 3;

        System.out.println("now enter your choice");
        int choice = input.nextInt();

        input.close();

        Lab2 lb2 = new Lab2(inputFileName, num);

        int [] frame = lb2.memorySpace;
        int [] str = lb2.virtualDisk;

        if(choice == 1){                // 1 : First-in, First-out (FIFO)
            FIFO_JY(str, frame);
            FIFO_EA(str, frame);
        }
        if(choice == 2){                // 2 : Optimal Algorithm (OPT)
            Optimal_YU(str, frame);
            Optimal_EA(str, frame);
        }
        if(choice == 3){                // 3 : Least Recently Used (LRU)
            LRU_YU(str, frame);
            LRU_EA(str, frame);
        }
        if(choice == 0){                // 0 : All
            FIFO_JY(str, frame);
            FIFO_EA(str, frame);
            System.in.read();
            Optimal_YU(str, frame);
            Optimal_EA(str, frame);
            System.in.read();
            LRU_YU(str, frame);
            LRU_EA(str, frame);
        }
    }

    public static void FIFO_JY(int [] a, int [] b){
        //merge
    }

    /*
     *   First-in, First-out (FIFO) Algorithm
     *   - Replace frame that was inserted first
     *   - Similar structure to a Queue
     *
     *
     *   ~ Implemented using Arrays, ArrayLists, and Vector
     *   ~ Traverse reference string array
     *   ~ Insert initial pages
     *   ~ Once initial pages are full, start replacing using FIFO Algorithm
     *
     *   * FIFO_EA ( reference string array, frames array ){}
     */
    public static void FIFO_EA(int [] a, int [] b) {
        long startTime = System.nanoTime();                     // Variables used to measure runtime and memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        ArrayList<Integer> refString = conver_arr_arrlst(a);    // Convert arrays to ArrayList
        ArrayList<Integer> frameList = conver_arr_arrlst(b);
        System.out.println("First-in, First-out (FIFO) - Edward Ayala \nReference String: "
                            + refString + "\nFrames: " + frameList);

        Vector<Integer> framesIndex = new Vector<>();       // Initialize and populate Vector object
        for(int i = 0; i < frameList.size(); i++){          // to keep track of indexes in frameList
            framesIndex.add(i, 0);
        }
        int pageFaults = 0;

        for (int i = 0; i < a.length; i++) {
            if (!frameList.contains(a[i])){
                if (i < b.length){              // If there are empty frames
                    frameList.set(i,a[i]);      // set frame at (i) with array element at [i]
                    framesIndex.set(i, i);      // set index at i with i
                    System.out.println(frameList);
                    pageFaults++;               // Increment pageFault
                }
                else{                           // no empty frame - replace
                    int first = framesIndex.indexOf(min(framesIndex));  // first = the index of the smallest element within frame index
                    frameList.set(first,a[i]);      // set frame at (first) to the string element at [i]
                    framesIndex.set(first, i);      // set index at (first) to position [i]
                    System.out.println(frameList);
                    pageFaults++;     // Increment pageFaults
                }
            }
        }
        long endTime = System.nanoTime();
        long runTime = (endTime - startTime)/1000000;
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = (endMemory - startMemory)/(1024);
        System.out.println("FIFO Page faults: " + pageFaults);
        System.out.println("Algorithm Runtime: " + runTime + "ms");
        System.out.println("Memory used: " + memoryUsed + "kB");
    }

    private static void Optimal_YU(int[] a, int[] b) {
        // merge
    }

    /*
    *   Optimal Algorithm (OPT)
    *   - Replace page that wont be used for the longest time
    *   - Replace page that has the largest index in the reference string
    *
    *
    *   ~ Implemented using ArrayLists
    *   ~ Traverse reference string
    *   ~ Insert initial pages
    *   ~ Once initial pages are full, start replacing using Optimal Algorithm
    *
    *   * Optimal_EA ( reference string array, frames array ){}
    */
    public static void Optimal_EA(int[]a, int [] b){
        long startTime = System.nanoTime();                     // Variables used to measure runtime and memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        ArrayList<Integer> refString = conver_arr_arrlst(a);        // Convert arrays to ArrayList
        ArrayList<Integer> frameList = conver_arr_arrlst(b);
        System.out.println("Optimal Algorithm (OPT) - Edward Ayala \nReference String: "
                            + refString + "\nFrames: " + frameList);

        int pageFaults = 0;             // Initialize pageFault and replaceIndex variables
        int replaceIndex;
        for (int i = 0; i < refString.size(); i++) {
            if (!frameList.contains(refString.get(i))) {    // If frameList does not contain refString value at (i)
                if (i < frameList.size()) {     // if iterator is smaller than frame size (inserting initial pages)
                    frameList.set(i, refString.get(i));     // set frame at (i) to refString value at (i)
                    pageFaults++;       // Increment pageFaults
                    System.out.println(frameList);
                }

                else {    // else (i > frameList.size()) - iterator is larger than frame size : Replace
                    replaceIndex = replaceIndex(frameList, refString, i);   // function to determine index of frame to be replaced
                    frameList.set(replaceIndex, refString.get(i));      // set frame at replaceIndex to refString at (i)
                    pageFaults++;       // Increment pageFaults
                    System.out.println(frameList);
                }
            }
        }
        long endTime = System.nanoTime();
        long runTime = (endTime - startTime)/1000000;
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = (endMemory - startMemory)/(1024);
        System.out.println("Optimal Page Faults: " + pageFaults);
        System.out.println("Algorithm Runtime: " + runTime + "ms");
        System.out.println("Memory used: " + memoryUsed + "kB");
    }

    static int replaceIndex(ArrayList frames, ArrayList refString, int index){
        int replaceIndex = -1;      // Initialize replaceIndex, last reference variables, (i & j) iterators
        int lastRef = index;
        int i,j;
        for (i = 0; i < frames.size(); i++){        // Traverse frame ArrayList
            for (j = index; j < refString.size(); j++){     // Traverse remaining elements of refString ArrayList
                if (frames.get(i) == refString.get(j)){     // if frame (i) == refString (j)
                    if (j > lastRef){       // if iterator j > lastRef
                        lastRef = j;        // change lastRef to j - index of refString that equaled frame at i
                        replaceIndex = i;   // change replaceIndex to indexOf(frame which equaled refString at j)
                    }
                    break;
                }
            }
            if (j == refString.size())      // if j is last value in refString ArrayList - return value (i)
                return i;
        }
        return (replaceIndex == -1) ? 0 : replaceIndex;     // return replaceIndex
    }

    private static void LRU_YU(int[] a, int[] b) {
        // merge
    }

    /*
     *   Least Recently Used Algorithm (LRU)
     *   - Replace page that is least recently used
     *   - Replace page that has the least number of references (counter)
     *   - Multiple implementation options
     *
     *
     *   ~ Implemented using ArrayLists
     *   ~ Traverse reference string
     *   ~ Insert initial pages starting from the last frame - using for each
     *   ~ Use counter to keep track of page references
     *   ~ Once initial pages are full, start replacing using Least Recently Used Algorithm
     *
     *   * LRU_EA ( reference string array, frames array ){}
     */
    public static void LRU_EA(int[]a, int [] b){
        long startTime = System.nanoTime();                     // Variables used to measure runtime and memory usage
        Runtime runtime = Runtime.getRuntime();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();
        ArrayList<Integer> refString = conver_arr_arrlst(a);        // Convert arrays to ArrayList
        ArrayList<Integer> frameList = conver_arr_arrlst(b);

        System.out.println("Least Recently Used (LRU) - Edward Ayala \nReference String: "
                            + refString + "\nFrames: " + frameList);

        int pageFaults = 0;        // Initialize refCount and pageFault variables
        int refCount=0;
        for(int i : refString)          // Traverse refString ArrayList - for each
        {
            if (!frameList.contains(i))           // If frameList does not contain refString int value
            {
                if(frameList.size() == b.length){
                    frameList.add(b.length,i);    // add refString int value to end of frameList
                    frameList.remove(0);
                    System.out.println(frameList);
                }
                else{
                    frameList.add(refCount,i);         // replace frame at refCount position with refString(i) value
                    System.out.println(frameList);
                }
                pageFaults++;                               // Increment pageFaults & refCount
                refCount++;
            }
            else{          // If frameList does contain refString int value
                frameList.remove((Integer)i);       // Remove refString<Integer> object from frameList
                frameList.add(frameList.size(),i);  // replace last frame with refString(i) value
                System.out.println(frameList);
            }

        }
        long endTime = System.nanoTime();
        long runTime = (endTime - startTime)/1000000;
        long endMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = (endMemory - startMemory)/(1024);
        System.out.println("LRU Page faults: " + pageFaults);
        System.out.println("Algorithm Runtime: " + runTime + "ms");
        System.out.println("Memory used: " + memoryUsed + "kB");
    }

    public static ArrayList<Integer> conver_arr_arrlst(int [] a){
        Integer [] newarr = new Integer[a.length];
        int i = 0;
        for (int value : a) {
            newarr[i++] = Integer.valueOf(value);
        }
        ArrayList<Integer> arrLst = new ArrayList<Integer>(Arrays.asList(newarr));

        return arrLst;
    }
    static boolean check(int [] arr, int toCheckValue){
        int res = Arrays.binarySearch(arr, toCheckValue);
        return (res > 0);
    }
}