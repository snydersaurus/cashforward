package org.cashforward.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for handling various Date calculations 
 *
 * @author Bill
 */
public class DateUtilities {

    /**
     * Return the number of days between two dates.
     *
     * @param start
     * @param end
     * @return positive value if end is after start, negative otherwise
     */
    public static int daysBetween(Date start, Date end){
        int result = 0;
        
        Calendar s = Calendar.getInstance();
        s.setTime(start);
        Calendar e = Calendar.getInstance();
        e.setTime(end);
        
        //need to account for different years of calendars...later 
        //int years = e.get(Calendar.YEAR) - s.get(Calendar.YEAR);
        
        result += e.get(Calendar.DAY_OF_YEAR) -
            s.get(Calendar.DAY_OF_YEAR);
        
        return result;
        
    }
    
    /**
     * Convenience method for getting the last date fo the year
     * @return
     */
    public static Date endOfThisYear() {
        Calendar thisYear = Calendar.getInstance();
        thisYear.set(Calendar.DAY_OF_YEAR, 
                thisYear.getMaximum(Calendar.DAY_OF_YEAR));
        return thisYear.getTime();
    }

    public static Date firstOfThisYear() {
        Calendar thisYear = Calendar.getInstance();
        thisYear.set(Calendar.DAY_OF_YEAR, 
                thisYear.getMinimum(Calendar.DAY_OF_YEAR));
        return thisYear.getTime();
    }
    
    /**
     * @param date 
     * @param daysAfter 
     * @return the Date daysAfter the given date
     */
    public static Date getDateAfterDays(Date date, int daysAfter) {
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.add(Calendar.DAY_OF_YEAR, daysAfter);
        
        return start.getTime();
    }

    /**
     * Get the Date so many unitsAfter a certain period.
     *
     * For example, to get 3 days after today:
     *
     * Date in3Days = getDateAfterPeriod(new Date(), Calendar.DAY, 3);
     *
     * @param date
     * @param period
     * @param unitsAfter
     * @return
     */
    public static Date getDateAfterPeriod(Date date, int period, int unitsAfter) {
        Calendar start = Calendar.getInstance();
        start.setTime(date);
        start.add(period, unitsAfter);
        
        return start.getTime();
    }
}
