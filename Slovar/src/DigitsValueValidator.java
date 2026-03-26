import java.util.regex.Pattern;
public class DigitsValueValidator implements ValueValidator {
    private static final Pattern PATTERN = Pattern.compile("^\\d{5}$");

    @Override
    public boolean isValid(String value) {
        return value != null && PATTERN.matcher(value).matches();
    }

    @Override
    public String getValidationRuleDescription() {
        return "значение должно содержать ровно 5 цифр (0-9)";
    }
}
