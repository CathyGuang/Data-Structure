import java.util.NoSuchElementException;


/** 
 * An implementation for the Deque ADT using a circular array. It includes the methods 
 * 
 * @param <T> The type of data that the deque stores.
 * 
 */
public class CircularArrayDequeImplementation<T> implements Deque<T> {
    T[] myArray;
    int front;
    int back;
    int numOfItem;
    
    /**
    * Constructor. Initialize instance variable. 
    */
    public CircularArrayDequeImplementation(){
        @SuppressWarnings("unchecked")
        T[] tmp = (T[]) new Object[5];
        myArray = tmp;
        this.front = 0;
        this.back = 0;
        this.numOfItem = 0;
    }
    
    
    /** Adds an item to the front of this deque
     * @param item The item to add.
     */
    public void addFront(T item){
        //If the array is empty, add to the first position
        if (this.isEmpty()){
            myArray[0] = item;
            back = 0;
            front = 0;
        }else{
            //Resize the array if the array has no empty space left
            if (numOfItem > (myArray.length-1)){
                int previousSize = myArray.length;
                @SuppressWarnings("unchecked")
                T[] tmp = (T[]) new Object[previousSize*2];
                T[] newArray = tmp;
                //copy each element from the previous array to the new array
                for (int i=0; i<previousSize; i++){
                    newArray[i] = myArray[(front + i)%previousSize];
                }
                //set front and back to the correct position in the new array
                this.myArray = newArray;
                front = 0;
                back = numOfItem-1;
            }
            //move the front index, and then add item to the front
            if (front != 0){
                front = front -1;
            }else {
                front = (myArray.length-1);
            }
            myArray[front] = item;
        }
        numOfItem++;
    }
        
    
    /** Removes the item from the front of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeFront(){
        //Throw an error if user tries to remove from an empty array
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }else{
            //change the front and set the original front item to null
            T removedValue = myArray[front];
            myArray[front] = null;
            numOfItem--;
            front = (front + 1)% myArray.length;
            return removedValue;
        }
    }
    
    /** Adds an item to the back of this deque
     * @param item The item to add.
     */
    public void addBack(T item){
        //if the array is empty, add to the first position
        if (this.isEmpty()){
            myArray[0] = item;
            back = 0;
            front = 0;
        }else{
        //Resize the array if the array has no empty space left
        if (numOfItem > (myArray.length-1)){
            int previousSize = myArray.length;
            @SuppressWarnings("unchecked")
            T[] tmp = (T[]) new Object[previousSize*2];
            T[] newArray = tmp;
            //copy each element from the previous array to the new array
            for (int i=0; i<previousSize; i++){
                newArray[i] = myArray[(front + i)%previousSize];
            }
            //set front and back to the correct position in the new array
            this.myArray = newArray;
            front = 0;
            back = numOfItem-1;
        }
        //change the back index
        back = (back + 1)% myArray.length;
        myArray[back] = item;
        }
        numOfItem++;
    }
    
    /** Removes the item from the back of this deque, and returns it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T removeBack(){
        //if the user tries to remove from an empty list, throw an error
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }else {
            //change the back and set the original back item to null
            T item = myArray[back];
            numOfItem--;
            myArray[back] = null;
            if (back != 0){
                back = back-1;
            }else {
                back = (myArray.length-1);
            }
            return item;
        }
    }
    
    /** Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekFront(){
        //if the user tries to peek from an empty list, throw an error
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        return myArray[front];
    }
    
    /** Returns the item at the front of the deque, without removing it.
     * Throws a NoSuchElementException if the deque is empty
     * @return the item at the top of the deque
     */
    public T peekBack(){
        //if the user tries to peek from an empty list, throw an error
        if (this.isEmpty()){
            throw new NoSuchElementException();
        }
        return myArray[back];
    }
    
    /** Returns true if the deque is empty. */
    public boolean isEmpty(){
        return (this.numOfItem == 0);
    }
    
    /** Removes all items from the deque. */
    public void clear(){
        //create a new empty array 
    	@SuppressWarnings("unchecked")
        T[] tmp = (T[]) new Object[5];
        myArray = tmp;
        numOfItem = 0;
        front = 0;
        back = 0;
    }
}
