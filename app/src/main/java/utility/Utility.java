package utility;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utility {

    public static String getCurrentDate(){

        String currentDate;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyy", Locale.getDefault());
        currentDate = simpleDateFormat.format(calendar.getTime());

        return currentDate;

    }

    public static TextDrawable getDrawableLetter(String itemTitle){
        ColorGenerator generator = ColorGenerator.MATERIAL;

        // Get the first letter from the list item
        String firstLetter = String.valueOf(itemTitle.charAt(0));

        //  Create a new TextDrawable for our image's background and return the TextDrawable
        return TextDrawable.builder().buildRound(firstLetter, generator.getRandomColor());
    }
}
