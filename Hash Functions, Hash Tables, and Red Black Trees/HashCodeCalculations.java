import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;

/**
 * In class lab for learning about hash code functions and collisions.
 * @author arafferty
 * @author Wenlai Han, Cathy Guang
 */
public class HashCodeCalculations {

    /**
     * Always returns 0.
     */
    public static int hashCode0(String s) {
        return 0;
    }

    /**
     * hashCode1()
     * hashCode1 returns 0 if the string is empty, and returns the unicode of first
     * character in the string if not empty.
     */
    public static int hashCode1(String s) {
        if(s.isEmpty()) {
            return 0;
        } else {
            return (int) s.charAt(0);
        }
    }

    /**
     * hashCode2()
     * Returns the sum of all unicode values of characters in the string
     */
    public static int hashCode2(String s) {
        int hashCode = 0;
        for(int i = 0; i < s.length(); i++) {
            hashCode += (int) s.charAt(i);
        }
        return hashCode;
    }

    /**
     * hashCode3()
     * Returns the sum of values of unicode of each character multiplied by the factor 129.
     */
    public static int hashCode3(String s) {
        int hashCode = 0;
        for(int i = 0; i < s.length(); i++) {
            hashCode = 129*hashCode + (int) s.charAt(i);
        }
        return hashCode;
    }



    /**
     * Compression function that takes a hash code (positive or negative) and
     * the number of buckets we have to use in our hash table, and compresses
     * the hash code into the range [0, numberOfBuckets).
     * Returns the hash code after compression. 
     */
    public static int compressToSize(int hashCode, int numberOfBuckets) {
        int a = hashCode % numberOfBuckets;
        if (a < 0){
          a += numberOfBuckets;
        }
        return a;
    }

