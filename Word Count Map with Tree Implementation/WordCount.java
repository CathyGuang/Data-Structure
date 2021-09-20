/**
 * This class creates WordCount object that represents the word
 * and its count.
 */
public class WordCount{
    private String word;
    private int count;
    
    /**
     * Constructs a WordCount object.
     */
    public WordCount(String word, int count){
        this.word = word;
        this.count = count;
    }
    
    /**
     * Gets the word stored by this WordCount
     */
    public String getWord(){
        return this.word;
    }

    /** 
     * Gets the count stored by this WordCount
     */
     public int getCount(){
         return this.count;
     }
}