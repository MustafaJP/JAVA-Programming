import java.io.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class ThirdCSV
{
    public String pathFinder(int year){
        String path = null;
        if (year == 2012){
            path = "/Users/mustafa/Documents/Online Courses/JAVA/CSVProject/testing/yob2012short.csv";
        }
        if (year == 2013){
            path = "/Users/mustafa/Documents/Online Courses/JAVA/CSVProject/testing/yob2013short.csv";
        }
        if (year == 2014){
            path = "/Users/mustafa/Documents/Online Courses/JAVA/CSVProject/testing/yob2014short.csv";
        }
        return path;
    }

    /*public String pathFinder(){
    CSVRecord smallestSoFar = null;
    String filePath = null;
    DirectoryResource dr = new DirectoryResource(); 
    for (File f: dr.selectedFiles()){
    filePath = f.getPath();
    }
    return filePath;
    }*/

    public void printAllNames(){
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)){
            System.out.println("Name " + rec.get(0) + 
                "Gender " + rec.get(1) + 
                "Num birth " + rec.get(2));
        }
    }

    public void totalBirth(FileResource fr){
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")){
                totalBoys += 1;
            }
            if (rec.get(1).equals("F")){
                totalGirls += 1;
            }
        }
        System.out.println("Total Births = " + totalBirths);
        System.out.println("Total Boys = " + totalBoys);
        System.out.println("Total Girls = " + totalGirls);
    }

    public int gettingRank(int year,String name, String gender){
        int rank = 0;
        int count = 0;
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)){
            if (rec.get(1).equals(gender)){
                count += 1;
                if (rec.get(0).equals(name)){
                    rank = count;
                }
            } 
            if (rank == 0){
                rank = -1;
            }
        }
        return rank;
    }

    public String gettingName(int year,int rank, String gender){
        int count = 0;
        String name = null;
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)){
            if (rec.get(1).equals(gender)){
                count += 1;
                if (rank == count){
                    name = rec.get(0);
                }
            } 
            if (name == null){
                name = null;
            }
        }
        return name;
    }

    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int Rank = gettingRank(year, name, gender);
        String Name = gettingName(newYear, Rank, gender);
        System.out.println(name + " born in " + year + " would be " + Name + " if she was born in " + newYear);
    }
    
    //this method of yearOfHighestRank is wrong. dunno what I have to correct
    public int yearOfHighestRank(String name, String gender){
        String Year = null;
        int rank = 1000000;
        int count = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            for (CSVRecord rec : fr.getCSVParser(false)){
                int temp = 0;
                if (rec.get(1).equals(gender)){
                    count += 1;
                    if (rec.get(0).equals(name)){
                        temp = count;
                    }
                }
                if (temp != 0){
                    if (temp < rank) {
                        rank = temp;
                        Year = f.getName();
                    }
                }
            }
        }
        int year = Integer.parseInt(Year.substring(3, 7));
        return year;
    }

    public double getAverageRank(String name, String gender){

        int sum = 0;
        int counter = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int count = 0;
            for (CSVRecord rec : fr.getCSVParser(false)){
                if (rec.get(1).equals(gender)){
                    count += 1;
                    if (rec.get(0).equals(name)){
                        sum += count;
                        counter += 1;
                        break;
                    }
                }
            }
        }
        double avg = ((double)sum)/counter;
        return avg;
    }

    public  int getTotalBirthsRankedHigher(int year,String name, String gender){
        int rank = 0;
        int count = 0;
        int sum = 0;
        int count1 = 0;
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)){
            if (rec.get(1).equals(gender)){
                count += 1;
                if (rec.get(0).equals(name)){
                    rank = count;
                }
            }
        }
        if (rank == 0){  
            return -1;
        }else {
            for (CSVRecord rec : fr.getCSVParser(false)){
                if (rec.get(1).equals(gender)){
                    count1 += 1;
                    if (rank > count1){
                        sum += Integer.parseInt(rec.get(2));
                    }
                } 
            }
            return sum;
        }
    }

    public void testGetTotalBirthsRankedHigher(){
        System.out.println(getTotalBirthsRankedHigher(2012, "Emily", "F"));
    }

    public void testgetAverageRank(){
        System.out.println(getAverageRank("Robert", "M"));
    }

    public void testyearOfHighestRank(){
        String name = "Genevieve";
        String gender = "F";
        int highYear = yearOfHighestRank(name, gender);
        System.out.println(name + " highest ranking was in " + highYear );
    }

    public void testWhatIsNameInYear(){
        whatIsNameInYear("Owen", 2012, 2014, "M");
    }

    public void testGetName(){
        String name =gettingName(2012, 450, "M");
        if (name.isEmpty()){
            System.out.println("NO NAME");
        }else{
            System.out.println(name);
        }
    }

    public void testGetRank(){
        System.out.println(gettingRank(2012, "Frank", "M"));
    }

    public void testtotalBirth(){
        FileResource fr = new FileResource();
        totalBirth(fr);
    }
}
