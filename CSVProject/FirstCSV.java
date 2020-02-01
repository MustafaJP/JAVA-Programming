import edu.duke.*;
import org.apache.commons.csv.*;

public class FirstCSV
{
    public void listExporters(CSVParser parser, String exportOfInterest){
        for (CSVRecord record : parser){
            String export = record.get("Exports");
            if (export.contains(exportOfInterest)){
                String country = record.get("country");
                System.out.println(country);
            }
        }
    }

    public void tester(){
        FileResource fr = new FileResource();
        System.out.println(countryInfo(fr.getCSVParser(), "Nauru"));
        listExportersTwoProducts(fr.getCSVParser(), "cotton", "flowers");
        System.out.println(numberOfExporter(fr.getCSVParser(), "cocoa"));
        bigExporters(fr.getCSVParser(), "$999,999,999,999");
    }

    public String countryInfo(CSVParser parser, String country){
        String string = "";
        for (CSVRecord record : parser){
            String export = record.get("Country");
            if (export.contains(country)){
                String found = record.get("Country");
                String otherInfo = record.get("Exports");
                System.out.println(otherInfo);
                String otherInfo1 = record.get("Value (dollars)");
                string = found + " : " + otherInfo + " : " + otherInfo1;
                break;
            }else{
                string = "NOT FOUND";   
            }
        }
        return string;
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        for (CSVRecord record : parser){
            String export = record.get("Exports");
            String word1 = "";
            String word2 = "";
            int wordIndex1 = export.indexOf(exportItem1, 0);
            int wordIndex2 = export.indexOf(exportItem2, 0);
            if (wordIndex1 != -1) word1 = export.substring(wordIndex1, wordIndex1 + (exportItem1.length()));
            if (wordIndex2 != -1) word2 = export.substring(wordIndex2, wordIndex2 + (exportItem2.length()));

            if (export.contains(exportItem1) && export.contains(exportItem2)){
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public int numberOfExporter(CSVParser parser, String exportItem){
        int count = 0;
        for (CSVRecord record : parser){
            String export = record.get("Exports");
            int wordIndex1 = export.indexOf(exportItem, 0);
            if (wordIndex1 != -1) count = count + 1;
        }
        return count;
    }

    public void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record : parser){
            String export = record.get("Value (dollars)");
            if (export.length() > amount.length()) {
                String country = record.get("Country");
                String value = record.get("Value (dollars)");
                System.out.println(country + " : " + value);
            }
        }
    }
}
