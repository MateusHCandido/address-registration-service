package com.mtzz.api.application.util;


import com.mtzz.api.application.util.exception.NameFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormat
{
        public static String number_format(String number)
        {
                try
                {
                        return number.replaceAll("[^0-9]+", "");
                }
                catch (NullPointerException pointerException)
                {
                        throw new NumberFormatException("Unable to update a null number");
                }

        }

        public static String name_format(String name)
        {
                try
                {
                        name = name.toUpperCase();
                        name = name.replaceAll("[^A-Z]+", " ");
                }
                catch (NullPointerException pointerException)
                {
                        throw new NameFormatException("Unable to update a null name");
                }

                return name.trim().replaceAll("\\s+", " ");
        }

        public static Date date_format(String registered_date) throws ParseException
        {
                var simple_df = new SimpleDateFormat("yyyy-MM-dd");
                return simple_df.parse(registered_date);
        }
}
