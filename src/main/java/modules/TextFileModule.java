package modules;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TextFileModule extends Module {
    @Override
    String RegexFormatFileName() {
        return "^[\\w\\d]+\\.(txt)$";
    }

    @Override
    public String GetAnswerFunction(String fileName, int numFunction) {
        String fileString;
        try {
            fileString = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка при чтении текстового файла");
        }
        switch (numFunction) {
            case 0:
                return GetCountLinesText(fileString);
            case 1:
                return GetCountFrequencyChars(fileString);
            case 2:
                return GetStringText(fileString);
        }
        throw new IllegalArgumentException("Метод с таким номером не найден");
    }

    @Override
    public String GetSummaryFunction(int numFunction) {
        switch (numFunction) {
            case 0:
                return GetCountLinesTextSummary();
            case 1:
                return GetCountFrequencyCharsSummary();
            case 2:
                return GetStringTextSummary();
        }
        throw new IllegalArgumentException("Описание метода с таким номером не найден");
    }

    private String GetCountLinesText(String textFile) {
        return String.valueOf(textFile.split("[\\r\\n]").length + 1);
    }

    private String GetCountLinesTextSummary() {
        return "Подсчет и вывод количества строк";
    }

    private String GetCountFrequencyChars(String textFile) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (int i = 0; i < textFile.length(); i++) {
            frequency.put(textFile.charAt(i), frequency.getOrDefault(textFile.charAt(i), 0) + 1);
        }
        StringBuilder resStrBuilder = new StringBuilder();
        for (Map.Entry<Character, Integer> keyPair : frequency.entrySet()) {
            resStrBuilder.append(keyPair.getKey()).append(" - ").append(keyPair.getValue()).append('\n');
        }
        return resStrBuilder.toString();
    }

    private String GetCountFrequencyCharsSummary() {
        return "Вывод частоты вхождения каждого символа";
    }

    private String GetStringText(String textFile) {
        return textFile;
    }

    private String GetStringTextSummary() {
        return "Вывод файла в консоль";
    }
}
