import java.util.Comparator;
import java.util.Arrays;
import java.lang.IndexOutOfBoundsException;
public class TestSortedLinkedList {
    
    /**
     * Tests situations such as adding to an empty list, to the front inside a stuffed list, to the middle, to the end.
     */
    public static void testAdd(Country testCountry1, Country testCountry2, Country testCountry3, Country testCountry4) {
        Comparator<Country> comparator = new CountryComparator("AccessToElectricity", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        testList.add(testCountry3);
        System.out.println("This tests adding towards an empty list. The result should be C: " + testList.get(0).getCountryName());
        testList.add(testCountry3);
        System.out.println("This tests adding the same country node into the sortedLinkedList. The result should be CC: " + testList.get(0).getCountryName() + testList.get(1).getCountryName());
        testList.add(testCountry1);
        System.out.println("This tests adding to the front of a stuffed list. The result should be ACC: " + testList.get(0).getCountryName() + testList.get(1).getCountryName() + testList.get(2).getCountryName());
        testList.add(testCountry2);
        System.out.println("This tests adding to the middle of a stuffed list. The result should be ABCC: " + testList.get(0).getCountryName() + testList.get(1).getCountryName() + testList.get(2).getCountryName() + testList.get(3).getCountryName());
        testList.add(testCountry4);
        System.out.println("This tests adding an NaN to the end of a stuffed list. The result should be ABCCD " + testList.get(0).getCountryName() + testList.get(1).getCountryName() + testList.get(2).getCountryName() + testList.get(3).getCountryName() + testList.get(4).getCountryName());
        System.out.println("--------------");
    }

    /**
     * Tests situations such as removing a country from an empty list, from the front inside a stuffed list, from the middle, from the end.
     */
    public static void testRemove(Country testCountry1, Country testCountry2, Country testCountry3, Country testCountry4){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        System.out.println("This removes a targetItem from an empty list. The output is supposed to be false, and the result of this is: " + testList.remove(testCountry1));
        testList.add(testCountry1);
        testList.add(testCountry2);
        testList.add(testCountry3);
        System.out.println("This removes a targetItem from a list that has the current targetItem at the first position. The output is supposed to be true, and the result of this is: " + testList.remove(testCountry1) + " The list now should be BC: " + testList.get(0).getCountryName() + testList.get(1).getCountryName());
        System.out.println("This removes a targetItem from the end of the list. The output is supposed to be true, and the result of this is: "+ testList.remove(testCountry3) + " The list now should be B: " + testList.get(0).getCountryName());
        testList.add(testCountry1);
        testList.add(testCountry2);
        testList.add(testCountry3);
        System.out.println("This removes a targetItem from the middle of the list. The output is supposed to be true, and the result of this is: " + testList.remove(testCountry2) + " The list now should be ABC: " + testList.get(0).getCountryName() + testList.get(1).getCountryName() + testList.get(2).getCountryName());
        System.out.println("This removes a targetItem from a non-empty list that doesn't have the current item. The output is supposed to be false, and the result of this is: "+ testList.remove(testCountry4));
        System.out.println("--------------");
    }
    
    /**
     * Tests situations such as removing a country from an empty list, from the front inside a stuffed list, from the middle, from the end.
     */
    public static void testRemove(Country item1, Country item2, Country item3, Country item4, int position1){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        try{
            testList.remove(1);
        }catch(IndexOutOfBoundsException e){
            System.err.println("This removes the country at a given position from an empty list. The output is supposed to be IndexOutOfBoundExceptions, and the result of this is: IndexOutOfBoundsException");
        }
        
        testList.add(item1);
        testList.add(item2);
        testList.add(item3);
        testList.add(item4);
        System.out.println("This removes a country at a position 0 from the list. The output is supposed to be the A, and the result of this is: " + testList.remove(position1 - 1).getCountryName());
        System.out.println("This removes a country at position 1 from a list. The output is supposed to be the C, and the result of this is: " + testList.remove(position1).getCountryName());
        System.out.println("This removes a country at the end of the list. The output is supposed to be the D, and the result of this is: " + testList.remove(position1).getCountryName());
        System.out.println("The remaining list should be B, and the result of this is: " + testList.get(0).getCountryName());
        System.out.println("--------------");
    }


    /**
     * Tests situations such as getting a country's position from an empty list, from the front inside a stuffed list, from the middle, from the end.
     */
    public static void testGetPosition(Country targetItem1, Country targetItem2){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        System.out.println("This gets the position of a country from an empty list. The output is supposed to be -1, and the result of this is: " + testList.getPosition(targetItem1));
        testList.add(targetItem1);
        testList.add(targetItem2);
        System.out.println("This gets the position of targetItem2. The output is supposed to be targetCountry2's position which is 1, and the result of this is: " + testList.getPosition(targetItem2));
        System.out.println("This gets the position of targetItem1. The output is supposed to be targetCountry1's position which is 0, and the result of this is: " + testList.getPosition(targetItem1));
        System.out.println("--------------");
    }

    /** 
     * Tests situations such as getting a country at a given position from an empty list, from the front inside a stuffed list, from the middle, from the end.
     */
    public static void testGet(int position, Country targetItem1, Country targetItem2){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        try{
            testList.get(position);
        }catch(IndexOutOfBoundsException e){
            System.err.println("This gets the country at a given position from an empty list. The output is supposed to be IndexOutOfBoundExceptions, and the result of this is: IndexOutOfBoundsException");
        }
        testList.add(targetItem1);
        testList.add(targetItem2);
        System.out.println("This gets the country at position 0. The output is supposed to be A, and the result of this is: " + testList.get(position).getCountryName());
        System.out.println("This gets the country at position 1. The output is supposed to be B, and the result of this is: " + testList.get(position + 1).getCountryName());
        try{
            testList.get(position + 2).getCountryName();
        }catch(IndexOutOfBoundsException e){
            System.err.println("This gets a non-existing country at an invald position. The output is supposed to throw an IndexOutOfBoundExceptions: IndexOutOfBoundsException");
        }
        System.out.println("--------------");
    }
    

    /** 
     * Tests situations whether if the list contains a country. The lists vary from an empty list to stuffed lists.
     */
    public static void testContains(Country item1, Country item2, Country item3, Country item4){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        System.out.println("This tries to check if a country is in an empty list. The output is supposed to be false, and the result of this is: " + testList.contains(item1));
        testList.add(item1);
        testList.add(item2);
        testList.add(item3);
        testList.add(item4);
        System.out.println("This tries to check if a country is in an list that has this country. The output is supposed to be true, and the result of this is: " + testList.contains(item2));
        System.out.println("This tries to check if a country is in an list that has this country at the end. The output is supposed to be true, and the result of this is: " + testList.contains(item4));
        System.out.println("--------------");
    }
    
    /** 
     * Tests resort situation for an empty list, and a stuffed list.
     */
    public static void testResort(Comparator<Country> comparator1, Country targetItem1, Country targetItem2, Country targetItem3, Country targetItem4){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        testList.resort(comparator);
        System.out.println("This should resort an empty list. The current testList should print No need to sort!");
        
        testList.add(targetItem1);
        testList.add(targetItem2);
        testList.add(targetItem3);
        testList.add(targetItem4);
        System.out.println("This should resort the list based on a new comparator. The current testList should be ABCD: " + testList.get(0).getCountryName() + testList.get(1).getCountryName() + testList.get(2).getCountryName() + testList.get(3).getCountryName());
        testList.resort(comparator1);
        System.out.println("The resorted list should be ABDC: " + testList.get(0).getCountryName() + testList.get(1).getCountryName() + testList.get(2).getCountryName() + testList.get(3).getCountryName());
        System.out.println("--------------");
    }

    /** 
     * Tests whether the size method return the right size of the list.
     */
    public static void testSize(Country targetItem1, Country targetItem2){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        System.out.println("This returns the size of an empty list. The output is supposed to be 0, and the result of this is: " + testList.size());
        testList.add(targetItem1);
        testList.add(targetItem1);
        testList.add(targetItem2);
        System.out.println("This returns the size of a list. The output is supposed to be 3, and the result of this is: " + testList.size());
        System.out.println("--------------");
        
    }
    /** 
     * Tests whether the size is empty.
     */
    public static void testIsEmpty(Country targetItem1, Country targetItem2){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        System.out.println("Newly created sortedLinkedList should be empty. Result of isEmpty(): " + testList.isEmpty());
        testList.add(targetItem1);
        testList.add(targetItem2);
        System.out.println("Newly created sortedLinkedList should have two items. Result of isEmpty(): " + testList.isEmpty());
        System.out.println("--------------");
    }

    /** 
     * Test whether toArray method creates an array that represents the whole list.
     */
    public static void testToArray(Country targetItem1, Country targetItem2){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        Object[] tempCountryArray1 = testList.toArray();
        int size = tempCountryArray1.length;
        System.out.println("Newly created sortedLinkedList should be empty. Length of the array: " + size);
        
        testList.add(targetItem1);
        testList.add(targetItem2);
        Object[] tempCountryArray = testList.toArray();
        String countryName1 = ((Country)tempCountryArray[0]).getCountryName();
        String countryName2 = ((Country)tempCountryArray[1]).getCountryName();
        System.out.println("Newly created sortedLinkedList should have two items AD. Result of toArray(): " + countryName1 + countryName2);
        System.out.println("--------------");
        
    }

    //We didn't test the iterator here because it's not implemented.
    
    /** 
     * This tests whether we can clear the list.
     */
    public static void testClear(Country targetItem1, Country targetItem2){
        Comparator<Country> comparator = new CountryComparator("CO2Emissions", false);
        SortedList<Country> testList = new SortedLinkedList(comparator);
        testList.clear();
        System.out.println("This shows the result after clearing an empty list, and the result of isEmpty() is: " + testList.isEmpty());
        testList.add(targetItem1);
        testList.add(targetItem2);
        System.out.print("Newly created sortedLinkedList should have" + testList.size() + " items,");
        testList.clear();
        System.out.println(" and the result of isEmpty() after clearing the list is: " + testList.isEmpty());
        
        
    }
    
    public static void main(String[] args) {
        
        
        String[] aArray = new String[9];
        aArray[0] = "A"; 
        aArray[1] = "1";
        aArray[2] = "7";
        aArray[3] = "37";
        aArray[4] = "50.82438083";
        aArray[5] = "6.970951131";
        aArray[6] = "3.43750798";
        aArray[7] = "27916980.71";
        aArray[8] = "4.568332945";
        Country a = new Country(aArray);
        
        String[] bArray = new String[9];
        bArray[0] = "B"; 
        bArray[1] = "3";
        bArray[2] = "3";
        bArray[3] = "38";
        bArray[4] = "50";
        bArray[5] = "6";
        bArray[6] = "3";
        bArray[7] = "2";
        bArray[8] = "4";
        Country b = new Country(bArray);

        String[] cArray = new String[9];
        cArray[0] = "C"; 
        cArray[1] = "4";
        cArray[2] = "NaN";
        cArray[3] = "39";
        cArray[4] = "50";
        cArray[5] = "6";
        cArray[6] = "3";
        cArray[7] = "27916980";
        cArray[8] = "4";
        Country c = new Country(cArray);
        
        String[] dArray = new String[9];
        dArray[0] = "D"; 
        dArray[1] = "5";
        dArray[2] = "1";
        dArray[3] = "NaN";
        dArray[4] = "50";
        dArray[5] = "6";
        dArray[6] = "3";
        dArray[7] = "27916980";
        dArray[8] = "4";
        Country d = new Country(dArray);
        
        
        Comparator<Country> testComparator = new CountryComparator("TotalGreenhouseGasEmissions", true);
        testAdd(a, b, c, d);
        testRemove(a, b, c, d);
        testRemove(a, b, c, d, 1);
        testGetPosition(a, b);
        testGet(0, a, b);
        testContains(a, b, c, d);
        testResort(testComparator, a, b, c, d);
        testSize(a, d);
        testIsEmpty(a, d);
        testToArray(a, d);
        testClear(a, d);        
    }
}
