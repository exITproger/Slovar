public interface ValueValidator {
    boolean isValid(String key);
    String getValidationRuleDescription();
}