package preprocessing;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ITE_Syria
 */

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Validatore {

    // List of all date formats that we want to parse.
    // Add your own format here.
    private static List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>() {
        {
            add(new SimpleDateFormat("M/dd/yyyy"));
            //   add(new SimpleDateFormat("yyyy"));
            add(new SimpleDateFormat("dd/M/yyyy"));
            add(new SimpleDateFormat("dd.M.yyyy"));
            add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.M.yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.MMM.yyyy"));
            add(new SimpleDateFormat("dd-MMM-yyyy"));
        }
    };

    /**
     * Convert String with various formats into java.util.Date
     *
     * @param input Date as a string
     * @return java.util.Date object if input string is parsed successfully else
     * returns null
     */
    public static Date convertToDate(String input) {
        Date date = null;
        if (null == input) {
            return null;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) {
                //Shhh.. try other formats
            }
            if (date != null) {
                break;
            }
        }

        return date;
    }

    public static boolean DateValidator(String input) {
        Date date = null;
        if (null == input) {
            return false;
        }
        for (SimpleDateFormat format : dateFormats) {
            try {
                format.setLenient(false);
                date = format.parse(input);
            } catch (ParseException e) {
                //Shhh.. try other formats
            }
            if (date != null) {
                return true;
            }
        }

        return false;
             /*    DateValidator v = DateValidator.getInstance() ;
                        return v.isValid(input);*/

    }

    public static boolean emailValidator(String email) {

        // Get an EmailValidator
        //   DateValidator v = v.isValid(email)
        EmailValidator validator = EmailValidator.getInstance();
        // Validate specified String containing an email address
        if (!validator.isValid(email)) {
            return false;
        }
        return true;
    }

    public static boolean UrlValidator(String url) {

        // Get an EmailValidator
        //   DateValidator v = v.isValid(email)
        UrlValidator validator = UrlValidator.getInstance();
        // Validate specified String containing an email address
        if (!validator.isValid(url)) {
            return false;
        }
        return true;
    }

}
