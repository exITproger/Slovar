import java.util.regex.Pattern;

public class DigitsKeyValidator implements KeyValidator {
    private static final Pattern PATTERN = Pattern.compile("^\\d{5}$");

    @Override
    public boolean isValid(String key) {
        return key != null && PATTERN.matcher(key).matches();
    }

    @Override
    public String getValidationRuleDescription() {
        return "ключ должен содержать ровно 5 цифр (0-9)";
    }
}