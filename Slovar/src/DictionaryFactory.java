public class DictionaryFactory {
    public static Dictionary createLatinDictionary() {
        return new FileDictionary(new LatinValueValidator());
    }

    public static Dictionary createDigitsDictionary() {
        return new FileDictionary(new DigitsValueValidator());
    }
}
