package tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.salary_manager.ISalaryManager;
import src.salary_manager.Factory;

public class SalaryMangerTest {
    public static void main(String[] args) {
        List<String> source = List.of(
                "JOSE=MO08:00-19:00",
                "JUAN=TH12:00-17:00,FR09:01-16:00",
                "ANA=SA09:00-13:00,MO08:10-16:00");

        Map<String, Double> tests = new HashMap<>();
        tests.put("JOSE", 179.0);
        tests.put("JUAN", 179.0);
        tests.put("ANA", 209.0);

        ISalaryManager manager = Factory.NewSalaryManager();

        Map<String, Double> result = manager.ProcessData(source);

        for (String key : result.keySet()) {
            if (result.get(key) != tests.get(key)) {
                System.err.printf("Output %.2f  expected %.2f\n", result.get(key), tests.get(key));
            }
        }
    }
}
