import java.lang.reflect.Array;

/**
Country.java

Reads country info from a correctly formatted String and stores relevant indicators in an Array.
Methods to get and change indicators and the country name are included.

Indicators are indexed as follows:
0 CountryName
1 CO2Emissions 
2 TotalGreenhouseGasEmissions
3 AccessToElectricity
4 RenewableEnergy
5 ProtectedAreas
6 PopulationGrowth
7 PopulationTotal
8 UrbanPopulationGrowth.

*/
public class Country{
    // Instance variable: infoList
    // String Array that holds indicator data.
    private String[] infoList;
    
    /**
     * Country constructor
     * Reads country info from a correctly formatted String and stores relevant indicators in an Array of String.
     */
    public Country(String[] infoList) {
    	this.infoList = infoList;
    }
    
    /**
     * returns the infoList instance variable in Country class
     */
    public String[] getInfoList(){
        return this.infoList;
        
    }
    
    /**
     * checks if this country is completely equivalent to another country
     */
    public boolean equals(Country otherCountry) {
    	boolean equals = true;
        for(int i = 0; i < this.infoList.length; i++){
            if (this.infoList[i].compareTo(otherCountry.infoList[i]) != 0){
                return false;
            }
        }
        return equals;
    }
    
    /**
    getCountryName()
    Returns the name of the country.
    */
    public String getCountryName(){
        return infoList[0];
    }
    
    /**
    changeCountryName()
    Changes the name of the country to a given new name (String).
    */
    public void changeCountryName(String newName){
        infoList[0] = newName;
    }
    
    /**
    getIndicator()
    Returns the indicator associated with a specific index 1 to 8.
    */
    public Double getIndicator(int indicator){
        return Double.parseDouble(infoList[indicator]);
    }
    
    /**
    changeIndicator()
    Changes the indicator associated with a specific index 1 to 8 to a given new value (double).
    */
    public void changeIndicator(int indicator, double newValue){
        infoList[indicator] = Double.toString(newValue);
    }
}