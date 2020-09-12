package printer;

public class ConsolePrinter implements Printer {

    public static final ConsolePrinter INSTANCE=new ConsolePrinter();

    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
