package sample.logic;

public class PersonaException extends Exception {

    public static String BAD_AGE_LOWER = "Edad debe ser mayor a 0";
    public static String BAD_AGE_UPPER = "Edad no debe ser mayor a 120";
    public static String BAD_AGE = "Edad no lógica";

    public PersonaException(String message) {
        super(message);
    }
}
