package modules;

import java.io.File;
import java.nio.file.Paths;

public class CatalogModule extends Module {
    @Override
    String RegexFormatFileName() {
        return "^[\\w\\d]+$";
    }

    @Override
    public String GetAnswerFunction(String fileName, int numFunction) {
        File curFolder = new File(Paths.get(fileName).toUri());
        switch (numFunction) {
            case 0:
                return WriteListFiles(curFolder);
            case 1:
                return GetCountFiles(curFolder);
            case 2:
                return GetAllSizeFiles(curFolder);
        }
        throw new IllegalArgumentException("Метод с таким номером не найден");
    }

    @Override
    public String GetSummaryFunction(int numFunction) {
        switch (numFunction) {
            case 0:
                return WriteListFilesSummary();
            case 1:
                return GetCountFilesSummary();
            case 2:
                return GetAllSizeFilesSummary();
        }
        throw new IllegalArgumentException("Описание метода с таким номером не найден");
    }

    private String WriteListFiles(File curFolder) {
        if (curFolder == null || !curFolder.exists())
            return "Nothing to show";
        StringBuilder listFileStr = new StringBuilder();
        for (File fileEntry : curFolder.listFiles()) {
            listFileStr.append(fileEntry.getName()).append('\n');
        }
        return listFileStr.toString();
    }

    private String WriteListFilesSummary() {
        return "Вывод списка файлов в каталоге";
    }

    private String GetCountFiles(File curFolder) {
        if (curFolder == null || !curFolder.exists())
            return "0";
        return String.valueOf(curFolder.listFiles().length);
    }

    private String GetCountFilesSummary() {
        return "Подсчет количества всех файлов и папок в каталоге";
    }

    private String GetAllSizeFiles(File curFolder) {
        if (curFolder == null || !curFolder.exists())
            return "0";
        long totalSize = 0;
        for (File fileEntry : curFolder.listFiles()) {
            if (fileEntry.isFile())
                totalSize += fileEntry.getTotalSpace();
        }
        return String.valueOf(totalSize);
    }

    private String GetAllSizeFilesSummary() {
        return "Подсчет размера всех файлов в каталоге";
    }
}
