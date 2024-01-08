package hr.carpazar.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    public static void saveAsPng(MultipartFile imgFile, String directory, int counter){
        String newFilename = "img_" + counter + ".png";
        try {
            File outputImg = new File(directory + newFilename);
            BufferedImage originalImg = ImageIO.read(imgFile.getInputStream());
            ImageIO.write(originalImg, "png", outputImg);
        } catch(IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }
}
