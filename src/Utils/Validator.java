package Utils;

import Exceptions.InvalidInputException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Centralized input / business-rule validation.
 * Every method throws InvalidInputException with a clear message
 * so callers can catch it and ask the user to re-enter (BR29).
 */
public class Validator {

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    // BR2: text fields must not be empty
    public static String requireNonEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " must not be empty!");
        }
        return value.trim();
    }

    public static int parseInt(String value, String fieldName) throws InvalidInputException {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid integer!");
        }
    }

    public static double parseDouble(String value, String fieldName) throws InvalidInputException {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid number!");
        }
    }

    // BR4: age between 16 and 45
    public static int validateAge(int age) throws InvalidInputException {
        if (age < 16 || age > 45) {
            throw new InvalidInputException("Age must be between 16 and 45!");
        }
        return age;
    }

    // BR5: shirt number between 1 and 99
    public static int validateShirtNumber(int n) throws InvalidInputException {
        if (n < 1 || n > 99) {
            throw new InvalidInputException("Shirt number must be between 1 and 99!");
        }
        return n;
    }

    // BR3: position must be one of the four allowed values
    public static String validatePosition(String pos) throws InvalidInputException {
        String p = requireNonEmpty(pos, "Position");
        if (p.equalsIgnoreCase("Goalkeeper") || p.equalsIgnoreCase("Defender")
                || p.equalsIgnoreCase("Midfielder") || p.equalsIgnoreCase("Forward")) {
            return Character.toUpperCase(p.charAt(0)) + p.substring(1).toLowerCase();
        }
        throw new InvalidInputException("Position must be Goalkeeper, Defender, Midfielder, or Forward!");
    }

    // BR11: base salary must be greater than 0
    public static double validateSalary(double s) throws InvalidInputException {
        if (s <= 0) {
            throw new InvalidInputException("Base salary must be greater than 0!");
        }
        return s;
    }

    // BR12: date must be a valid calendar date in dd/MM/yyyy
    public static LocalDate validateDate(String s) throws InvalidInputException {
        try {
            return LocalDate.parse(s.trim(), DATE_FMT);
        } catch (Exception e) {
            throw new InvalidInputException("Date must be a valid calendar date in dd/MM/yyyy format!");
        }
    }

    // BR13: month 1..12, year 2000..2100
    public static int validateMonth(int m) throws InvalidInputException {
        if (m < 1 || m > 12) {
            throw new InvalidInputException("Month must be between 1 and 12!");
        }
        return m;
    }

    public static int validateYear(int y) throws InvalidInputException {
        if (y < 2000 || y > 2100) {
            throw new InvalidInputException("Year must be between 2000 and 2100!");
        }
        return y;
    }

    // BR14: match type must be Friendly, League, or Cup
    public static String validateMatchType(String t) throws InvalidInputException {
        String x = requireNonEmpty(t, "Match type");
        if (x.equalsIgnoreCase("Friendly") || x.equalsIgnoreCase("League") || x.equalsIgnoreCase("Cup")) {
            return Character.toUpperCase(x.charAt(0)) + x.substring(1).toLowerCase();
        }
        throw new InvalidInputException("Match type must be Friendly, League, or Cup!");
    }

    // BR19: stats must not be negative
    public static int validateNonNegative(int v, String fieldName) throws InvalidInputException {
        if (v < 0) {
            throw new InvalidInputException(fieldName + " must not be negative!");
        }
        return v;
    }

    // BR20: minutes played between 0 and 120
    public static int validateMinutes(int v) throws InvalidInputException {
        if (v < 0 || v > 120) {
            throw new InvalidInputException("Minutes played must be between 0 and 120!");
        }
        return v;
    }
}
