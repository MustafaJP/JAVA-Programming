import java.io.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class SecondCSV {
    public CSVRecord hottestHourInFiles(CSVParser parser){
        CSVRecord largestSoFar = null;
        for (CSVRecord currentRow : parser){
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        return largestSoFar;
    }

    public CSVRecord hottestInManyDays(){
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource(); 
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource();
            CSVRecord currentRow = hottestHourInFiles(fr.getCSVParser());
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        return largestSoFar;
    }

    public CSVRecord getLargestOfTwo(CSVRecord currentRow, CSVRecord largestSoFar){
        if (largestSoFar == null) {
            largestSoFar = currentRow;   
        }else{
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            if (currentTemp > largestTemp){
                largestSoFar = currentRow;
            }
        }
        return largestSoFar;
    }

    public void testHottestInManyDays(){
        CSVRecord largest = hottestInManyDays();
        System.out.println("Hottest temperature is " + largest + " at " + largest.get("DateUTC"));
    }

    public void testHottestHourInFile(){
        FileResource fr = new FileResource();
        CSVRecord largest = hottestHourInFiles(fr.getCSVParser());
        System.out.println("Hottest temperature was" + largest + " at " + largest.get("TimeEST"));
    }
}