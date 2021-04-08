package time;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeTest {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.println(date + " " + time.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
    }
}
