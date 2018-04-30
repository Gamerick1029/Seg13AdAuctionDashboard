package Backend.Model.Interfaces;

import java.util.Date;
import java.util.HashMap;

public class StepHolder {

    private static final Date SUNDAY = new Date(0);
    private static final Date MONDAY = new Date(1);
    private static final Date TUESDAY = new Date(2);
    private static final Date WEDNESDAY = new Date(3);
    private static final Date THURSDAY = new Date(4);
    private static final Date FRIDAY = new Date(5);
    private static final Date SATURDAY = new Date(6);

    //Use these to get the dates corresponding with specific days in the week or hours in the day
    //hours are from 0-23
    public static HashMap<String, Date> days = new HashMap<>();
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

        days.put("Mon", MONDAY);
        days.put("Tue", TUESDAY);
        days.put("Wed", WEDNESDAY);
        days.put("Thu", THURSDAY);
        days.put("Fri", FRIDAY);
        days.put("Sat", SATURDAY);
        days.put("Sun", SUNDAY);
    }

    public enum Step {
        DAY, WEEK, MONTH, HOUR_OF_DAY, DAY_OF_WEEK
    }

}
