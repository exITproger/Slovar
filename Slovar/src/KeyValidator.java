public interface KeyValidator {
    boolean isValid(String key);
    String getValidationRuleDescription();
}