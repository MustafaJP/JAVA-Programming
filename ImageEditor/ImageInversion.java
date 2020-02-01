import java.io.*;
import edu.duke.*;

public class ImageInversion{

    public ImageResource makeGray(ImageResource inImage){
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel : outImage.pixels()){
            Pixel inPixel = inImage .getPixel (pixel.getX(), pixel.getY());
            int Redinvertor = 255 - inPixel.getRed();
            int Blueinvertor = 255 - inPixel.getBlue();
            int Greeninvertor = 255 - inPixel.getGreen();
            pixel.setRed(Redinvertor);
            pixel.setBlue(Blueinvertor);
            pixel.setGreen(Greeninvertor);
        }
        return outImage;
    }

    public void doSave(){
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()){
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeGray(inImage);
            String fname = inImage.getFileName();
            String newName = "inverted-" + fname;
            gray.setFileName(newName);
            gray.draw();
            gray.save();
        }
    }
}
