import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.String;

/**
 * This class implements tree structure with map element inside node.
 * Each node except for the root will represent a String of length 1 
 * as well as a count. The children of a node will be characters that
 * could come after the current character to represent a word.
 * 
 * It contains instance variable root as the root of the tree, which
 * does not represent any letter.
 * 
 * 
 * 
 */
public class WordCountMap{

    private Node root;

    /**
     * Inner class Node that constructs every node with its children 
     * being a Map of nodes. Every node also contains a letter and a
     * count for the word that ends at that letter.
     */
    private class Node{
        private Map<String, Node> children;
        private String letter;
        private int count;

        public Node(){
            this(null, 0);
        }
        public Node(String letter, int count){
            this.children = new HashMap<String, Node>();
            this.letter = letter;
            this.count = count;
        }
    }

    /**
     * Constructs an empty WordCountMap.
     */
    public WordCountMap(){
        this.root = new Node("", 0);
    }

    /**
     * Adds 1 to the existing count for word, or adds word to the WordCountMap
     * with a count of 1 if it was not already present.
     */
    public void incrementCount(String word){
        String[] wordString = word.split("");
        //Calls helper method to recurse.
        this.incrementCountHelper(wordString, root, 0);
    }
    
    /**
     * Helper method of incrementCount().
     */
    private void incrementCountHelper(String[] word, Node startingNode, int currentWordIndex){
        int currentIndex = currentWordIndex;
        //Base case, if reaching the end of the word, increment the count and/or add the node into the map.
        if(currentIndex == word.length - 1){
            //If the children map doesn't contain the letter, puts a new node that represents that word and increments its count.
            if(startingNode.children.isEmpty()){
                Node newChild = new Node(word[currentIndex], 1);
                startingNode.children.put(newChild.letter, newChild);
            }else {
                //Only increment the count in certain node if the word has already existed.
            	if(startingNode.children.containsKey(word[currentIndex])) {
                    startingNode.children.get(word[currentIndex]).count++;

            	}
                //creates a new node to store the letter and increment count.
                else {
            		Node newChild = new Node(word[currentIndex], 1);
                    startingNode.children.put(newChild.letter, newChild);
            	}
            }
        }
        //recurse if still processing in the middle of the word
        else {
            //If the children map doesn't contain the letter, puts a new node that represents that word and increments its count.
            if(startingNode.children.isEmpty()){
                Node newChild = new Node(word[currentIndex], 0);
                startingNode.children.put(newChild.letter, newChild);
                incrementCountHelper(word, newChild, currentWordIndex + 1);
            }else{
                //If the letter exists in the children, recurse down the tree to the next letter
            	if(startingNode.children.containsKey(word[currentIndex])) {
            		Node currentNode = startingNode.children.get(word[currentIndex]);
            		incrementCountHelper(word, currentNode, currentWordIndex + 1);
            	}
                //If the letter does not exist in the children, add the node here and recurse down the next letter
                else {
            		Node newChild = new Node(word[currentIndex], 0);
                    startingNode.children.put(newChild.letter, newChild);
                    incrementCountHelper(word, newChild, currentWordIndex + 1);
            	}
            }
        }
    }


    /**
     * Remove 1 to the existing count for word. If word is not present, does
     * nothing. If word is present and this decreases its count to 0, removes
     * any nodes in the tree that are no longer necessary to represent the
     * remaining words.
     */
    public void decrementCount(String word){
        //If the tree has the word, split the word and call helper method to decrement its count.
        if(this.contains(word)){
            String[] wordString = word.split("");
            decrementCountHelper(wordString, root, 0);
        }
    }
    
    /**
     * Helper method of decrementCount().
     */
    private void decrementCountHelper(String[] word, Node reference, int currentIndex){
        //If reaching the last index of the word and the corresponding node has no child, call helper method to check is we have to remove nodes preceding it. If reaching the last index of the word and the corresponding node has child, decrement the count.
        if(currentIndex == word.length - 1){
            Node finalNode = reference.children.get(word[currentIndex]);
            finalNode.count--;
            if(finalNode.children.isEmpty()){
                decrementCountRemoveHelper(word, root, 0);
            }
        }
        //If not reaching the end of the word, recurse down the word.
        else{
            decrementCountHelper(word, reference.children.get(word[currentIndex]), currentIndex + 1);
        }
    }
    
