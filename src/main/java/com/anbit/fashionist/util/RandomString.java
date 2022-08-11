package com.anbit.fashionist.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomString {
    // create a string of uppercase and lowercase characters and numbers
    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";

    public String generateVirtualAccount(int length){

        // combine all strings
        String virtualAccount = numbers;

        // create random string builder
        StringBuilder orderId = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(virtualAccount.length());

            // get character specified by index
            // from the string
            char randomChar = virtualAccount.charAt(index);

            // append the character to string builder
            orderId.append(randomChar);
        }

        return orderId.toString();
    }
}
