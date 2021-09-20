import java.util.NoSuchElementException;
import java.util.Arrays;
/**
 * This class implements PriorityQueue using heap structure, with each node
 * being a Flashcard object. It contains all methods in PriorityQueue: add, poll
 * clear, isEmpty, and peek with several helper methods that help with testing.
 * @instance variables: Flashcard[] flashcard; int numItems.
 */
public class FlashcardPriorityQueue implements PriorityQueue <Flashcard>{
  private Flashcard[] flashcard;
  private int numItems;


  /**
  * Creates an empty priority queue.
  */
  public FlashcardPriorityQueue(){
    flashcard = new Flashcard[5];
    numItems = 0;
  }

  /** Helper method that helps swapping during bubbling up */
  private void swapUp(int currentIndex){
    //When we reached the first two indices of the array which are constantly null, we return.
    if(flashcard[currentIndex/3 + 1] == null){
      return;
    }else{
      //If the parent of the currentIndex is greater than it, swap them and call swapUp recursively. Else, do nothing.
      if(flashcard[currentIndex/3 + 1].compareTo(flashcard[currentIndex]) > 0){
        Flashcard temp = flashcard[currentIndex/3 + 1];
        flashcard[currentIndex/3 + 1] = flashcard[currentIndex];
        flashcard[currentIndex] = temp;
        swapUp(currentIndex/3 + 1);
      }
    }
  }

  /** Helper method that helps swapping during bubbling down */
  private void reheap(int currentIndex){
    int minChildIndex = (currentIndex - 1) * 3;
    //Finding the minimum child of the currentIndex under three different situations.
    //When currentIndex has all three children, find the smallest one.
    if((currentIndex - 1) * 3 + 2 <= numItems + 1){
      for (int i = 1; i < 3; i++){
        if (flashcard[minChildIndex].compareTo(flashcard[(currentIndex - 1) * 3 + i]) > 0){
          minChildIndex = (currentIndex - 1) * 3 + i;
        }
      }
    }
    //When currentIndex has only two children, find the smaller one.
    else if (((currentIndex - 1) * 3 + 1) <= numItems + 1){
      if (flashcard[minChildIndex].compareTo(flashcard[(currentIndex - 1) * 3 + 1]) > 0){
        minChildIndex = (currentIndex - 1) * 3 + 1;
      }
    }else if (((currentIndex - 1) * 3) > numItems + 1){
      return;
    }
    //If the minimum child of the currentIndex is less than it, swap them and call reheap recursively. Else, do nothing.
    if (flashcard[minChildIndex].compareTo(flashcard[currentIndex]) < 0){
      Flashcard temp = flashcard[minChildIndex];
      flashcard[minChildIndex] = flashcard[currentIndex];
      flashcard[currentIndex] = temp;
      reheap(minChildIndex);
    }
  }

  /** Adds the given item to the queue. */
  public void add(Flashcard item){
    //Resize the array if necessary.
    if(numItems == flashcard.length - 2){
      Flashcard[] newflashcard = new Flashcard[2 * this.flashcard.length];
      for(int i = 2; i <= flashcard.length - 1; i ++){
        newflashcard[i] = flashcard[i];
      }
      this.flashcard = newflashcard;
    }
    //Add the item into the array and update numItems, before trying bubbling it up to the right position
    flashcard[numItems + 2] = item;
    numItems ++;
    swapUp(numItems + 1);

  }


  /** Removes the first item according to compareTo from the queue, and returns it.
   * Throws a NoSuchElementException if the queue is empty.
   */
  public Flashcard poll(){
    //If the queue is empty, we throw a NoSuchElementException.
    if(this.isEmpty()){
      throw new NoSuchElementException();
    }
    //Swaps the last index with the root node, and removes the original root before considering bubbling the new root down to the correct position.
    Flashcard tempcard = flashcard[2];
    flashcard[2] = flashcard[numItems + 1];
    flashcard[numItems + 1] = null;
    numItems --;
    reheap(2);
    return tempcard;
  }

