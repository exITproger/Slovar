import java.util.regex.Pattern;

public class LatinValueValidator implements ValueValidator {
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z]{4}$");

    @Override
    public boolean isValid(String value) {
        return value != null && PATTERN.matcher(value).matches();
    }

    @Override
    public String getValidationRuleDescription() {
        return "значение должно содержать ровно 4 латинских буквы (A-Z, a-z)";
    }
}

