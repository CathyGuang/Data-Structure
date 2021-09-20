import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
/**
 * This is an interactive program letting the user to practice flashcards. They can
 * input, exit the program, or save flashcards in correct formats.
 * @instance variables: FlashcardPriorityQueue q.
 */
public class FlashcardDisplayer{
  private FlashcardPriorityQueue q;
  /**
   * Creates a flashcard displayer with the flashcards in file.
   * File has one flashcard per line. On each line, the date the flashcard
   * should next be shown is first (format: YYYY-MM-DDTHH-MM), followed by a tab,
   * followed by the text for the front of the flashcard, followed by another tab.
   * followed by the text for the back of the flashcard.
   */
  public FlashcardDisplayer(String file){
    q = new FlashcardPriorityQueue();
    File inputFile = new File(file);
    Scanner scanner;
    try{
      scanner = new Scanner(inputFile);
      while(scanner.hasNextLine()){
        String[] splittedLine = scanner.nextLine().split("	");
        Flashcard f = new Flashcard(splittedLine[0], splittedLine[1], splittedLine[2]);
        q.add(f);
      }
      scanner.close();
    }catch (FileNotFoundException e){
      System.err.println("Caution!! FileNotFoundException happened!");
      System.exit(1);
    }

  }

  /**
   * Writes out all flashcards to a file so that they can be loaded
   * by the FlashcardDisplayer(String file) constructor. Returns true
   * if the file could be written. The FlashcardDisplayer should still
   * have all of the same flashcards after this method is called as it
   * did before the method was called. However, it may be that flashcards
   * with the same exact same next display date are removed in a different order.
   */
  public boolean saveFlashcards(String outFile){
    PrintWriter toFile;
    boolean noError = false;
    //while the filename is in correct format, return true.
    while(!noError){
      try{
        toFile = new PrintWriter(outFile);
        if(q.isEmpty()){
          return false;
        }
        //Output each string value in the queue to the file
        String[] copyQueue = this.q.outPut();
        for (int i = 0; i < copyQueue.length; i++){
          toFile.println(copyQueue[i]);
        }
        toFile.close();
        return true;
      }catch(FileNotFoundException e){
        System.err.println("Please enter valid input file name!");
        Scanner scanner = new Scanner(System.in);
        outFile = scanner.nextLine();
      }
    }
    return true;
  }

  /**
   * Displays any flashcards that are currently due to the user, and
   * asks them to report whether they got each card correct. If the
   * card was correct, it is added back to the deck of cards with a new
   * due date that is one day later than the current date and time; if
   * the card was incorrect, it is added back to the card with a new due
   * date that is one minute later than that the current date and time.
   */
  public void displayFlashcards(){
    //Displays flashcards that have dueDate prior to now.
    while (LocalDateTime.now().compareTo(q.peek().getDueDate()) >= 0){
      System.out.println("Card:");
      System.out.println(q.peek().getFrontText());
      boolean correctness = true;
      Scanner scanner = new Scanner(System.in);
      System.out.print("[Press return for back of card]");
      scanner.nextLine();
      System.out.println(q.peek().getBackText());
      System.out.println("Press 1 if you got the card correct and 2 if you got the card incorrect.");
      String userInput = scanner.nextLine();
      //Set next dueDate according to user's feedback.
      if(userInput.equals("1")){
        Flashcard currentCard = q.poll();
        currentCard.setDueDate(LocalDateTime.now().minusDays(-1));
        q.add(currentCard);
      }else if(userInput.equals("2")){
        Flashcard currentCard = q.poll();
        currentCard.setDueDate(LocalDateTime.now().minusMinutes(-1));
        q.add(currentCard);
      }else {
        System.out.println("Please enter valid number.");
      }
    }
    System.out.println("No cards are waiting to be studied!");
  }

  /** The main method is where the user could interactively use the program to do quiz upon the flashcards they input,
  * save those cards, and exit after finishing the quiz.
  */
  public static void main (String [] args){
    FlashcardDisplayer displayer = new FlashcardDisplayer(args[0]);
    System.out.println("Time to practice flashcards! The computer will display your flashcards, you generate the response in your head, and then see if you got it right. The computer will show you cards that you miss more often than those you know!");
    System.out.println("Enter a command:");
    Scanner scanner = new Scanner(System.in);
    String userInput = scanner.nextLine();
    //Reprompt the user to input the correct command if they misspelled the command.
    while(!userInput.equals("exit") && !userInput.equals("quiz") && !userInput.equals("save")){
      System.out.println("Please enter valid command. What would you like to do? Please choose: exit, quiz, or save.");
      userInput = scanner.nextLine();
    }
    while(!userInput.equals("exit")){

      //Call displayFlashcards to start the quiz for the user and ask for their command after the quiz is finished.
      if (userInput.equals("quiz")){
        displayer.displayFlashcards();
        System.out.println("Enter a command:");
        userInput = scanner.nextLine();
        while(!userInput.equals("exit") && !userInput.equals("quiz") && !userInput.equals("save")){
          System.out.println("Please enter valid command. What would you like to do? Please choose: exit, quiz, or save.");
          userInput = scanner.nextLine();
        }
      }
      //Call saveFlashcards when demanded by the user. Checks whether the file name the user provide is valid or not (whether ends with .txt or not).
      else if (userInput.equals("save")){
        System.out.println("Type a filename where you'd like to save the flashcards:");
        String fileName = scanner.nextLine();
        while(fileName.substring(fileName.length() - 4, fileName.length()).compareTo(".txt") != 0){
          System.out.println("Please enter valid text file name ending with \".txt\"");
          fileName = scanner.nextLine();
        }
        displayer.saveFlashcards(fileName);
        System.out.println("Enter a command:");
        userInput = scanner.nextLine();
        while(!userInput.equals("exit") && !userInput.equals("quiz") && !userInput.equals("save")){
          System.out.println("Please enter valid command. What would you like to do? Please choose: exit, quiz, or save.");
          userInput = scanner.nextLine();
        }
      }
    }
    System.out.println("Goodbye!");
  }
}
