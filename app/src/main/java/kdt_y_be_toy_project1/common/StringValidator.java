package kdt_y_be_toy_project1.common;

public class StringValidator {

    private StringValidator() {}

    public static String requireLargeThan(String target, int length) {
        if (target.length() <= length) {
            throw new IllegalArgumentException(target + " must be large than " + length);
        }

        return target;
    }

    public static String requireNotBlank(String target) {
        if (target.isBlank()) {
            throw new IllegalArgumentException(target + " must be blank ");
        }

        return target;
    }
}
