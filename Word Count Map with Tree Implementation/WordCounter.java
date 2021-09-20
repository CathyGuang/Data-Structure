import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.lang.Character;
import java.io.PrintWriter;
import java.lang.Integer;

/**
 * This class uses WordCountMap object to store the words in an
 * input file and conduct operations according to the users' demand.
 * 
 * If the user input only the file name, prints all words and counts
 * in the file, sorted in decreasing order by counts. Format must match
 * that given above of word:count.
 * 
 * If the user input the file name, the number of items, and the target
 * HTML file name, writes an HTML string representing the words in 
 * textFileName to the file outFileName, with up to numberOfWordsToInclude
 * words included.
 */
public class WordCounter{
    
    public static void main (String[] args){
        //loads StopWords.txt files and add to stopWord list
        WordCountMap map = new WordCountMap();
        File stopWordFile = new File("StopWords.txt");
        List<String> stopWordList = new ArrayList<String>();
        try{
        	Scanner scanner1 = new Scanner(stopWordFile);
        	
        	while (scanner1.hasNextLine()){
            	String stopWord = scanner1.nextLine();
            	stopWordList.add(stopWord);
        	}
            scanner1.close();
        }catch(FileNotFoundException e){
        	System.err.println("File not found!");
        }
        
        /**loads all words in the input text file, and "normalize" them:
        remove any punctuation that's attached to them at the beginning 
        or at the end, and make them lower case. This avoids having multiple 
        counts.
        */
        File file = new File(args[0]);
        Scanner scanner;
        try{
        	scanner = new Scanner(file);
        	while(scanner.hasNextLine()){
            	String eachLine = scanner.nextLine();
            	String[] words = eachLine.split(" ");
            	for(int i = 0; i < words.length; i ++){
            		words[i] = words[i].toLowerCase();
                    while(words[i].length() != 0 && !Character.isLetterOrDigit(words[i].charAt(0))){
                        words[i] = words[i].substring(1);
                    }
                    while(words[i].length() != 0 && !Character.isLetterOrDigit(words[i].charAt(words[i].length() - 1))){
                        words[i] = words[i].substring(0, words[i].length() - 1);
                    }
                }
            	for (String word : words){
                	if (!stopWordList.contains(word) && word != "" && word != " "){
                    	map.incrementCount(word);
                	}
            	}
            }
            scanner.close();
        }catch(FileNotFoundException e){
        	System.err.println("File not found!");
        }
        
        //Prints all words out if only one argument taken from the user. Otherwise, output the HTML file that draws a wordCloud.
        List<WordCount> wordsAndCount = map.getWordCountsByCount();
        if(args.length == 1){
            for (WordCount a : wordsAndCount){
                System.out.println(a.getWord() + ":" + a.getCount()); 
            }
        }else if(args.length == 3){
            List<WordCount> wordInCloud = new ArrayList<WordCount>();
            PrintWriter toFile = null;
            wordInCloud = map.getWordCountsByCount().subList(0, Integer.parseInt(args[1]));
            
            try{
                toFile = new PrintWriter(args[2]);
                toFile.print(WordCloudMaker.getWordCloudHTML(args[2], wordInCloud));
                toFile.close();
            }
            catch (FileNotFoundException e){
                System.err.println("HTML file not found!");
            }
        }
    }
}