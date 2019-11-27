import modules.Module;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;
import java.util.Scanner;

public class App {
    private final Collection<Module> modules;

    public App(Collection<Module> modules) {
        this.modules = modules;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Приложение принимает в качестве аргумента командной строки имя файла");
            return;
        }
        String fileName = args[0];

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = ctx.getBean(App.class);

        Module targetModule = null;
        for (Module module : app.modules) {
            if (module.isValidationFileName(fileName))
                targetModule = module;
        }
        if (targetModule == null) {
            System.out.println("Ы");
            return;
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(i + " " + targetModule.GetSummaryFunction(i));
        }
        Scanner scanner = new Scanner(System.in);
        int numCommand = scanner.nextInt();
        System.out.println(targetModule.GetAnswerFunction(fileName, numCommand));
    }
}