    /**
     * Helper method of decrementCountHelper().
     */
    private void decrementCountRemoveHelper(String[] word, Node reference, int currentIndex){
        //If reaching the last letter in the word, check if the count is 0. Remove the node if count is 0, and then remove its preceding nodes recursively.
        if (currentIndex == word.length - 1){
            if (reference.children.get(word[currentIndex]).count == 0){
                reference.children.remove(word[currentIndex]);
                if (word.length != 1){
                    String[] newWord = Arrays.copyOfRange(word, 0, word.length - 1);
                    decrementCountRemoveHelper(newWord, root, 0);
                }
            }
        }else {
            decrementCountRemoveHelper(word, reference.children.get(word[currentIndex]), currentIndex + 1);
        }
    }

    /**
     * Returns true if word is stored in this WordCountMap with
     * a count greater than 0, and false otherwise.
     */
    public boolean contains(String word){
        //split the word and call helper method
        String[] wordString = word.split("");
        return containsHelper(wordString, root, 0);
    }
    /**
     * Helper method of contains()
     */
    private boolean containsHelper(String[] word, Node referenceNode, int currentIndex){
        //If reaching the end of the word, and all the letters match, return true.
        if(currentIndex == word.length - 1){
            if(referenceNode.children.containsKey(word[currentIndex]) && referenceNode.children.get(word[currentIndex]).count > 0){
                return true;

            }else{
                return false;
            }
        }
        //If any middle letter doesn't appear in the tree, return false.
        else{
            if(!referenceNode.children.containsKey(word[currentIndex])){
                return false;
            }
            //If the children map is empty in the middle of the recursion, we return false.
            else if(referenceNode.children.isEmpty()){
                return false;
            }else{
                boolean found = containsHelper(word, referenceNode.children.get(word[currentIndex]), currentIndex + 1);
                return found;
            }
        }
    }

    /**
     * Returns the count of word, or -1 if word is not in the WordCountMap.
     * Implementation must be recursive, not iterative.
     */
    public int getCount(String word){
        //If the tree doesn't contain the word, return -1.
        if (!this.contains(word)){
            return -1;
        }
        //split the word and call helper method
        else{
            String[] wordString = word.split("");
            return getCountHelper(wordString, root, 0);
        }
    }
    
    /**
     * Helper method of getCount().
     */
    private int getCountHelper(String[] word, Node node, int currentIndex){
        //If reaching the end of the word, return its count. Otherwise, recursively call the method.
        if(currentIndex == word.length - 1){
            return node.children.get(word[currentIndex]).count;
        }else{
            int result = getCountHelper(word, node.children.get(word[currentIndex]), currentIndex + 1);
            return result;
        }
    }

    /**
    * Returns a list of WordCount objects, one per word stored in this
    * WordCountMap, sorted in decreasing order by count.
    */
    public List<WordCount> getWordCountsByCount(){
        List<WordCount> wordCountList = new ArrayList<WordCount>();
        String word = "";
        //If the tree is empty, return an empty wordCountList
        if(root.children.isEmpty()){
            return wordCountList;
        }
        //Otherwise, call helper method
        else{
            wordCountList = this.getWordCountHelper(word, root, wordCountList);
        }
        //Sort the wordCountList using insertion sort.
        for(int i = 1; i < wordCountList.size(); i++){
            WordCount itemToInsert = wordCountList.get(i);
            int j = i - 1;
            for(; j >= 0 && itemToInsert.getCount() > wordCountList.get(j).getCount(); j--){
            }
            wordCountList.remove(i);
            wordCountList.add(j + 1, itemToInsert);
        }
        return wordCountList;
    }
    
    /**
     * Helper method of getWordCountsByCount()
     */
    private List<WordCount> getWordCountHelper(String word, Node node, List<WordCount> wordCountList){
        String localWord;
        int count;
        //If reaching the leaf, add the word and its count to a WordCount object.
        if(node.children.isEmpty()){
            count = node.count;
            WordCount thisWord = new WordCount(word, count);
            //If the word is not empty, add the WordCount object to our list
            if(thisWord.getWord().length() != 0){
                wordCountList.add(thisWord);
            }
            
            return wordCountList;
        }
        //Using iterator to iterate through every single node in the children map, and decrement according to the same rule above.
        else{
            Iterator<String> iterator = node.children.keySet().iterator();
            while(iterator.hasNext()){
                String key = iterator.next();
                Node referenceNode = node.children.get(key);
                String nextLetter = referenceNode.letter;
                localWord = word + nextLetter;
                //If reaching a node that has a non-zero count, add its corresponding WordCount into our list.
                if (referenceNode.count != 0 && !referenceNode.children.isEmpty()) {
                    WordCount thisWord = new WordCount(localWord, referenceNode.count);
                    wordCountList.add(thisWord);
                }
                this.getWordCountHelper(localWord, referenceNode, wordCountList);
            }
            return wordCountList;
        }
    }

