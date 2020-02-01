import edu.duke.*;

public class TEST12
{
    private String mystery(String dna) {
        int pos = dna.indexOf("T");
        int count = 0;
        int startPos = 0;
        String newDna = "";
        if (pos == -1) {
            return dna;
        }
        while (count < 3) {
            count += 1;
            newDna = newDna + dna.substring(startPos,pos);
            startPos = pos+1;
            pos = dna.indexOf("T", startPos);
            if (pos == -1) {
                break;
            }
        }
        newDna = newDna + dna.substring(startPos);
        return newDna;
    }

    public void testMystery(){
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.toUpperCase();
        String ans = mystery(dna);
        System.out.println(ans);
    }

    public static void main (String[] args){
        TEST12 pr = new TEST12();
        pr.testMystery();
    }
}
