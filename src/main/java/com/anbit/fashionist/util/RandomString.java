package com.anbit.fashionist.util;

import java.util.Random;

public class RandomString {
    public String generate(String prefix){
        // create a string of uppercase and lowercase characters and numbers
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // create random string builder
        StringBuilder orderId = new StringBuilder(prefix + "-");

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 12;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            orderId.append(randomChar);
        }

        return orderId.toString();
    }
}
