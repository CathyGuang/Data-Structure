import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.lang.Class;
import java.util.Comparator;

/**
 *CountryDisplayer.java
 *
 *Parses country indicator information from a correctly formatted file.
 *Sorts given countries based on a given indicator from the command line.
 *Displays sorted countries as text or a graph.
 *Changes the sorting criteria of the list
 *Allows the user to add counties to the list 
 *Enables user interactions
 */
public class CountryDisplayer{
    
    private static SortedList<Country> countryList;
    private String filePath; 
    private String indicator;
    private boolean isGreatestToLeast;
    private Comparator<Country> comparator;
    
    /**
    * Constructs a new CountryDisplayer containing the countries in the file at filePath.
    * If filePath is null, constructs an empty CountryDisplayer.
    *
    * @param filePath path to the file from which to load country data
    * @param indicator indicator to use for sorting the countries
    * @param isGreatestToLeast whether the countries should be sorted from greatest to least
    */
    public CountryDisplayer(String filePath, String indicator, boolean isGreatestToLeast){
        this.filePath = filePath;
        this.indicator = indicator;
        this.isGreatestToLeast = isGreatestToLeast;
        this.countryList = null;
        this.comparator = new CountryComparator(indicator, isGreatestToLeast);
    }
        
    /**
    * gets the sorting based indicator
    */
    public String getIndicator(){
        return this.indicator;
    }
    
    /**
    * gets the sorting based order
    */
    public boolean getSortOrder(){
        return this.isGreatestToLeast;
    }
    
    /**
    * returns the sorted countryList
    */
    public SortedList<Country> getCountryList(){
        return countryList;
    }
    
    /**
    * sets indicator to another one
    */
    public void setIndicator(String indicator){
        this.indicator = indicator;
    }
    
    /**
    * sets sorting order to the opposite
    */
    public void setSortingOrder(boolean sortOrder){
        this.isGreatestToLeast = sortOrder;
    }
    
