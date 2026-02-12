package src.salary_manager;

import java.util.List;
import java.util.Map;

public interface ISalaryManager {

    Map<String, Double> ProcessData(List<String> employees);
    List<String> LoadFile(String path);
} 