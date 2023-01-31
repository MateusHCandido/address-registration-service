package com.mtzz.api.util;

import com.mtzz.api.application.util.DataFormat;
import static org.junit.Assert.*;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class DataFormatTest extends DataFormat
{
    @Test
    public void test_number_format(){
        var cep = "55555555";
        var cpf = "123.456.789-10";
        var number_phone = "+55 (99) 9999-9999";

        cep = number_format(cep);
        cpf = number_format(cpf);
        number_phone = number_format(number_phone);

        assertTrue(cep.matches("[0-9]*"));
        assertTrue(cpf.matches("[0-9]*"));
        assertTrue(number_phone.matches("[0-9]*"));
    }

    @Test
    public void test_name_format()
    {
        var name = "Ragnar Lothbrook";
        var country = "Denmark";
        var street = "Private Drive ";

        name = name_format(name);
        country = name_format(country);
        street = name_format(street);

        assertTrue(name.matches("[A-Z, ]+"));
        assertTrue(country.matches("[A-Z]+"));
        assertTrue(street.matches("[A-Z, ]+"));
    }

    @Test
    public void test_date_format() throws ParseException {
        String date = "1999-10-17";
        var simple_df = new SimpleDateFormat("yyyy-MM-dd");
        var converted_date = simple_df.parse(date);
        System.out.println(converted_date);
    }
}