    /**
    * Returns a count of the total number of nodes in the tree.
    * A tree with only a root is a tree with one node; it is an acceptable
    * implementation to have a tree that represents no words have either
    * 1 node (the root) or 0 nodes.
    */
    public int getNodeCount(){
        //If the tree is empty, return 1.
        if(root.children.isEmpty()){
            return 1;
        }else{
            int count = 0;
            //Call helper method
            count = getNodeCountHelper(root, count);
            return count;
        }
    }
    /**
     * Helper method of getNodeCount()
     */
    private int getNodeCountHelper(Node node, int count){
        //If reaching the leaf, increment the number of nodes.
        if(node.children.isEmpty()){
            return count ++;
        }
        //Otherwize, iterate through the whole children map using iterator and increment the number of nodes accordingly.
        else{
            Iterator<String> iterator = node.children.keySet().iterator();
            while(iterator.hasNext()){
                count ++;
                count = this.getNodeCountHelper(node.children.get(iterator.next()), count);
            }
            return count;
        }
    }
    
    /**
     * Tests all methods in WordCountMap implementaion.
     */
    public static void main(String[] args){
        WordCountMap test = new WordCountMap();
        test.incrementCount("Cathy");
        
        System.out.println("This tests if the incrementCount method works. If it does, \"C\" should be printed: " + test.root.children.get("C").letter);
        System.out.println("This tests if the incrementCount method works. If it does, \"1\" should be printed: " + test.getCount("Cathy"));
        
        test.decrementCount("Cathy");
        System.out.println("This tests whether we successfully remove a existing word and its related nodes from the map. The count result should be -1: " + test.getCount("Cathy"));
        
        test.incrementCount("Cathy");
        test.incrementCount("Cathy");
        
        test.decrementCount("Cat");
        System.out.println("This tests whether we successfully remove a non-existing word from the map. The count result should be -1: " + test.getCount("Cat"));
        
        
        test.decrementCount("Cathy");
        System.out.println("This tests whether we successfully remove a existing word with several counts from the map. The count result should be 1: " + test.getCount("Cathy"));
        test.incrementCount("Cathye");
        test.decrementCount("Cathy");
        System.out.println("This tests whether we successfully remove a existing word with count 1 from the map. This shouldn't remove any nodes since another word is using these nodes. The result should be 1: " + test.getCount("Cathye"));
        
        test.decrementCount("Cathye");
        System.out.println("This tests whether we successfully remove a non-existing word from the map. The method is not supposed to do anything result should be -1: " + test.getCount("Cathye"));
        
        System.out.println("This tests if the contains method would return false if this list doesn't contain the word. The result should be false: " + test.contains("a"));
        test.incrementCount("Cathy");
        System.out.println("This tests if the contains method would return true if this list does contain the word. The result should be true: " + test.contains("Cathy"));
        System.out.println("This tests if the contains method would return false if this word exceed the leaf of the tree. The result should be false: " + test.contains("Cathye"));
        System.out.println("This tests if the contains method would return false if the count of the word is 0. The result should be false: " + test.contains("C"));
        
        test.incrementCount("Cathy");
        System.out.println("This tests getting the count of a word that's not stored in the tree, which should return -1: " + test.getCount("Ca"));
        System.out.println("This tests getting the count of a word that's stored in the tree, which should return 2: " + test.getCount("Cathy"));
        test.incrementCount("Cathy");
        test.incrementCount("Cathy");
        test.incrementCount("Cathyee");
        test.incrementCount("Happyyyee");
        System.out.println("This tests whether getNodeCount gets the number of the nodes in a tree, which should return 16: " + test.getNodeCount());
        
        test.incrementCount("Cathy");
        test.incrementCount("Bike");
        test.incrementCount("Bite");
        test.incrementCount("Biae");
        test.incrementCount("Cathyeee");
        List<WordCount> wordsAndCount = test.getWordCountsByCount();
        for (WordCount a : wordsAndCount){
                System.out.println(a.getWord() + ":" + a.getCount()); 
        }
        
    }
}
