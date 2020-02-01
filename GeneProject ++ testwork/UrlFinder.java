import java.io.*;
import edu.duke.*;

public class UrlFinder
{
    public StorageResource findURLs(String url) {
        URLResource page = new URLResource(url);
        String source = page.asString();
        StorageResource store = new StorageResource();
        int start = 0;
        while (true) {
            int index = source.indexOf("href=", start);
            if (index == -1) {
                break;
            }
            int firstQuote = index+6; // after href="
            int endQuote = source.indexOf("\"", firstQuote);
            String sub = source.substring(firstQuote, endQuote);
            if (sub.startsWith("http")) {
                store.add(sub);
            }
            start = endQuote + 1;
        }
        return store;
    }

    public void testURL() {
        StorageResource s1 = findURLs("https://www.whitehouse.gov");
        StorageResource s2 = findURLs("http://www.doctorswithoutborders.org");
        for (String link : s1.data()) {
            System.out.println(link);
        }
        System.out.println("size = " + s1.size());
        System.out.println("size = " + s2.size());
    }

    public void testUrlCode(){
        URLResource file = new  URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String item : file.words()) {
            String itemLower = item.toLowerCase();
            int pos = itemLower.indexOf("youtube.com");
            if (pos != -1) {
                int beg = item.lastIndexOf("\"",pos);
                int end = item.indexOf("\"", pos+1);
                System.out.println(item.substring(beg+1,end));
            }
        }
    }
}
