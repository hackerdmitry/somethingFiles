package modules;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.mp4.Mp4MetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class Mp3Module extends Module {
    @Override
    String RegexFormatFileName() {
        return "^[\\w\\d]+\\.(mp3)$";
    }

    @Override
    public String GetAnswerFunction(String fileName, int numFunction) {
        File file = new File(Paths.get(fileName).toUri());
        AudioFileFormat fileFormat;
        try {
            fileFormat = AudioSystem.getAudioFileFormat(file);
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при чтении mp3 файла");
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("Неизвестное расширение музыкального файла");
        }
        switch (numFunction) {
            case 0:
                return GetNameMusicTrack(file);
            case 1:
                return GetDurationMusicTrack(fileFormat);
        }
        throw new IllegalArgumentException("Метод с таким номером не найден");
    }

    @Override
    public String GetSummaryFunction(int numFunction) {
        switch (numFunction) {
            case 0:
                return GetNameMusicTrackSummary();
            case 1:
                return GetDurationMusicTrackSummary();
        }
        throw new IllegalArgumentException("Описание метода с таким номером не найден");
    }

    private String GetNameMusicTrack(File file) {
        try {
            Metadata metadata = Mp4MetadataReader.readMetadata(file);

            for (Directory directory : metadata.getDirectories()) {
                if (directory.getName().equals("File")) {
                    return directory.getDescription(1);
                }
            }
            return "Ничего интересного...";
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при чтении изображения");
        } catch (ImageProcessingException e) {
            throw new IllegalArgumentException("Ошибка при чтении метаданных");
        }
    }

    private String GetNameMusicTrackSummary() {
        return "Вывод названия трека из тегов";
    }

    private String GetDurationMusicTrack(AudioFileFormat fileFormat) {
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = (fileFormat).properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int milliseconds = (int) (microseconds / 1000);
            int seconds = (milliseconds / 1000);
            return String.valueOf(seconds);
        }
        return String.valueOf(-1);
    }

    private String GetDurationMusicTrackSummary() {
        return "Вывод длительности в секундах";
    }

}
