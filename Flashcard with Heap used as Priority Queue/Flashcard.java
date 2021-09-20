import java.time.LocalDateTime;
/**
 * Creates a new flashcard with the given dueDate, text for the front
 * of the card (front), and text for the back of the card (back). Also
 * makes Flashcard objects comparable to each other by Overriding compareTo.
 * Contains a set method to update dueDate.
 * @instance variables String dueDate, String front, String back.
 */
public class Flashcard implements Comparable<Flashcard>{
  private String dueDate;
  private String front;
  private String back;

  /**
   * Creates a new flashcard with the given dueDate, text for the front
   * of the card (front), and text for the back of the card (back).
   * dueDate is in the format YYYY-MM-DDTHH-MM.
   */
  public Flashcard(String dueDate, String front, String back){
    this.dueDate = dueDate;
    this.front = front;
    this.back = back;
  }

  /**
   * Gets the text for the front of this flashcard.
   */
  public String getFrontText(){
    return this.front;
  }

  /**
   * Gets the text for the Back of this flashcard.
   */
  public String getBackText(){
    return this.back;
  }

  /**
   * Gets the time when this flashcard is next due.
   */
  public LocalDateTime getDueDate(){
    LocalDateTime dueTime = LocalDateTime.parse(this.dueDate);
    return dueTime;
  }

  /**
   * Set String object dueDate with the updatedDueDate.
   */
  public void setDueDate(LocalDateTime updateDueDate){
    this.dueDate = updateDueDate.toString();
  }

  /**
   * Overriding compareTo method to make flashcard comparable to each other.
   */
  public int compareTo(Flashcard flashcard){
    if (this.getDueDate().compareTo(flashcard.getDueDate()) < 0){
      return -1;
    }else if (this.getDueDate().compareTo(flashcard.getDueDate()) == 0){
      return 0;
    }else {
      return 1;
    }
  }
}
