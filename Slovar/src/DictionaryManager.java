import java.util.List;
import java.util.Optional;

public class DictionaryManager {
    private final Dictionary latinDictionary;
    private final Dictionary digitsDictionary;

    public DictionaryManager() {
        this.latinDictionary = DictionaryFactory.createLatinDictionary();
        this.digitsDictionary = DictionaryFactory.createDigitsDictionary();
    }

    public Dictionary getLatinDictionary() {
        return latinDictionary;
    }

    public Dictionary getDigitsDictionary() {
        return digitsDictionary;
    }

    public void loadAll(String latinFilePath, String digitsFilePath) {
        latinDictionary.loadFromFile(latinFilePath);
        digitsDictionary.loadFromFile(digitsFilePath);
    }

    public void addEntryToLatin(String key, String value) {
        latinDictionary.addEntry(key, value);
    }

    public void addEntryToDigits(String key, String value) {
        digitsDictionary.addEntry(key, value);
    }

    public void deleteFromLatin(String key) {
        latinDictionary.deleteByKey(key);
    }

    public void deleteFromDigits(String key) {
        digitsDictionary.deleteByKey(key);
    }

    public Optional<Entry> searchInLatin(String key) {
        return latinDictionary.findByKey(key);
    }

    public Optional<Entry> searchInDigits(String key) {
        return digitsDictionary.findByKey(key);
    }

    public List<Entry> getAllLatinEntries() {
        return latinDictionary.getAllEntries();
    }

    public List<Entry> getAllDigitsEntries() {
        return digitsDictionary.getAllEntries();
    }

    public String getLatinKeyRule() {
        return latinDictionary.getKeyRuleDescription();
    }

    public String getDigitsKeyRule() {
        return digitsDictionary.getKeyRuleDescription();
    }
}