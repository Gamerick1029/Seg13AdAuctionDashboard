package Backend.Model.Interfaces;

import java.util.Date;
import java.util.HashMap;

public class StepHolder {

    public static final Date SUNDAY = new Date(0);
    public static final Date MONDAY = new Date(1);
    public static final Date TUESDAY = new Date(2);
    public static final Date WEDNESDAY = new Date(3);
    public static final Date THURSDAY = new Date(4);
    public static final Date FRIDAY = new Date(5);
    public static final Date SATURDAY = new Date(6);

    //Use these to get the dates corresponding with specific dayToDate in the week or hours in the day
    //hours are from 0-23
    public static HashMap<String, Date> dayToDate = new HashMap<>();
    public static HashMap<Date, String> dateToDay = new HashMap<>();
    public static Date[] hours = new Date[24];

    static {
        hours[0] = new Date(7);
        hours[1] = new Date(8);
        hours[2] = new Date(9);
        hours[3] = new Date(10);
        hours[4] = new Date(11);
        hours[5] = new Date(12);
        hours[6] = new Date(13);
        hours[7] = new Date(14);
        hours[8] = new Date(15);
        hours[9] = new Date(16);
        hours[10]  = new Date(17);
        hours[11]  = new Date(18);
        hours[12]  = new Date(19);
        hours[13]  = new Date(20);
        hours[14]  = new Date(21);
        hours[15] = new Date(22);
        hours[16]  = new Date(23);
        hours[17]  = new Date(24);
        hours[18]  = new Date(25);
        hours[19]  = new Date(26);
        hours[20]  = new Date(27);
        hours[21]  = new Date(28);
        hours[22]  = new Date(29);
        hours[23]  = new Date(30);

        dayToDate.put("Mon", MONDAY);
        dayToDate.put("Tue", TUESDAY);
        dayToDate.put("Wed", WEDNESDAY);
        dayToDate.put("Thu", THURSDAY);
        dayToDate.put("Fri", FRIDAY);
        dayToDate.put("Sat", SATURDAY);
        dayToDate.put("Sun", SUNDAY);

        dateToDay.put(MONDAY, "Mon");
        dateToDay.put(TUESDAY, "Tue");
        dateToDay.put(WEDNESDAY, "Wed");
        dateToDay.put(THURSDAY, "Thu");
        dateToDay.put(FRIDAY, "Fri");
        dateToDay.put(SATURDAY, "Sat");
        dateToDay.put(SUNDAY, "Sun");
    }

    public enum Step {
        DAY, WEEK, MONTH, HOUR_OF_DAY, DAY_OF_WEEK
    }

    public String dateToDayOfWeek(Date date){
        return dateToDay.get(date);
    }

    public static String dateToHourOfDay(Date date){
        for (Integer i = 0; i < hours.length; i++) {
            if (hours[i] == date) return i.toString();
        }
        return "";
    }

}
