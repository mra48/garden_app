package com.cs1530_group1.gardenapp;
import 	android.provider.CalendarContract;
import java.util.GregorianCalendar;
import android.provider.CalendarContract.Events;
import android.content.Intent;

/**
 * @author Joseph Podnar
 */
public class NewEvent {

    //This data member holds the intent that this data structure is abstracting
    Intent calIntent;

    //Default constructor for a new event
    public NewEvent() {
        calIntent = new Intent(Intent.ACTION_INSERT);
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(Events.EVENT_LOCATION, "Garden");
    }


    //Set the date of the event
    public boolean setDate(int month, int day, int year) {
        //check that month is a valid value
        if (month < 0 || month > 11) {
            return false;
        }

        //check if day is between 1 and 31
        if (day > 31 || day <= 0) {
            return false;
        }

        //check if year is in the #### format
        if ( year < 1000 || (year / 9999) != 0) {
            return false;
        }

        //Month is -1 because for some reason month starts at 0, where all other values start logically.
        GregorianCalendar calDate = new GregorianCalendar(year, (month - 1), day);

        //Currently events will always be "all day"
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        return true;
    }

    //Set the event's name
    public boolean setEventName(String eventName) {

        //Check that the string passed is valid
        if (null == eventName || eventName.equals("") ) {
            return false;
        }

        calIntent.putExtra(Events.TITLE, eventName);

        return true;
    }

    //Set the description of the event
    public boolean setEventDescription(String eventDescription) {

        //Check that the string passed is valid
        if (null == eventName || eventName.equals("") ) {
            return false;
        }

        calIntent.putExtra(Events.DESCRIPTION, eventDescription);

        return true;
    }

    //Return the completed event
    public Intent getEventIntent(){
        return calIntent;
    }

}
