import src.salary_manager.Factory;
import src.salary_manager.ISalaryManager;

public class Main {

    public static void main(String[] args) {
        ISalaryManager manager = Factory.NewSalaryManager();
        System.out.println(manager.ProcessData(manager.LoadFile(args[0])));
    }
}