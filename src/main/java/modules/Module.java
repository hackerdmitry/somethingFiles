package modules;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Module {
    final Pattern pattern;

    protected Module() {
        pattern = Pattern.compile(RegexFormatFileName());
    }

    public boolean isValidationFileName(String fileName) {
        Matcher matcher = pattern.matcher(Paths.get(fileName).getFileName().toString());
        return matcher.matches();
    }

    abstract String RegexFormatFileName();

    public abstract String GetAnswerFunction(String fileName, int numFunction);

    public abstract String GetSummaryFunction(int numFunction);
}
