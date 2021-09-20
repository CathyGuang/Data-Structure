import java.lang.IndexOutOfBoundsException;
import java.util.Comparator;
import java.util.Iterator;

/**
 *SortedLinkedList.java
 *
 *This class implements SortedList interface with a linked structure.
 *
 */
public class SortedLinkedList implements SortedList<Country>{
    
    private Comparator<Country> countryComparator;
    private Node header;
    private Country tempCountry;
    
    
    public SortedLinkedList(Comparator countryComparator){
        this.countryComparator = countryComparator;
        header = null;
        tempCountry = new Country(null);
        
    }
    
    /**
     *This is an inner class that construct Node object in the SortedLinkedList structure.
     */
    private class Node{
        private Country data;
        private Node next;
        
        private Node(Country country){
            this(country, null);
        }
        
        private Node(Country data, Node nextNode){
            this.data = data;
            this.next = nextNode;
        }
    }
    
    /**
     * Adds item to the list in sorted order. The best adding time efficiency is Big O(1),
     * while the worst case requires a time efficiency Big O(n).
     */
    public void add(Country item) {
        
        //Checks if the list is empty. If it is, refer the header to the item added.
    	if (header == null){
            Node newNode = new Node(item);
            header = newNode;
        }else{
            Node currentNode = header.next;
            Node previousNode = header;
            //Compares the added item with the first entry in the list and add it to the correct position.
            if(countryComparator.compare(previousNode.data, item) > 0){
                Node addedNode = new Node(item, previousNode);
                header = addedNode;
            }else{
                //Compares every other nodes with the added item and add it to the correct position.
                while (currentNode != null){
                    if(countryComparator.compare(currentNode.data, item) < 0){
                        currentNode = currentNode.next;
                        previousNode = previousNode.next;
                    }else{
                        Node addedNode = new Node(item, currentNode);
                        previousNode.next = addedNode;
                        break;
                    }
                }
                //This adds the item to the end of the list which is also in the correct order.
                if(currentNode == null){
                    Node addedNode = new Node(item, currentNode);
                    previousNode.next = addedNode;
                }
                
        }
    }}
    

    /**
     * Remove targetItem from the list, shifting everything after it up
     * one position. targetItem is considered to be in the list if
     * an item that is equal to it (using .equals) is in the list.
     * (This convention for something being in the list should be
     * followed throughout.)
     * @return true if the item was in the list, false otherwise
     * The best case time efficiency is Big O(1),
     * while the worst case requires a time efficiency Big O(n).
     */
    public boolean remove(Country targetItem){
        boolean found = false;
        //Checks if the list is already empty and return false if empty.
        if(header == null){
            return found;
        }else if(header.data.equals(targetItem)){
            header = header.next;
            return true;
        }
        //Traverses the whole list, remove the targetItem if found, and return its corresponding removing result.
        Node currentNode = header;
        while(currentNode.next != null){
            if(currentNode.next.data.equals(targetItem)){
                currentNode.next = currentNode.next.next;
                found = true;
                return found;
            }
            currentNode = currentNode.next;
        }
        return found;
    }
    
    /**
     * Remove the item at index position from the list, shifting everything
     *  after it up one position.
     * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
     * The best case time efficiency is Big O(1),
     * while the worst case requires a time efficiency Big O(n).
     */
    public Country remove(int position){
        //Checks if the list is already empty and throw IndexOutOfBoundsException if empty.
        if (header == null){
            throw new IndexOutOfBoundsException();
        }
        
        //Checks if the position corresponds to the first node.
        if (position == 0){
            Country tempCountry = header.data;
            header = header.next;
            return tempCountry;
        }
        
        //Traverse through the whole list. Remove the item if position is in bounds.
        Node previousNode = header;
        int nodePosition = 1;
        while(previousNode.next != null){
            if (nodePosition != position){
                nodePosition++;
                previousNode = previousNode.next;
            }else{
                Country tempCountry = previousNode.next.data;
                previousNode.next = previousNode.next.next;
                return tempCountry;
            }
        }
        //If reaching the end of the list and still hasn't found the right position, throw IndexOutOfBoundsException.
        if(previousNode.next == null){
            throw new IndexOutOfBoundsException();
        }
        return null;
    }
    