    /**
     * Counts the number of buckets that have no words stored at them - i.e.,
     * they have value 0 - and calculates what proportion of the total buckets
     * that is.
     */
    public static double proportionOfBucketsWithNoWords(int[] buckets) {
        int emptyBucketCount = 0;
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] == 0) {
                emptyBucketCount++;
            }
        }
        return emptyBucketCount*1.0/buckets.length;
    }


    /**
     * Returns the maximum value in a single bucket
     */
    public static int getMaxBucketValue(int[] buckets) {
        int max = -1;//Safe starting value since all buckets[i] should be >= 0
        for(int i = 0; i < buckets.length; i++) {
            if(buckets[i] > max) {
                max = buckets[i];
            }
        }
        return max;
    }

    /**
     * Returns the average number of words in each non-empty bucket
     */
    public static double getAverageInNonEmptyBuckets(int[] buckets) {
        int totalCount = 0;
        int totalNonEmpty = 0;
        for(int i = 0; i < buckets.length; i++) {
            totalCount += buckets[i];
            if(buckets[i] != 0) {
                totalNonEmpty++;
            }
        }
        return totalCount*1.0/totalNonEmpty;
    }


    /**
     * collisionCounter()
     * Calculates how many words would be placed in each bucket in the array.
     * Each individual word is only counted once.
     * @param numBuckets number of spots to include in the array
     * @param file file to read from
     * @param hashCodeFunctionToUse which of the hash functions to use
     * @return an array that indicates how many different words are place in index 0, 1, etc.
     */
    public static int[] collisionCounter(int numBuckets, String file, int hashCodeFunctionToUse) {
        //Initialize the variables that are needed to count collisions (an array, a set)
        int[] test = new int[numBuckets];
        Set<String> wordsAdded = new HashSet<String>();
		//Read each line of the file and split each line into words
        try {
            Scanner scanner =  new Scanner(new File(file));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine().strip();
                String[] words = line.split(" ");
                for (String word : words){
                	//If a same word was not already added
                    if (!wordsAdded.contains(word)){
                        wordsAdded.add(word);
                        //Determine which hashCode function to use base on parameter.
                        if (hashCodeFunctionToUse == 0){
                            test[compressToSize(hashCode0(word), numBuckets)] ++;
                        }else if (hashCodeFunctionToUse == 1){
                            test[compressToSize(hashCode1(word), numBuckets)] ++;
                        }else if (hashCodeFunctionToUse == 2){
                            test[compressToSize(hashCode2(word), numBuckets)] ++;
                        }else if (hashCodeFunctionToUse == -1){
                            test[compressToSize(word.hashCode(), numBuckets)] ++;
                        }else {
                            test[compressToSize(hashCode3(word), numBuckets)] ++;
                        }
                    }
                }
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return test;
    }
    
    /**
     * main method
     * Test and run the method of collision 
     */
    public static void main(String[] args) {
        int[] test = HashCodeCalculations.collisionCounter(196613, "words.txt", 0);
        System.out.println("This tests the collision numbers using hashCodeFunction 0, with number of bucket being 196613: " + Arrays.toString(test));
        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
        System.out.println("");
       
//        int[] test = HashCodeCalculations.collisionCounter(196613, "words.txt", 1);
//        System.out.println("This tests the collision numbers using hashCodeFunction 1, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(196613, "words.txt", 2);
//        System.out.println("This tests the collision numbers using hashCodeFunction 2, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(196613, "words.txt", 3);
//        System.out.println("This tests the collision numbers using hashCodeFunction 3, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(200000, "words.txt", 0);
//        System.out.println("This tests the collision numbers using hashCodeFunction 0, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//       
//        int[] test = HashCodeCalculations.collisionCounter(200000, "words.txt", 1);
//        System.out.println("This tests the collision numbers using hashCodeFunction 1, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(200000, "words.txt", 2);
//        System.out.println("This tests the collision numbers using hashCodeFunction 2, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//
//        int[] test = HashCodeCalculations.collisionCounter(200000, "words.txt", 3);
//        System.out.println("This tests the collision numbers using hashCodeFunction 3, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//
//        int[] test = HashCodeCalculations.collisionCounter(196613, "HoundOfTheBaskervilles.txt", 0);
//        System.out.println("This tests the collision numbers using hashCodeFunction 0, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(196613, "HoundOfTheBaskervilles.txt", 1);
//        System.out.println("This tests the collision numbers using hashCodeFunction 1, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(196613, "HoundOfTheBaskervilles.txt", 2);
//        System.out.println("This tests the collision numbers using hashCodeFunction 2, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(196613, "HoundOfTheBaskervilles.txt", 3);
//        System.out.println("This tests the collision numbers using hashCodeFunction 3, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(200000, "HoundOfTheBaskervilles.txt", 0);
//        System.out.println("This tests the collision numbers using hashCodeFunction 0, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//       
//        int[] test = HashCodeCalculations.collisionCounter(200000, "HoundOfTheBaskervilles.txt", 1);
//        System.out.println("This tests the collision numbers using hashCodeFunction 1, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//       
//        int[] test = HashCodeCalculations.collisionCounter(200000, "HoundOfTheBaskervilles.txt", 2);
//        System.out.println("This tests the collision numbers using hashCodeFunction 2, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//
//        int[] test = HashCodeCalculations.collisionCounter(200000, "HoundOfTheBaskervilles.txt", 3);
//        System.out.println("This tests the collision numbers using hashCodeFunction 3, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//
//        int[] test = HashCodeCalculations.collisionCounter(196613, "HoundOfTheBaskervilles.txt", -1);
//        System.out.println("This tests the collision numbers using hashCodeFunction 0, with number of bucket being 196613: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 196613 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
//        
//        int[] test = HashCodeCalculations.collisionCounter(200000, "words.txt", -1);
//        System.out.println("This tests the collision numbers using hashCodeFunction 3, with number of bucket being 200000: " + Arrays.toString(test));
//        System.out.println("The proportion of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) + ". The exact number of empty bucket in this array is: " + HashCodeCalculations.proportionOfBucketsWithNoWords(test) * 200000 + ". And the average items stored in the same bucket is " + HashCodeCalculations.getAverageInNonEmptyBuckets(test) + ". The maximum number of items stored in a single bucket is " + getMaxBucketValue(test));
//        System.out.println("");
    }

}
