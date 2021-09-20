import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
* CountryComparator.java
* CountryComparator implements Comparator interface. It includes the method compare which is used to compare
* Country objects
*/
public class CountryComparator implements Comparator<Country>{

	private String indicator;
	private boolean isGreatestToLeast;
    
    /**
    * construct now CountryComparator and intialize the two instance variables base on input
    */
	public CountryComparator(String indicator, boolean isGreatestToLeast) {
		this.indicator = indicator;
		this.isGreatestToLeast = isGreatestToLeast;
	}
    
    /**
    * get the indicator string
    */
    public String getIndicator(){
        return this.indicator;
    }
    /**
    * get the sorting order
    */
    public boolean getSortOrder(){
        return this.isGreatestToLeast;
    }
    /**
    * set indicator
    */
    public void setIndicator(String indicator){
        this.indicator = indicator;
    }
    /**
    * set the sorting order
    */
    public void setSortOrder(boolean sortOrder){
        this.isGreatestToLeast = sortOrder;
    }
	
    /**
    * compare(Country country1, Country country2)
    * compare two countries. Return a negative number if country1 comes before country2 in sorted order
    * Return 0 if country1 and country2 are equal in the sorted order
    * Return a positive number if country1 comes after country2 in sorted order
    */
	public int compare(Country country1, Country country2) {
		
		int indicatorIndex = 0;
	
		if (this.indicator.equals("CO2Emissions")) {
            indicatorIndex = 1;
        }
        else if (this.indicator.equals("TotalGreenhouseGasEmissions")) {
        	indicatorIndex = 2;
        }
        else if (this.indicator.equals("AccessToElectricity")) {
        	indicatorIndex = 3;
        }
        else if (this.indicator.equals("RenewableEnergy")) {
        	indicatorIndex = 4;
        }
        else if (this.indicator.equals("ProtectedAreas")) {
        	indicatorIndex = 5;
        }
        else if (this.indicator.equals("PopulationGrowth")) {
        	indicatorIndex = 6;
        }
        else if (this.indicator.equals("PopulationTotal")) {
        	indicatorIndex = 7;
        }
        else if (this.indicator.equals("UrbanPopulationGrowth")) {
        	indicatorIndex = 8;
        }
        //make sure we compare NaN values correctly with normal numbers
        if (country1.getIndicator(indicatorIndex).isNaN() && !country2.getIndicator(indicatorIndex).isNaN()){
                return 1;
        } else if (country2.getIndicator(indicatorIndex).isNaN() && !country1.getIndicator(indicatorIndex).isNaN()){
                return -1;
        } else if (country1.getIndicator(indicatorIndex).isNaN() && country2.getIndicator(indicatorIndex).isNaN()){
                int relativeCountryNameValue = country1.getCountryName().compareTo(country2.getCountryName());
                return relativeCountryNameValue;
        }
        
        double relativeValue = country1.getIndicator(indicatorIndex) - country2.getIndicator(indicatorIndex);
        //make sure when two countries have the same values, we compare them base on their initial letter of country names
        if (relativeValue == 0){
                int relativeCoutryNameValue = country1.getCountryName().compareTo(country2.getCountryName());
                return relativeCoutryNameValue;
        }
        if (this.isGreatestToLeast){
            if(relativeValue > 0){
                return -1;
            } else{
                return 1;
            }
        
        } else{
            if(relativeValue < 0){
                return -1;
            } else{
                return 1;
            }
        }
    }
   
    /**
    * main method
    * creates three countries and test compare method.
    */
	public static void main(String[] args) {
		Comparator<Country> countryComparator = new CountryComparator("AccessToElectricity", false);
        String[] akhatenArray = new String[9];
        akhatenArray[0] = "akhaten"; 
        akhatenArray[1] = "1.291433838";
        akhatenArray[2] = "41657.16485";
        akhatenArray[3] = "38.17670504";
        akhatenArray[4] = "50.82438083";
        akhatenArray[5] = "6.970951131";
        akhatenArray[6] = "3.43750798";
        akhatenArray[7] = "27916980.71";
        akhatenArray[8] = "4.568332945";
        Country akhaten = new Country(akhatenArray);
        
        String[] trenzaloreArray = new String[9];
        trenzaloreArray[0] = "trenzalore"; 
        trenzaloreArray[1] = "1.291433838";
        trenzaloreArray[2] = "41657.16485";
        trenzaloreArray[3] = "37.17670504";
        trenzaloreArray[4] = "50.82438083";
        trenzaloreArray[5] = "6.970951131";
        trenzaloreArray[6] = "3.43750798";
        trenzaloreArray[7] = "27916980.71";
        trenzaloreArray[8] = "4.568332945";
        Country trenzalore = new Country(trenzaloreArray);

        String[] gallifreyArray = new String[9];
        gallifreyArray[0] = "gallifrey"; 
        gallifreyArray[1] = "1.291433838";
        gallifreyArray[2] = "41657.16485";
        gallifreyArray[3] = "38.17670504";
        gallifreyArray[4] = "50.82438083";
        gallifreyArray[5] = "6.970951131";
        gallifreyArray[6] = "3.43750798";
        gallifreyArray[7] = "27916980.71";
        gallifreyArray[8] = "4.568332945";
        Country gallifrey = new Country(gallifreyArray);
        
		//Value for akhatenVersusTrenzalore should be positive (trenzalore has less access)
		int akhatenVersusTrenzalore = countryComparator.compare(akhaten, trenzalore);
        System.out.println(akhatenVersusTrenzalore);
        
		//Value for akhatenVersusGallifrey should be negative
		//(they have the same access, and akhaten is alphabetically first)
		int akhatenVersusGallifrey = countryComparator.compare(akhaten, gallifrey);
        System.out.println(akhatenVersusGallifrey);
	}
}