    /**
    * sets countryList instance variable to another one
    */
    public void setCountryList(SortedList<Country> countryList){
        this.countryList = countryList;
    }
    /**
     * loadCountries()
     * Adds Country objects from a correctly formatted file to the countryList in sorted order.
     */
    public void loadCountries(String filePath){
        Comparator<Country> comparator = new CountryComparator(this.indicator, this.isGreatestToLeast);
        countryList = new SortedLinkedList(comparator);
        
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
            String[] splitLine = line.split(",");
            Country country = new Country(splitLine);
            countryList.add(country);
        }

    }
    
    /**
     *displayCountriesAsText()
     *Prints all Country objects in the list as Strings.
    */
    public void displayCountriesAsText(){
        for (int i = 0; i < countryList.size(); i ++){
            Country country = countryList.get(i);
            System.out.println(country.getCountryName() + "," + country.getIndicator(1) + "," + country.getIndicator(2) + "," + country.getIndicator(3) + "," + country.getIndicator(4) + "," + country.getIndicator(5) + "," + country.getIndicator(6) + "," + country.getIndicator(7) + "," + country.getIndicator(8));
        }
    }
    
    /**
    * Displays a graph with the top 10 countries (based on the sorting criteria)
    * and a second series showing the additional indicator.
    * @param secondaryIndicator indicator to show as the second series in the graph
    */
    public void displayGraph(String secondaryIndicator){
        // Names the bar chart depending on sortOrder.
        int indicatorIndex1 = getIndicatorIndex(this.indicator);
        int indicatorIndex2 = getIndicatorIndex(secondaryIndicator);

        // Creates new BarChart with given indicator in title and specified axis names.
        BarChart graph = new BarChart(this.indicator, "Country", "Value");
        
        // Adds first 10 Country object indicator values to the bar chart.
        for (int i=0; i<=9; i++){
            Country country = countryList.get(i);
            double number = country.getIndicator(indicatorIndex1);
            String name = country.getCountryName();
            graph.addValue(name, number, this.indicator);
            double number2 = country.getIndicator(indicatorIndex2);
            graph.addValue(name, number2, secondaryIndicator);
        }
        // Displays chart.
        graph.displayChart();
    }
    
    /**
    * Changes the criteria for sorting 
    * @param indicator indicator to use for sorting the countries Valid values are: CO2Emissions, 
    *        TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, 
    *        PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth
    * @param isGreatestToLeast whether the countries should be sorted from greatest to least
    */
    public void changeSortingCriteria(String indicator, boolean isGreatestToLeast){
        this.indicator = indicator;
        this.isGreatestToLeast = isGreatestToLeast;
        this.comparator = new CountryComparator(indicator, isGreatestToLeast);
        this.countryList.resort(this.comparator);
    }
    
    /**
    * Adds the given country to the data.
    * @param country country to add
    */
    public void addCountry(Country addedCountry){
        this.countryList.add(addedCountry);
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
    * Main method
    * Gets following input from the user interactively 
    * Parses info from given file, sorts it by given indicator and sort order, and displays it as text or as a graph according to the demand of the user.
    * Changes sorting criteria based on user's need and add countries interactively.
    */
    
    public static void main(String[] args){
        CountryDisplayer displayer = new CountryDisplayer("CountryDataset.csv", "CO2Emissions", true);

        System.out.println("Default setting: indicator: CO2Emissions; sort order: greatest to least");
        //Check do we need to load countries from "CountryDataset.csv", if yes, load all the countries, otherwize, create an empty list inside the add method.
        if(args.length != 0 && args[0].equals("CountryDataset.csv")){
            displayer.loadCountries(args[0]);
        }else{
            Comparator<Country> comparator = new CountryComparator(displayer.getIndicator(), displayer.getSortOrder());
            countryList = new SortedLinkedList(comparator); 
            System.out.println("No country is loaded!");
        }
        
        System.out.println("What would you like to do? Please choose: set sorting criteria, add country, display text, display graph, or exit");
        
        Scanner scanner = new Scanner(System.in);
        
        String userInput = scanner.nextLine();
        while(!userInput.equals("set sorting criteria") && !userInput.equals("add country") && !userInput.equals("display text") && !userInput.equals("display graph") && !userInput.equals("exit")){
            System.out.println("Please enter valid command. What would you like to do? Please choose: set sorting criteria, add country, display text, display graph, or exit");
            userInput = scanner.nextLine();
        }
        //If not exiting the system, we prompt for other functions.
        while(!userInput.equals("exit")){
            //Set sorting criteria for the user if the user asked for that
            if(userInput.equals("set sorting criteria")){
                System.out.println("Please enter the sorting criteria that you want to sort, such as CO2Emissions, TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth: ");
                String sortingCriteria = scanner.nextLine();
                while(!sortingCriteria.equals("CO2Emissions") && !sortingCriteria.equals("TotalGreenhouseGasEmissions") && !sortingCriteria.equals("AccessToElectricity") && !sortingCriteria.equals("RenewableEnergy") && !sortingCriteria.equals("ProtectedAreas") && !sortingCriteria.equals("PopulationGrowth") && !sortingCriteria.equals("PopulationTotal") && !sortingCriteria.equals("UrbanPopulationGrowth")){
                    System.out.println("Invalid input! Please enter a secondary indicator that you want to show, such as CO2Emissions, TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth: ");
                    sortingCriteria = scanner.nextLine();
                }
                System.out.println("Please enter the sorting order that you want to sort: GreatestToLeast or LeastToGreatest: ");
                String sortingCriteria2 = scanner.nextLine();
                while (!sortingCriteria2.equals("GreatestToLeast") && !sortingCriteria2.equals("LeastToGreatest")){
                    
                    sortingCriteria2 = scanner.nextLine();
                    System.out.println("Please enter valid sorting criteria mentioned above, either GreatestToLeast or LeastToGreatest: ");
                    
                }
                if(sortingCriteria2.equals("GreatestToLeast")){
                    displayer.changeSortingCriteria(sortingCriteria, true);
                    System.out.println("Successfully changed!");
                }else {
                    displayer.changeSortingCriteria(sortingCriteria, false);
                    System.out.println("Successfully changed!");
                }
            }
            //Adds countries if the user asked and inputted in the right format
            if(userInput.equals("add country")){
                System.out.println("Please enter the country information so that each data is separated by a comma: ");
                String[] stringCountry = scanner.nextLine().split(",");
                while (stringCountry.length != 9){
                    System.out.println("Please enter the country information in an array of strings!!!: ");
                    stringCountry = scanner.nextLine().split(",");
                }
                
                Country country = new Country(stringCountry);
                displayer.addCountry(country);
                System.out.println("Successfully added!!");
                
            }
            //displays all text in sorting order according to the sorting criteria
            if(userInput.equals("display text")){
                displayer.displayCountriesAsText();
            }
            //displays countries's info in graph
            if(userInput.equals("display graph")){
                System.out.println("Please enter a secondary indicator that you want to show, such as CO2Emissions, TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth: ");
                String secondIndicator = scanner.nextLine();
                while(!secondIndicator.equals("CO2Emissions") && !secondIndicator.equals("TotalGreenhouseGasEmissions") && !secondIndicator.equals("AccessToElectricity") && !secondIndicator.equals("RenewableEnergy") && !secondIndicator.equals("ProtectedAreas") && !secondIndicator.equals("PopulationGrowth") && !secondIndicator.equals("PopulationTotal") && !secondIndicator.equals("UrbanPopulationGrowth")){
                    System.out.println("Invalid input! Please enter a secondary indicator that you want to show, such as CO2Emissions, TotalGreenhouseGasEmissions, AccessToElectricity, RenewableEnergy, ProtectedAreas, PopulationGrowth, PopulationTotal, or UrbanPopulationGrowth: ");
                    secondIndicator = scanner.nextLine();
                }
                displayer.displayGraph(secondIndicator);
            }
            
            System.out.println("What else would you like to do? Please choose: set sorting criteria, add country, display text, display graph, or exit");
            userInput = scanner.nextLine();
        }
        //exits the program if asked
        if (userInput.equals("exit")){
            System.exit(1);
        }
        
    }
}
