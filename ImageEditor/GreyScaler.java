import java.io.*;
import edu.duke.*;

public class GreyScaler{

    public ImageResource makeGray(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());

        for (Pixel pixel : outImage.pixels()){
            Pixel inPixel = inImage .getPixel (pixel.getX(), pixel.getY());
            int avg = (inPixel.getRed() + inPixel.getRed() + inPixel.getGreen())/3;
            pixel.setRed(avg);
            pixel.setBlue(avg);
            pixel.setGreen(avg);
        }
        return outImage;
    }

    public void doSave(){
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeGray(inImage);
            String fname = inImage.getFileName();
            String newName = "gray-" + fname + ".png";
            gray.setFileName(newName);
            gray.draw();
            gray.save();
        }
    }
}
