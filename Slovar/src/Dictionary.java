import java.util.List;
import java.util.Optional;
/// интерфейс словаря
interface Dictionary {
    void loadFromFile(String filePath);
    void addEntry(String key, String value);
    void deleteByKey(String key);
    Optional<Entry> findByKey(String key);
    List<Entry> getAllEntries();
    boolean isValidKey(String value);
    String getKeyRuleDescription();
}
