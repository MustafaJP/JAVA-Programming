import java.io.*;
import edu.duke.StorageResource;
import edu.duke.FileResource;

public class AllGeneStored
{
    public int stopCodon(String dna, int startIndex, String stopCodon)
    {
        int currIndex = dna.indexOf(stopCodon, startIndex+3);
        while (currIndex != -1){
            int diff = currIndex - startIndex;
            if (diff % 3 == 0){
                return currIndex;
            }else {
                currIndex = dna.indexOf(stopCodon, currIndex + 1);
            }
        }
        return dna.length();
    }

    public String findGene(String Dna, int where){

        int startIndex = Dna.indexOf("ATG", where);
        if (startIndex == -1){
            return " ";
        }

        int taa = stopCodon(Dna,startIndex,"TAA");
        int tag = stopCodon(Dna,startIndex,"TAG");
        int tga = stopCodon(Dna,startIndex,"TGA");

        /*int minIndex = Math.min(taa,Math.min(tag,tga));
        if (minIndex == Dna.length()){
        return " ";
        }*/

        int minIndex = 0;
        if (taa == -1 || (tga != -1 && tga < taa)){
            minIndex = tga;
        }else{
            minIndex = taa;
        }
        if (minIndex == -1 || (tag == -1 && tag < minIndex)){
            minIndex = tag;   
        }else{
            return " ";   
        }
        return Dna.substring(startIndex, minIndex +3);
    }

    public StorageResource getAllGene(String dnA){
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        while (true) {
            String currGene = findGene(dnA, startIndex);
            if (currGene.isEmpty()){
                break;
            }
            geneList.add(currGene);  
            startIndex = dnA.indexOf(currGene, startIndex) +
            currGene.length();
        }
        return geneList;
    }
    

    public void processGene(String string){
        int startIndex = 0;
        int ratiocount = 0;
        int lengthcount = 0;
        int high = 0;
        String highGene = "";
        while (true) {
            String currGene = findGene(string, startIndex);
            int geneLength = currGene.length();
            float ratio = Ratio(currGene);
            if (currGene.isEmpty()) break;
            if (geneLength > 60){
                if (high > geneLength) {
                    highGene = currGene;
                    high = geneLength;
                }
                lengthcount = lengthcount + 1;
                //System.out.println(currGene);
            }
            if (ratio > 0.35) {
                ratiocount = ratiocount + 1;
                //System.out.println(currGene);
            }
            startIndex = string.indexOf(currGene, startIndex) + currGene.length();
        }
        System.out.println("THE LONGEST GENE IS  " + highGene);
    }

    public float Ratio(String dnaString) {
        int lenght = dnaString.length();
        int startpostionC = 0;
        int startpostionG = 0;
        float countC = 0;
        float countG = 0;
        while (true) {
            int c = dnaString.indexOf("C", startpostionC);
            int g = dnaString.indexOf("G", startpostionG);
            if (c == -1){
            }else{
                countC = countC + 1;
            }
            if (g == -1){
            }else{
                countG = countG + 1;
            }
            if (c == -1 && g == -1) break;

            startpostionG = g + 1;
            startpostionC = c + 1;
        }
        float cgratio = (countC+countG)/lenght;
        return cgratio;
    }

    public int CTGCodon(String DnaString) {
        int startpostionCTG = 0;
        int countCTG = 0;
        while (true) {
            int CTG = DnaString.indexOf("CTG", startpostionCTG);
            if (CTG == -1){
                break;
            }else{
                countCTG = countCTG + 1;
            }
            startpostionCTG = CTG + 3;
        }
        return countCTG;
    }

    /*public void testOn(String dna){
    System.out.println("testing all genes here ");
    StorageResource gene = getAllGene(dna);
    for (String s: gene.data()){
    System.out.println(s);
    }
    }*/

    public void testProcessGenes() {
        FileResource fr = new FileResource();
        String dna = fr.asString();
        
    }
    
    public void test2(){
        String dna = "xxxxyyyyCTGCTGxxxxyyyyCTGxxxxyyyy";
        int Codon = CTGCodon(dna);
        System.out.println(Codon);
    }

    public void test1(){
        String dna = "CCGGWE";
        float ratio = Ratio(dna);
        System.out.println(ratio);
    }

    public void testCode()
    {
        String DNA = "xxxxyyyyTAAxxxxyyyyTAAxxxxyyyy";
        int dex = stopCodon(DNA,0,"TAA");
        if (dex != 7) {
            System.out.println("error");
        }else {
            System.out.println("sucess");
        }
    }
}
