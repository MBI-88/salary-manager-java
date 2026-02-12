package src.salary_manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class  RangePayments {

    public static Map<String,List<Data>> newData() {
        Map<String, List<Data>> payments = new HashMap<>();
        payments.put("Week", List.of(
                new Data("00:01", "09:00", 25.0),
                new Data("09:01", "18:00", 15.0),
                new Data("18:01", "23:00", 20.0)));

        payments.put("Weekend", List.of(
                new Data("00:01", "09:00", 30.0),
                new Data("09:01", "18:00", 20.0),
                new Data("18:01", "23:00", 25.0)));

        return payments;
    }
}


