public class DictionaryFactory {
    public static Dictionary createLatinDictionary() {
        return new FileDictionary(new LatinKeyValidator());
    }

    public static Dictionary createDigitsDictionary() {
        return new FileDictionary(new DigitsKeyValidator());
    }
}
