package modules;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ImageModule extends Module {
    @Override
    String RegexFormatFileName() {
        return "^[\\w\\d]+\\.(png|jpg|bmp)$";
    }

    @Override
    public String GetAnswerFunction(String fileName, int numFunction) {
        File file = new File(Paths.get(fileName).toUri());
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при чтении изображения");
        }
        switch (numFunction) {
            case 0:
                return GetSizeImage(image);
            case 1:
                return WriteExifImage(file);
            case 2:
                return WriteSizeImage(image);
        }
        throw new IllegalArgumentException("Метод с таким номером не найден");
    }

    @Override
    public String GetSummaryFunction(int numFunction) {
        switch (numFunction) {
            case 0:
                return GetSizeImageSummary();
            case 1:
                return WriteExifImageSummary();
            case 2:
                return WriteSizeImageSummary();
        }
        throw new IllegalArgumentException("Описание метода с таким номером не найден");
    }

    private String GetSizeImage(BufferedImage image) {
        DataBuffer dataBuffer = image.getData().getDataBuffer();
        long sizeBytes = ((long) dataBuffer.getSize()) * 4L;
        return String.valueOf(sizeBytes);
    }

    private String GetSizeImageSummary() {
        return "Вывод размера изображения";
    }

    private String WriteExifImage(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            StringBuilder listFileStr = new StringBuilder();

            System.out.println("EXIF information:");
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    listFileStr.append(tag).append('\n');
                }
            }

            return listFileStr.toString();
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при чтении изображения");
        } catch (ImageProcessingException e) {
            throw new IllegalArgumentException("Ошибка при чтении метаданных");
        }
    }

    private String WriteExifImageSummary() {
        return "Вывод размера изображения";
    }

    private String WriteSizeImage(BufferedImage image) {
        return image.getWidth() + " x " + image.getHeight();
    }

    private String WriteSizeImageSummary() {
        return "Вывод ширины и высоты картинки";
    }
}
