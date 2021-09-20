import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;

/**
 * This class implements two version of Radix Sort algorithm. The first one uses
 * standard least significant digit radix sort to sort the words, while the second
 * one starts with a insertion sort of the first letter in each word.
 */
public class RadixSort {
	
	
	/**
	 * Uses standard least significant digit radix sort to sort the
	 * words.
	 *
	 * @param A list of words. Each word contains only lower case letters in a-z.
	 * @return A list with the same words as the input argument, in sorted order
	 */
	public static List<String> radixSort(List<String> words){
		
		int longestDigit = 0;
		List<String> sortedWordsInProgress = new ArrayList<String>();
		
		// Stores the biggest digit number possible in the input word file.
		for(String a : words) {
			int currentLength = a.length();
			if (currentLength > longestDigit) {
				longestDigit = currentLength;
			}
		}
		//Creates a map that has all keys being 26 letters and their values being the buckets (lists) that stores all the pending words.
		Map <String, List<String>> mapInProcess = new HashMap<String, List<String>>();
		
		for(int i = 0; i < longestDigit; i++) {
			
			mapInProcess.clear();//Clears the map when each round of sorting a certain digit is done.
			
			for (int j = 0; j < words.size(); j++) {
				String currentWord = words.get(j);
				String currentWordWithA = currentWord;
				while(currentWordWithA.length() < longestDigit) {
					currentWordWithA = currentWordWithA + "a";//Apending "a"s to the end of each word that doesn't have the longest length.
				}
				String currentCharacter = currentWordWithA.substring(currentWordWithA.length() - i - 1, currentWordWithA.length() - i);
				if(!mapInProcess.containsKey(currentCharacter)) {//If there isn't such key for the corresponding letter, we create one for that.
					mapInProcess.put(currentCharacter, new ArrayList<String>());
					
				}
				mapInProcess.get(currentCharacter).add(currentWord);//Adds words into buckets.
			}
			
			String[] sortedKeys = RadixSort.sortKey(mapInProcess);//Sorting all our keys in alphabetically order.
			
			sortedWordsInProgress.clear();
			for(int k = 0; k < sortedKeys.length; k++) {
				sortedWordsInProgress.addAll(mapInProcess.get(sortedKeys[k]));
			}
			words = sortedWordsInProgress;
		}
		
		return words;
	}
	
	/**
	 * This is a helper method that sorts all keys (all letters, up to 26) in the alphabetical order.
	 */
	public static String[] sortKey(Map<String, List<String>> unsortedMap){
		Set<String> unsortedKey = unsortedMap.keySet();
		String[] sortedArray = new String[unsortedKey.toArray().length];
		
		for (int k = 0; k < unsortedKey.toArray().length; k++) {
			sortedArray[k] = unsortedKey.toArray()[k].toString();
		}
		
        //using selection sort to compare the string values of different keys
		for (int i = 0; i < sortedArray.length; i++) {
			int smallestIndex = i;
			for (int j = i+1; j < sortedArray.length; j++) {
				if (sortedArray[j].compareTo(sortedArray[smallestIndex]) < 0) {
					smallestIndex = j;
				}
			}
			String temp = sortedArray[i];
			sortedArray[i] = sortedArray[smallestIndex];
			sortedArray[smallestIndex] = temp;
		}
		
		return sortedArray;
	}
	
	/**
	 * A variation on radix sort that sorts the words into buckets by their initial letter,
	 * and then uses standard radix sort to separately sort each of the individual 
	 * buckets. Recombines at the end to get a fully sorted list.
	 *
	 * @param A list of words. Each word contains only lower case letters in a-z.
	 * @return A list with the same words as the input argument, in sorted order
	 */
	public static List<String> msdRadixSort(List<String> words){
		Map<String, List<String>> initialLetterMap = new HashMap<String, List<String>>();
		List<String> sortedInitialList = new ArrayList<String>();
	    //Put each word into the initialLetterMap according to its first letter
        for(String eachWords : words) {
			if(!initialLetterMap.containsKey(eachWords.substring(0, 1))) {
				initialLetterMap.put(eachWords.substring(0, 1), new ArrayList<String>());
			}
			initialLetterMap.get(eachWords.substring(0, 1)).add(eachWords);
		}
		String[] sortedInitialLetter = sortKey(initialLetterMap);
		
        //Standard radix sort for each bucket of initialLetterMap
		for(int k = 0; k < sortedInitialLetter.length; k++) {
			sortedInitialList.addAll(radixSort(initialLetterMap.get(sortedInitialLetter[k])));
		}

		return sortedInitialList;
	}
	
	public static void main (String args[]) {
		File words = new File("words.txt");
		
        Scanner scanner = null;
        try {
            scanner = new Scanner(words);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        
        List<String> inputWords= new ArrayList<String>();
        
        List<Long> runTime = new ArrayList<Long>();//Used to keep track of multiple run times.
        for(int j = 0; j < 20; j++) {
        
	        while(scanner.hasNextLine()) {
	        	String currentWord = scanner.nextLine().toLowerCase();
	        	inputWords.add(currentWord);
	        	//The follow codes got commented is used to investigate how repetition in the input words could affect run time for two separate methods.
//	        	for (int repetitiveness = 5; repetitiveness < 506; repetitiveness+=100) {
//	        		inputWords.add(currentWord);
//	        	}
	        }
	        
	        Collections.shuffle(inputWords); //Shuffles input words for later subList action.
	        List<String> smallList = new ArrayList<String>();
	        int wordIndex;
	        for (int i = 0 ; i < 20; i++) {
	        	wordIndex = (int)(Math.random()*inputWords.size()); //Generates random index of words to repeat
	        	smallList.add(inputWords.get(wordIndex));
	        }
	        
	        List<String> makingRepetitiveWords = new ArrayList<String>();
	        int k = 0;
	        while (k < smallList.size()) {
	        	
	        	for (int repetitiveness = 0; repetitiveness < 5001; repetitiveness++) {
	        
	        		makingRepetitiveWords.add(smallList.get(k));//Repeat those words generated above accordingly.
	        	}
	        	k++;
	        }
	        
	        
	        
	        
	        //The following commented codes were used to investigate how run time changes with changes in the size of the lists.
//	        List<String> smallList = new ArrayList<String>();
//	        smallList = inputWords.subList(0, j*10000 + 10000);
//	        
	        //inputWords.removeRange(j*10000 + 10000, inputWords.size());
	       
	        long startTime = System.currentTimeMillis();	
	        RadixSort.msdRadixSort(makingRepetitiveWords);
	     	long endTime = System.currentTimeMillis();
	        
	     	runTime.add(endTime-startTime);
	    
        }
        scanner.close();
        long timer = 0;
	    for(Long eachRunTime : runTime) {
	    	System.out.println(eachRunTime.toString());
	    	timer = eachRunTime + timer;//Used to sum up run times of all 20 times.
	    	
	    }
	    
	    System.out.println("average run time: "+ timer/runTime.size());//Averages out all run times.
        
 
    
    
	}
}
