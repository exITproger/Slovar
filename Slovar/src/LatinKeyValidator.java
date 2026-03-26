import java.util.regex.Pattern;

public class LatinKeyValidator implements KeyValidator {
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z]{4}$");

    @Override
    public boolean isValid(String key) {
        return key != null && PATTERN.matcher(key).matches();
    }

    @Override
    public String getValidationRuleDescription() {
        return "ключ должен содержать ровно 4 латинских буквы (A-Z, a-z)";
    }
}