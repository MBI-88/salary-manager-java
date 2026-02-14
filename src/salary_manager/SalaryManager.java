package src.salary_manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SalaryManager implements ISalaryManager {
     Map<String, Double> ToPay;

     public Map<String, Double> ProcessData(List<String> employees) {
          this.ToPay = new HashMap<String, Double>();
          Map<String, List<Data>> payments = RangePayments.newData();

          for (String employee : employees) {
               String name = employee.split("=")[0];
               String[] ranges = employee.split("=")[1].split(",");
               this.ToPay.put(name, 0.0);

               for (String r : ranges) {
                    String day = r.substring(0, 2);
                    String[] timeRange = r.split("-");
                    LocalTime start = this.Formater(timeRange[0].substring(2));
                    LocalTime end = this.Formater(timeRange[1]);

                    switch (day) {
                         case "MO", "TU", "WE", "TH", "FR":
                              this.Operation(payments.get("Week"), name, start, end);
                              break;
                         case "SA", "SU":
                              this.Operation(payments.get("Weekend"), name, start, end);
                              break;

                         default:
                              break;
                    }
               }
          }

          return this.ToPay;
     }

     public List<String> LoadFile(String path) {
          List<String> content;
          try {
               content = Files.readAllLines(Path.of(path));
          } catch (IOException e) {
               System.out.println(e);
               return null;
          }
          return content;
     }

     private LocalTime Formater(String hours) {
          return LocalTime.parse(hours);
     }

     private void Operation(List<Data> payrange, String name, LocalTime start, LocalTime end) {
     
          for (int i = 0; i < payrange.size(); i++) {
               if (start.compareTo(this.Formater(payrange.get(i).EndRange)) > 0) {
                    continue;
               } else if (start.compareTo(this.Formater(payrange.get(i).StartRange)) > -1
                         &&
                         start.compareTo(this.Formater(payrange.get(i).EndRange)) < 1
                         &&
                         end.compareTo(this.Formater(payrange.get(i).EndRange)) < 1) {
                    
                    Double pay = this.ToPay.get(name);
                    pay += payrange.get(i).Payment * (end.getHour() - start.getHour());
                    this.ToPay.put(name, pay);

               } else if (start.compareTo(this.Formater(payrange.get(i).StartRange)) > -1
                         &&
                         start.compareTo(this.Formater(payrange.get(i).EndRange)) < 1
                         &&
                         end.compareTo(this.Formater(payrange.get(i).EndRange)) > 0) {

                    Double pay = this.ToPay.get(name);
                    pay += payrange
                              .get(i).Payment * (this.Formater(payrange.get(
                                        i).EndRange).getHour() - start.getHour());
                              
                    for (int t = i+1; t < payrange.size(); t++) {
                         if (end.compareTo(this.Formater(payrange.get(t).StartRange)) > -1
                                   && end.compareTo(this.Formater(
                                             payrange.get(t).EndRange)) < 1) {
                              
                              
                              pay += payrange.get(t).Payment
                                        * (end.getHour() - this.Formater(payrange.get(t).StartRange).getHour());
                                        
                              this.ToPay.put(name, pay);

                         } else if (end.compareTo(this.Formater(payrange.get(t).EndRange)) > 0) {

                              pay += payrange.get(t).Payment * (end.getHour() - start.getHour());
                              this.ToPay.put(name, pay);
                         }
                    }

               }
          }

     }
}
