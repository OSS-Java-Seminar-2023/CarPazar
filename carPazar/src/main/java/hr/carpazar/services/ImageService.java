package hr.carpazar.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public static void saveAsPng(List<MultipartFile> imageList, String dirPath) {
        int fileCount;
        File directory = new File(dirPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null)
                fileCount = files.length;
            else
                fileCount = 0;
        } else {
            fileCount = 0;
        }

        AtomicInteger counter = new AtomicInteger(fileCount + 1);
        imageList.stream().forEach(image -> {
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

    private static void resizeCompression(BufferedImage originalImg, File outputImg) {
        try {
            Thumbnails.of(originalImg).size(600, 450).outputQuality(1.0).outputFormat("png").toFile(outputImg);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}
