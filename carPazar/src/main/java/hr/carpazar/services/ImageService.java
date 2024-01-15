package hr.carpazar.services;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ImageService {
    @Autowired
    private static MessageSource msgSource;

    public static void saveAsPng(List<MultipartFile> imageList, String dirPath){
        int fileCount;
        File directory = new File(dirPath);
        if(directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            if(files != null)
                fileCount = files.length;
            else
                fileCount = 0;
            System.out.println(files.length);
        }
        else{
            fileCount = 0;
            System.out.println("ovo se nikad ne bi smilo ispisat"); // custom exception stvorit
        }

        AtomicInteger counter = new AtomicInteger(fileCount + 1);
        imageList.stream().forEach(image -> {
            System.out.println();
            String newFilename = "img_" + (counter.getAndIncrement()) + ".png";
            try {
                File outputImg = new File(dirPath + newFilename);
                BufferedImage originalImg = ImageIO.read(image.getInputStream());
                resizeCompression(originalImg, outputImg);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        });
    }

    private static void resizeCompression(BufferedImage originalImg, File outputImg){
        try {
            Thumbnails.of(originalImg).size(1024, 768).outputQuality(1.0).outputFormat("png").toFile(outputImg);
        } catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }
}