  /** Returns the first item according to compareTo in the queue, without removing it.
   * Throws a NoSuchElementException if the queue is empty.
   */
  public Flashcard peek(){
    if(this.isEmpty()){
      throw new NoSuchElementException();
    }
    return flashcard[2];
  }

  /** Helper method to get the actual array when testing   */
  private Flashcard[] getFlashCard(){
    return this.flashcard;
  }

  /** Helper method to get the actual numItems when testing   */
  private int getNumItems(){
    return this.numItems;
  }

  /** Returns true if the queue is empty. */
  public boolean isEmpty(){
    if (numItems == 0){
      return true;
    }
    return false;
  }

  /** Removes all items from the queue. */
  public void clear(){
    flashcard = new Flashcard[this.flashcard.length];
    numItems = 0;
  }

  /** Helper method to output a String representation of the current queue for the FlashcardDisplayer. */
  public String[] outPut(){
    String[] fileContent = new String[this.numItems];
    for(int i = 2; i < this.numItems + 2; i++){
      fileContent[i-2] = this.flashcard[i].getDueDate().toString() + "	" + this.flashcard[i].getFrontText() + "	" + this.flashcard[i].getBackText();
    }
    return fileContent;
  }

  /**
  * main method was written to test all the methods in the PriorityQueue interface implementation.
  */
  public static void main (String[] args){
    FlashcardPriorityQueue test = new FlashcardPriorityQueue();
    System.out.println("This tests if isEmpty method works when the priority queue is empty. The result should be true: " + test.isEmpty());
    test.add(new Flashcard("2020-02-02T01:05",	"Beijing",	"China"));
    System.out.println("This tests if add and peek methods work when the priority queue is empty. The result should print Beijing: " + test.peek().getFrontText());
    test.add(new Flashcard("2020-02-02T01:00",	"Kabul",	"Afghanistan"));
    System.out.println("This tests if add and peek methods work when the priority queue has an object with lower priority. The result should still print Kabul: " + test.peek().getFrontText());
    test.add(new Flashcard("2020-02-02T01:15",	"A",	"a"));
    System.out.println("This tests if add and peek methods work when the priority queue has an object with higher priority. The result should still print Kabul: " + test.peek().getFrontText());
    test.add(new Flashcard("2020-02-02T01:20",	"B",	"b"));
    test.add(new Flashcard("2020-02-02T01:15",	"C",	"c"));
    test.add(new Flashcard("2020-02-02T01:03",	"D",	"d"));
    System.out.println("This tests if add and peek methods work after resizing. The result should still print Kabul: " + test.peek().getFrontText());
    test.add(new Flashcard("2020-02-02T00:58",	"First",	"first"));
    System.out.println("This tests if add method works if we add a highest priority thing into the already established tree. The result should still print First: " + test.peek().getFrontText());
    System.out.println("This tests if poll method works according to the functionality of priority queue. The result should still print First, Kabul, D, Beijing, C, A, B: ");
    System.out.print(test.poll().getFrontText() + ", ");
    System.out.print(test.poll().getFrontText() + ", ");
    System.out.print(test.poll().getFrontText() + ", ");
    System.out.print(test.poll().getFrontText() + ", ");
    System.out.print(test.poll().getFrontText() + ", ");
    System.out.print(test.poll().getFrontText() + ", ");
    System.out.println(test.poll().getFrontText());
    try{
      Flashcard tmp = test.peek();
    }catch(NoSuchElementException e){
      System.err.println("This tests if peek method works when the queue is empty. This line appears here because the program throws a NoSuchElementException error.");
    }

    test.add(new Flashcard("2020-02-02T01:05",	"Beijing",	"China"));
    test.add(new Flashcard("2020-02-02T01:20",	"B",	"b"));
    test.clear();
    try{
      Flashcard tmp = test.peek();
    }catch(NoSuchElementException e){
      System.err.println("This tests if clear method works. This line appears here because the program throws a NoSuchElementException error.");
    }
  }
}
