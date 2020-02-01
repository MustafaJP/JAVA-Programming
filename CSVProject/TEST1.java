
import java.io.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class TEST1{
    String fileName = null;

    public CSVRecord coldestHourInFiles(CSVParser parser){
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow : parser){
            smallestSoFar = getSmallestOfTemperature(currentRow, smallestSoFar);
        }
        return smallestSoFar;
    }

    public CSVRecord coldestInManyDays(){
        CSVRecord smallestSoFar = null;
        DirectoryResource dr = new DirectoryResource(); 
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord currentRow = coldestHourInFiles(fr.getCSVParser());
            smallestSoFar = getSmallestOfTemperature(currentRow, smallestSoFar);
        }
        return smallestSoFar;
    }

    public String fileWithColdestTemperature(){
        CSVRecord smallestSoFar = null;
        String filePath = "";
        DirectoryResource dr = new DirectoryResource(); 
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            for (CSVRecord currentRow : parser){
                if (smallestSoFar == null) {
                    smallestSoFar = currentRow;   
                }else{
                    double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                    if (currentTemp == -9999){
                    }else{
                        double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                        if (currentTemp < smallestTemp){
                            smallestSoFar = currentRow;
                            filePath = f.getPath();
                            fileName = f.getName();
                        }
                    }
                }
            }
        }
        return filePath;
    }

    public CSVRecord getSmallestOfTemperature(CSVRecord currentRow, CSVRecord smallestSoFar){
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;   
        }else{
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            if (currentTemp == -9999){
            }else{
                double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                if (currentTemp < smallestTemp){
                    smallestSoFar = currentRow;
                }
            }
        }
        return smallestSoFar;
    }

    public void printAllContents(CSVParser parser){
        for (CSVRecord currentRow : parser){
            System.out.println(currentRow.get("DateUTC") + " : " + currentRow.get("TemperatureF"));
        }
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow : parser){
            smallestSoFar = getSmallestOfHumidity(currentRow, smallestSoFar);
        }
        return smallestSoFar;
    }

    public CSVRecord lowestHumidityInManyFile(){
        CSVRecord smallestSoFar = null;
        DirectoryResource dr = new DirectoryResource(); 
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVParser parser1 = fr.getCSVParser();
            for (CSVRecord currentRow : parser1){
                smallestSoFar = getSmallestOfHumidity(currentRow, smallestSoFar);
            }
        }
        return smallestSoFar;
    }

    public CSVRecord getSmallestOfHumidity(CSVRecord currentRow, CSVRecord smallestSoFar){
        if (smallestSoFar == null) {
            smallestSoFar = currentRow;   
        }else{
            if (currentRow.get("Humidity").equals("N/A")){
            }else{
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double smallestHumidity = Double.parseDouble(smallestSoFar.get("Humidity"));
                if (currentHumidity <= smallestHumidity){
                    if (currentHumidity == smallestHumidity){
                    }else{
                        smallestSoFar = currentRow;
                    }
                }
            }
        }
        return smallestSoFar;
    }

    public double averageTemperatureInFile(CSVParser parser){
        double sum = 0;
        int count = 0;
        double flag = 0;
        for (CSVRecord currentRow : parser){
            double temp = Double.parseDouble(currentRow.get("TemperatureF"));
            sum = sum + temp;
            count = count + 1;
        }
        if (count == 0){
            flag = -1;
            return flag;
        }
        double avg = sum/count;
        return avg;
    }

    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sum = 0;
        int count = 0;
        double flag = 0;
        //double valuedouble = Double.valueOf(value);
        for (CSVRecord currentRow : parser){
            double humidity = Double.parseDouble(currentRow.get("Humidity"));
            if (humidity >= value) {
                double temp = Double.parseDouble(currentRow.get("TemperatureF"));
                sum = sum + temp;
                count = count + 1;
            }
        }
        if (count == 0){
            flag = -1;
            return flag;
        }
        double avg = sum/count;
        return avg;
    }

    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        double avg = averageTemperatureInFile(fr.getCSVParser());
        avg = ((int)(avg*10000))/10000.0;
        System.out.println("Average temperature in file is " + avg);
    }

    public void testAverageTemperatureWithHighHumidityInFile(){
        int value = 80;
        FileResource fr = new FileResource();
        double avg = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), value);
        if (avg == -1){
            System.out.println("No temperatures with that humidity");
        }else{
            avg = ((int)(avg*1000))/1000.0;
            System.out.println("Average Temp when high Humidity is " + avg);
        }
    }

    public void testLowestHumidityInFiles(){
        FileResource fr = new FileResource();
        CSVRecord lowest = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest Humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC"));
    }

    public void testLowestHumidityInManyFiles(){
        CSVRecord lowest = lowestHumidityInManyFile();
        System.out.println("Lowest Humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC"));
    }

    public void testFileWithColdestTemperature(){
        String filePath = fileWithColdestTemperature();
        FileResource fr = new FileResource(filePath);
        CSVRecord coldTemp = coldestHourInFiles(fr.getCSVParser());
        System.out.println("Coldest day was in file " + fileName);
        System.out.println("Coldest temperature on that day was " + coldTemp.get("TemperatureF"));
        System.out.println("All the Temperatures on the coldest day were: ");
        printAllContents(fr.getCSVParser());
    }

    public void testColdestInManyDays(){
        CSVRecord smallest = coldestInManyDays();
        System.out.println("coldest temperature is " + smallest.get("TemperatureF") + " at " + smallest.get("DateUTC"));
    }

    public void testcoldestHourInFile(){
        FileResource fr = new FileResource();
        CSVRecord smallest = coldestHourInFiles(fr.getCSVParser());
        System.out.println("coldest temperature is " + smallest.get("TemperatureF") + " at " + smallest.get("DateUTC"));
    }
} 
