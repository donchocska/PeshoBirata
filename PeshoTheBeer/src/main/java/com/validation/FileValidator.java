/*
 * Validator.java
 *
 * created at Mar 2, 2017 by d.balamdzhiev <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package com.validation;


public class FileValidator
{
    String valid = null;

    public String isValid(String val, int from, int to)
    {
        try
        {
                valid = Integer.toString(Integer.parseInt(val, from), to);
        }
        catch (NumberFormatException e)
        {
          //  e.printStackTrace();
            valid = null;
        }
       return valid;
    }

}
