import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileDictionary implements Dictionary {
    private final Map<String, Entry> storage = new HashMap<>();
    private final KeyValidator keyValidator;
    private String currentFilePath;

    public FileDictionary(KeyValidator keyValidator) {
        this.keyValidator = keyValidator;
    }

    @Override
    public void loadFromFile(String filePath) {
        this.currentFilePath = filePath;
        storage.clear();

        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(":", 2);
                if (parts.length != 2) continue;

                String key = parts[0].trim();
                String value = parts[1].trim();

                if (keyValidator.isValid(key)) {
                    storage.put(key, new Entry(key, value));
                }
            }
        } catch (IOException e) {
            throw new DictionaryException("Ошибка чтения файла: " + filePath, e);
        }
    }

    private void saveToFile() {
        if (currentFilePath == null) {
            throw new DictionaryException("Не указан файл для сохранения");
        }

        try {
            List<String> allLines = Files.readAllLines(Paths.get(currentFilePath));
            List<String> newLines = new ArrayList<>();

            for (String line : allLines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(":", 2);
                if (parts.length != 2) {
                    newLines.add(line);
                    continue;
                }

                String key = parts[0].trim();
                if (keyValidator.isValid(key) && storage.containsKey(key)) {
                    newLines.add(key + ":" + storage.get(key).getValue());
                } else if (!keyValidator.isValid(key)) {
                    newLines.add(line);
                }
            }

            for (Entry entry : storage.values()) {
                String entryLine = entry.getKey() + ":" + entry.getValue();
                if (!newLines.contains(entryLine)) {
                    newLines.add(entryLine);
                }
            }

            Files.write(Paths.get(currentFilePath), newLines,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new DictionaryException("Ошибка сохранения файла: " + currentFilePath, e);
        }
    }

    @Override
    public void addEntry(String key, String value) {
        if (key == null || value == null) {
            throw new ValidationException("Ключ и значение не могут быть null");
        }

        if (!keyValidator.isValid(key)) {
            throw new ValidationException("Ключ '" + key + "' не соответствует правилам: " +
                    keyValidator.getValidationRuleDescription());
        }

        if (value.isBlank()) {
            throw new ValidationException("Значение не может быть пустым");
        }

        storage.put(key, new Entry(key, value));
        saveToFile();
    }

    @Override
    public void deleteByKey(String key) {
        if (!storage.containsKey(key)) {
            throw new DictionaryException("Запись с ключом '" + key + "' не найдена");
        }
        storage.remove(key);
        saveToFile();
    }

    @Override
    public Optional<Entry> findByKey(String key) {
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public List<Entry> getAllEntries() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean isValidKey(String key) {
        return keyValidator.isValid(key);
    }

    @Override
    public String getKeyRuleDescription() {
        return keyValidator.getValidationRuleDescription();
    }
}