package us.proj.contactsapp.objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static Boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        // Check if each character is a letter or a space
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }



    public static Boolean isValidPhoneNumber (String phoneNumber) {
        for (char ch : phoneNumber.toCharArray()) {
            if (ch >= '0' && ch <= '9' && phoneNumber.length() == 11 &&
                    (phoneNumber.startsWith("010") || phoneNumber.startsWith("011") || phoneNumber.startsWith("012") || phoneNumber.startsWith("015"))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDomainValid(String email, int dotSymbol, int atSymbol) { // @ -> atSymbol
        String stringAfterDot = email.substring(dotSymbol + 1);
        char firstCharAfterAt = email.charAt(atSymbol + 1);

        if (!(firstCharAfterAt >= 'a' && firstCharAfterAt <= 'z' || firstCharAfterAt >= 'A' && firstCharAfterAt <= 'Z')) { // !between a & z or A & Z
            return false;
        }

        for (char c : stringAfterDot.toCharArray()) {
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty() ) {
            System.out.println("Email can't be null or empty!"); // just for debugging.
            return false;
        }

        String loweredCaseEmail = email.toLowerCase();
        int atSymbol = loweredCaseEmail.indexOf('@'); // to get the index of the @ symbol in the email.
        int atSymbolCount = 0; // counter for @ symbols because the email address should contain only one @ symbol.
        int dotSymbol = loweredCaseEmail.lastIndexOf('.'); // gets the index of the last "." in the email seif.ashraf@gmail.com
        int spaceSymbol = loweredCaseEmail.indexOf(' '); // gets the space symbol because if there are any spaces in the email the method with return false.

        for (char c : loweredCaseEmail.toCharArray()) { // an array to increase the atSymbolCounter whenever it finds @ in the email.
            if (c == '@') {
                atSymbolCount++;
            }
        }

        if (atSymbol != -1 &&
                atSymbol != 0 &&
                atSymbolCount == 1 && // the @ must be only one in the email address.
                isDomainValid(email.toLowerCase(), dotSymbol, atSymbol) &&
                dotSymbol != -1 &&
                dotSymbol != 0 &&
                (loweredCaseEmail.length() > dotSymbol + 1) &&
                spaceSymbol == -1
        ) {
            return true;
        } else {
            return false;
        }
    }
}