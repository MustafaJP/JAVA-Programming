import java.io.*;
import edu.duke.*;

public class Test1 {
    private String findSimpleGene(String dna){
        int startIndex = 0;
        String gene = null;
        int atg = dna.indexOf("ATG", startIndex);
        if (atg == -1) return gene;
        int taa = dna.indexOf("ATG", atg+3);
        if (taa == -1) return gene;
        if ((atg-taa+3) % 3 == 0) {
            gene = dna.substring(atg, taa+3);
            return gene;
        }else{
            return gene;
        }
    }

    private String findSimpleGeneSimplifiedVersion(String dna, String startCodon, String stopCodon){
        int startIndex = 0;
        String gene = null;
        int startCodonIndex = dna.indexOf(startCodon, startIndex);
        if (startCodonIndex == -1) return gene;
        int stopCodonIndex = dna.indexOf(stopCodon, startCodonIndex+3);
        if (stopCodonIndex == -1) return gene;
        if ((startCodonIndex-(stopCodonIndex+3)) % 3 == 0) {
            gene = dna.substring(startCodonIndex, stopCodonIndex+3);
            return gene;
        }else{
            return gene;
        }
    }

    private boolean twoOccurrences(String stringA, String stringB){
        int startIndex = 0;
        int count = 0;
        while (true){
            int startCodonIndex = stringB.indexOf(stringA, startIndex);
            if (startCodonIndex == -1) break;
            count += 1;
            startIndex = startCodonIndex + 3;
        }
        if (count >= 2){
            return true;
        }else{
            return false;
        }
    }

    private String lastPart(String stringA, String stringB){
        int startIndex = 0;
        int startCodonIndex = stringB.indexOf(stringA, startIndex); 
        if (startCodonIndex == -1) return stringB;
        String gene = stringB.substring(startCodonIndex+(stringA.length()), stringB.length());
        return gene;
    }

    private int howMany(String stringA, String stringB){
        int startIndex = 0;
        int count = 0;
        while (true){
            int startCodonIndex = stringB.indexOf(stringA, startIndex);
            if (startCodonIndex == -1) break;
            count += 1;
            startIndex = startCodonIndex + stringA.length();
        }
        return count;
    }

    private int stopCodon(String dna, int startIndex, String stopCodon)
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
        return -1;
    }

    private int countGenes(String Dna){
        int startIndex = 0;
        int count = 0;
        while (true){
            startIndex = Dna.indexOf("ATG", startIndex);
            if (startIndex == -1) break;
            int taa = stopCodon(Dna,startIndex,"TAA");
            int tag = stopCodon(Dna,startIndex,"TAG");
            int tga = stopCodon(Dna,startIndex,"TGA");
            int minIndex = 0;
            if (taa == -1 || (tga != -1 && tga < taa)){
                minIndex = tga;
            }else{
                minIndex = taa;
            }
            if (minIndex == -1 || (tag != -1 && tag < minIndex)){
                minIndex = tag;   
            }
            if (minIndex != -1) {
                count += 1;
                String DNA = Dna.substring(startIndex, minIndex+3);
                startIndex = Dna.indexOf(DNA, startIndex) + (DNA.length()+3);
                System.out.println(DNA);
            } else if(minIndex == -1){
                startIndex+=1;
            }
        }
        return count;
    }

    private String findGene(String Dna, int where){
        int startIndex = Dna.indexOf("ATG", where);
        if (startIndex == -1) return "";
        int taa = stopCodon(Dna,startIndex,"TAA");
        int tag = stopCodon(Dna,startIndex,"TAG");
        int tga = stopCodon(Dna,startIndex,"TGA");
        int minIndex = 0;
        if (taa == -1 || (tga != -1 && tga < taa)){
            minIndex = tga;
        }else{
            minIndex = taa;
        }
        if (minIndex == -1 || (tag != -1 && tag < minIndex)){
            minIndex = tag;   
        }
        if (minIndex == -1) return "";   
        return Dna.substring(startIndex, minIndex +3);
    }

    private StorageResource getAllGene(String dnA){
        StorageResource geneList = new StorageResource();
        int startIndex = 0;
        int count = 0;
        while (true) {
            String currGene = findGene(dnA, startIndex);
            if (currGene.isEmpty()) break;
            geneList.add(currGene); 
            count +=1;
            startIndex = dnA.indexOf(currGene, startIndex) + currGene.length();
        }
        return geneList;
    }

    private void processGenes(StorageResource sr){
        int startIndex = 0;
        int ratiocount = 0;
        int lengthcount = 0;
        int longestLength = 0;
        for (String link : sr.data()) {
            float ratio = CGRatio(link);
            if (link.length() > 60){
                if (longestLength < link.length()) {
                    longestLength = link.length();
                }
                lengthcount += 1;
            }
            if (ratio > 0.35) {
                ratiocount += 1;
            }
            startIndex = link.indexOf(link, startIndex) + link.length();
        }
        System.out.println("The length of the longest gene is " + longestLength);
        System.out.println("The number of genes with length greater than or equal to 60 is " + lengthcount);
        System.out.println("The number of genes with CGratio greater than or equal to 0.35 is " + ratiocount);
    }

    private float CGRatio(String dnaString) {
        int lenght = dnaString.length();
        int startpostionC = 0;
        int startpostionG = 0;
        float countC = 0;
        float countG = 0;
        while (true) {
            int c = dnaString.indexOf("C", startpostionC);
            int g = dnaString.indexOf("G", startpostionG);
            if (c != -1) {
                countC = countC + 1;
                startpostionC = c + 1;
            }else if (g != -1) {
                countG = countG + 1;
                startpostionG = g + 1;
            }else {
                break;
            }
            //(c == -1 && g == -1)
        }
        System.out.println(countC);
        System.out.println(countG);
        System.out.println(lenght);
        float cgratio = (countC+countG)/lenght;
        return cgratio;
    }

    private int CTGCodon(String DnaString) {
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

    public void testProcessGenes() {
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        StorageResource sr = getAllGene(dna);
        processGenes(sr);
    }

    public void testCTGCodon(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        int Codon = CTGCodon(dna);
        System.out.println(Codon);
    }

    public void testCGRatio(){
        //FileResource fr = new FileResource();
        //String dna = fr.asString();
        //dna = dna.toUpperCase();
        float ratio = CGRatio("ATGCCATAGASDDTGHCHCHCHCH");
        System.out.println(ratio);
    }

    public void testgetAllGenes(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        StorageResource sr = getAllGene(dna);
        System.out.println(sr);
    }

    public void testtwoOccurrences(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        System.out.println(twoOccurrences("atg", dna));
    }

    public void testCountGenes(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        int gene = countGenes(dna);
        System.out.println(gene);
    }

    public void testfindSimpleGeneSimplifiedVersion(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        String gene = findSimpleGeneSimplifiedVersion(dna, "ATG", "TAA");
        System.out.println(gene.toUpperCase());
        System.out.println(gene.toLowerCase());
    }

    public void testfindSimpleGene(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        System.out.println(findSimpleGene(dna));
    }
}