    /**
     * Returns the position of targetItem in the list.
     * @return the position of the item, or -1 if targetItem is not in the list
     * The best case time efficiency is Big O(1),
     * while the worst case requires a time efficiency Big O(n).
     */
    public int getPosition(Country targetItem){
        //Returns -1 if the list is empty because we can never find our targetItem.
        if (header == null){
            return -1;
        }
        //Traverses through the list until we found our targerItem and return its position.
        Node currentNode = header;
        int nodePosition = 0;
        while (currentNode.next != null && !currentNode.data.equals(targetItem)){
            currentNode = currentNode.next;
            nodePosition++;
            
        }
        //If reaching the end of the list and still hasn't found our targetItem, return -1.
        if (!currentNode.data.equals(targetItem)){
            return -1;
        }
        return nodePosition;
    }

    /** 
     * Returns the item at a given index.
     * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
     * The best case time efficiency is Big O(1),
     * while the worst case requires a time efficiency Big O(n).
     */
    public Country get(int position){
        //Checks if the list is empty. 
        if (header == null){
            throw new IndexOutOfBoundsException();
        }
        //Traverses through the list until we found the right position and return its Country object.
        Node currentNode = header;
        int nodePosition = 0;
        while (nodePosition < position && currentNode.next != null){
            currentNode = currentNode.next;
            nodePosition++;
        }
        //If reaching the end of the list and still hasn't found our targetItem, throw IndexOutOfBoundsException.
        if (nodePosition != position){
            throw new IndexOutOfBoundsException();
        }
        return currentNode.data;
    }
    

    /** Returns true if the list contains the target item.
     * The best case time efficiency is Big O(1),
     * while the worst case requires a time efficiency Big O(n).
     */
    public boolean contains(Country targetItem){
        //Checks if the list is empty. 
        if (header == null){
            return false;
        }
        //Traverses through the list to see if targetItem is included and return true if yes.
        Node currentNode = header;
        while (currentNode.data != targetItem && currentNode.next != null){
            currentNode = currentNode.next;
        }
        //If the last item is our targetItem, return true.
        if(currentNode.data.equals(targetItem)){
            return true;
        }
        return false;
    }
    
    /** Re-sorts the list according to the given comparator.
     * All future insertions should add in the order specified
     * by this comparator.
     * The best case time efficiency is Big O(1) (if the list is empty),
     * while the worst case requires a time efficiency Big O(n^2).
     */
    public void resort(Comparator<Country> comparator){
        //Checks whether the list is empty to decide whether to resort or not.
        if(this.isEmpty() || this.size() == 1){
            System.err.println("No need to sort!");
        }else{
            //Uses insertion sort algorithm to sort the entire list based on comparator inputted
            Node unsortedNode = header.next;
            header.next = null;
            
            //Divides the original list into two parts and insertion sort the second part based on the first sorted part
            while(unsortedNode != null){
                Node currentNode = this.header;
                Node previousNode = null;
                //traverse the first part of the list
                while(currentNode != null && comparator.compare(currentNode.data, unsortedNode.data) <= 0){
                    previousNode = currentNode;
                    currentNode = currentNode.next;
                }
                //insert the unsortedNode (first node in the unsorted part of the list) 
                Node tempUnsortedNode = unsortedNode;
                unsortedNode = unsortedNode.next;
                if(previousNode != null){
                    tempUnsortedNode.next = currentNode;
                    previousNode.next = tempUnsortedNode;
                }
                else {
                    tempUnsortedNode.next = header;
                    header = tempUnsortedNode;
                }
            }
        }
        
    }

    /** Returns the length of the list: the number of items stored in it. 
     * The time efficiency is Big O(n). 
     */
    public int size(){
        int nodePosition = 0;
        Node currentNode = header;
        while(currentNode != null){
            currentNode = currentNode.next;
            nodePosition ++;
        }
        return nodePosition;
    }

    /** Returns true if the list has no items stored in it. 
     * The time efficiency is Big O(1).
     */
    public boolean isEmpty(){
        return header == null;
    }

    /** Returns an array version of the list.  Note that, for technical reasons,
     * the type of the items contained in the list can't be communicated
     * properly to the caller, so an array of Objects gets returned.
     * @return an array of length length(), with the same items in it as are
     *         stored in the list, in the same order.
     * The time efficiency is Big O(n).
     */
    public Object[] toArray(){
        
        Node currentNode = header;
        int numOfNodes = this.size();
        Object[] array = new Object[numOfNodes];
        
        for (int i = 0; i < numOfNodes; i++){
            array[i] = currentNode.data;
            currentNode = currentNode.next;
        }
        return array;
    }

    /** Returns an iterator that begins just before index 0 in this list. 
     * Not implemented. But time efficiency would be Big O(n).
     */
    public Iterator<Country> iterator(){
        throw new UnsupportedOperationException("iterator() not implemented!");
    }
    
    /** Removes all items from the list. 
     * The time efficiency is Big O(1).
     */
    public void clear(){
        header = null;
    }
}
