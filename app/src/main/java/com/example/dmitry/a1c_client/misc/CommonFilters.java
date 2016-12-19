package com.example.dmitry.a1c_client.misc;

/**
 * Created by Admin on 19.12.2016.
 */

public class CommonFilters {
    public static Boolean isValidBarCode(String barCode){
        try {
            Long.parseLong(barCode);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public static Boolean isValidNumber(String number){
        try {
            if(Integer.parseInt(number) > 0){
                return true;
            }else {
                return false;
            }
        }catch (NumberFormatException e){
            return false;
        }
    }
}
