package org.example.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadUtils {

    public static String readLine() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static Integer readAndValidateAge() {
        try {
            String ageString = ReadUtils.readLine();
            if (ageString == null || ageString.isBlank()) {
                System.out.println("Возраст не число, введите число");
                return null;
            }
            return Integer.parseInt(ageString);
        } catch (NumberFormatException ex) {
            System.out.println("Возраст не число, введите число");
        }
        return null;
    }

    public static Long readAndValidateLong() {
        try {
            String line = ReadUtils.readLine();
            if (line == null || line.isBlank()) {
                System.out.println("Введено не число, введите число");
                return null;
            }
            return Long.parseLong(line);
        } catch (NumberFormatException ex) {
            System.out.println("Введено не число, введите число");
        }
        return null;
    }
}
