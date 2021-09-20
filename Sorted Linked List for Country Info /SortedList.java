import java.util.Iterator;
import java.util.Comparator;

/**
 * SortedList interface adapted from Carrano and Henry's interface in Data
 * Structures and Abstractions with Java. A sorted list ADT is like a list,
 * but maintains its entries in sorted order (rather than in the order they
 * were inserted or the order that the user specified).
 * @author Anna Rafferty, 
 *
 */
public interface SortedList<T> extends Iterable<T> {

    /**
     * Adds item to the list in sorted order.
     */
    public void add(T item);

    /**
     * Remove targetItem from the list, shifting everything after it up
     * one position. targetItem is considered to be in the list if
     * an item that is equal to it (using .equals) is in the list.
     * (This convention for something being in the list should be
     * followed throughout.)
     * @return true if the item was in the list, false otherwise
     */
    public boolean remove(T targetItem);
    
    /**
     * Remove the item at index position from the list, shifting everything
     *  after it up one position.
     * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
     */
    public T remove(int position);

    /**
     * Returns the position of targetItem in the list.
     * @return the position of the item, or -1 if targetItem is not in the list
     */
    public int getPosition(T targetItem);

    /** 
     * Returns the item at a given index.
     * @return the item, or throw an IndexOutOfBoundsException if the index is out of bounds.
     */
    public T get(int position);
    

    /** Returns true if the list contains the target item. */
    public boolean contains(T targetItem);
    
    /** Re-sorts the list according to the given comparator.
     * All future insertions should add in the order specified
     * by this comparator.
     */
    public void resort(Comparator<T> comparator);

    /** Returns the length of the list: the number of items stored in it. */
    public int size();

    /** Returns true if the list has no items stored in it. */
    public boolean isEmpty();

    /** Returns an array version of the list.  Note that, for technical reasons,
     * the type of the items contained in the list can't be communicated
     * properly to the caller, so an array of Objects gets returned.
     * @return an array of length length(), with the same items in it as are
     *         stored in the list, in the same order.
     */
    public Object[] toArray();

    /** Returns an iterator that begins just before index 0 in this list. */
    public Iterator<T> iterator();
    
    /** Removes all items from the list. */
    public void clear();

}
