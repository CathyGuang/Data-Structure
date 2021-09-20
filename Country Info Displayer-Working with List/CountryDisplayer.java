import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
CountryDisplayer.java

Parses country indicator information from a correctly formatted file.
Sorts given countries based on a given indicator from the command line.
Displays sorted countries as text or a graph.

*/
public class CountryDisplayer{
    // Instance variable: countryList
    // An ArrayList that stores all Country objects that are inputted from the file.
    private static ArrayList<Country> countryList;
        
    /**
    loadCountries()
    Adds Country objects from a correctly formatted file to the countryList.
    */
    public static void loadCountries(String filePath){
        File inputFile = new File(filePath);
        // Scans the file if the file path is correctly formatted.
        // If not, returns an error message.
        Scanner scanner = null;
        try {
            scanner = new Scanner(inputFile);
        } catch (FileNotFoundException e){
            System.err.println(e);
            System.exit(1);
        }
        
        // Skips the first line of indicator names
        scanner.nextLine();
        
        // Creates a new Country object for each line and adds it to the countryList.
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            Country country = new Country(line);
            countryList.add(country);
        }

    }
    
    /**
    sortCountryList()
    Sorts the ArrayList of Country objects based on a given indicator and sorting order.
    */
    
    public static void sortCountryList(int indicator, boolean sortingOrder){
        int size = countryList.size();
        
        // Sorts the list from greatest to least.
        for(int i = 0; i < size; i++) {
            // Assumes the ith entry is the greatest.
            int greatest = i;
            for(int j = i+1; j < size; j++) {
                // If any later entries are greater, update which entry is greatest.
                if (countryList.get(j).getIndicator(indicator) > countryList.get(greatest).getIndicator(indicator)){
                    greatest = j;
                 }
            }
            // Switches the ith and greatest entries.
            Country temp = countryList.get(i);
            countryList.set(i, countryList.get(greatest));
            countryList.set(greatest, temp);
        }
        // If the sorting order is leastToGreatest, this reverses the list.
        if (!sortingOrder){
            for (int i=0; i< size-1; i++){
                countryList.add(i, countryList.remove(size-1));
            }
        }
        // This sends all NaN values to the end of the list.
        for (int i = 0; i < size; i++){
            Double countryDouble = countryList.get(i).getIndicator(indicator);
            if (countryDouble.isNaN()){
                Country removed = countryList.remove(i);
                countryList.add(removed);
            }
        }
    }
    /**
    displayTextCountries()
    Prints all Country objects in the list as Strings.
    */
    public static void displayTextCountries(){
        for (Country country : countryList){
            System.out.println(country.getCountryName() + "," + country.getIndicator(1) + "," + country.getIndicator(2) + "," + country.getIndicator(3) + "," + country.getIndicator(4) + "," + country.getIndicator(5) + "," + country.getIndicator(6) + "," + country.getIndicator(7) + "," + country.getIndicator(8));
        }
    }
    /**
    Displays two given indicators of the top or bottom 10 countries on a BarChart, arranged based on given sort order.
    */
    public static void displayCountryGraph(String indicator1, String indicator2, int indicatorIndex1, int indicatorIndex2, boolean sortOrder){
        // Names the bar chart depending on sortOrder.
        String title1;
        if (sortOrder){
            title1 = "Top 10 ";
        }
        else{
            title1 = "Bottom 10 ";
        }
        
        // Creates new BarChart with given indicator in title and specified axis names.
        BarChart graph = new BarChart("" + title1 + indicator1, "Country", "Value");
        
        // Adds first 10 Country object indicator values to the bar chart.
        for (int i=0; i<=9; i++){
            Country country = countryList.get(i);
            double number = country.getIndicator(indicatorIndex1);
            String name = country.getCountryName();
            graph.addValue(name, number, indicator1);
            double number2 = country.getIndicator(indicatorIndex2);
            graph.addValue(name, number2, indicator2);
        }
        // Displays chart.
        graph.displayChart();
    }
    
    /**
    Gets the indicator index from a correctly formatted indicator name.
    */
    public static int getIndicatorIndex(String indicatorString){
        if (indicatorString.equals("CO2Emissions")) {
            return 1;
        }
        else if (indicatorString.equals("TotalGreenhouseGasEmissions")) {
            return 2;
        }
        else if (indicatorString.equals("AccessToElectricity")) {
            return 3;
        }
        else if (indicatorString.equals("RenewableEnergy")) {
            return 4;
        }
        else if (indicatorString.equals("ProtectedAreas")) {
            return 5;
        }
        else if (indicatorString.equals("PopulationGrowth")) {
            return 6;
        }
        else if (indicatorString.equals("PopulationTotal")) {
            return 7;
        }
        else if (indicatorString.equals("UrbanPopulationGrowth")) {
            return 8;
        }
        // Returns error if the indicator is formatted incorrectly.
        else {
            System.err.println("Indicator formatted incorrectly: use one of CO2Emissions, TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, PopulationGrowth, PopulationTotal, UrbanPopulationGrowth");
            System.exit(1);
            return 0;
        }
    }
    
    /**
    Main method
    Gets following input from command line: filePath, indicator1, sortOrder, and optionally indicator2.
    Parses info from given file, sorts it by given indicator and sort order, and displays it as text (3 arguments) or as a graph (4 arguments). 
    */
    
    public static void main(String[] args){
        // If there are too many or too few arguments, return a usage statement.
        if (args.length < 3 || args.length > 4){
            System.err.println("Required arguments: filePath indicator1 sortOrder (indicator2)");
            System.exit(1);
        }
        
        // Initializes countryList instance variable.
        countryList = new ArrayList<Country>();
        
        // Loads country info from file.
        String filePath = args[0];
        loadCountries(filePath);
        
        // Finds index of first indicator.
        String indicatorString = args[1];
        int indicator1 = getIndicatorIndex(indicatorString);
        
        // Rewrites sortOrder as a boolean sortType.
        // greatestToLeast = true, leastToGreatest = false.
        String sortOrder = args[2];
        boolean sortType = true;
        if (sortOrder.equals("greatestToLeast")){
            sortType = true;
        }
        else if (sortOrder.equals("leastToGreatest")){
            sortType = false;
        }
        // If sortOrder is formatted incorrectly, return a usage statement.
        else {
            System.err.println("Sort order formatted incorrectly: enter greatestToLeast or leastToGreatest");
            System.exit(1);
        }
        
        // Sorts the Country list with the first indicator and given sorting order.
        sortCountryList(indicator1, sortType);
        
        // If there are 4 arguments, display the two indicators on a bar chart.
        if (args.length == 4){
            int indicator2 = getIndicatorIndex(args[3]);
            displayCountryGraph(args[1], args[3], indicator1, indicator2, sortType);
        }
        // If there are 3 arguments, display indicator information as text.
        else if (args.length == 3){
            displayTextCountries();
        }

    }
}
