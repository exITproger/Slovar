import java.util.*;

void main() {
    Scanner scanner = new Scanner(System.in);
    DictionaryManager manager = new DictionaryManager();

    boolean latinLoaded = false;
    boolean digitsLoaded = false;

    while (true) {
        IO.println();
        IO.println("ГЛАВНОЕ МЕНЮ");
        IO.println("1. Латинский словарь (ключ: 4 лат. буквы)");
        IO.println("2. Цифровой словарь (ключ: 5 цифр)");
        IO.println("0. Выход");
        IO.print("Выберите словарь: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                if (!latinLoaded) {
                    latinLoaded = loadDictionary(scanner, manager.getLatinDictionary(), "латинского", "dictionary_latin.txt");
                }
                if (latinLoaded) {
                    workWithDictionary(scanner, manager.getLatinDictionary(), "ЛАТИНСКИЙ", manager.getLatinKeyRule());
                }
                break;

            case "2":
                if (!digitsLoaded) {
                    digitsLoaded = loadDictionary(scanner, manager.getDigitsDictionary(), "цифрового", "dictionary_digits.txt");
                }
                if (digitsLoaded) {
                    workWithDictionary(scanner, manager.getDigitsDictionary(), "ЦИФРОВОЙ", manager.getDigitsKeyRule());
                }
                break;

            case "0":
                IO.println("До свидания!");
                return;

            default:
                IO.println("Неверный выбор. Попробуйте снова.");
        }
    }
}

boolean loadDictionary(Scanner scanner, Dictionary dict, String dictType, String defaultPath) {
    while (true) {
        IO.println();
        IO.println("ЗАГРУЗКА " + dictType.toUpperCase() + " СЛОВАРЯ");
        IO.print("Введите путь к файлу (или 0 для выхода): ");
        String filePath = scanner.nextLine().trim();

        if (filePath.equals("0")) {
            IO.println("Возврат в главное меню");
            return false;
        }

        if (filePath.isEmpty()) {
            filePath = defaultPath;
            IO.println("Используем путь по умолчанию: " + filePath);
        }

        try {
            dict.loadFromFile(filePath);
            IO.println("Словарь успешно загружен из файла: " + filePath);
            return true;
        } catch (DictionaryException e) {
            IO.println("ОШИБКА: " + e.getMessage());
            IO.println("Файл не найден или повреждён. Попробуйте снова.");
        }
    }
}

void workWithDictionary(Scanner scanner, Dictionary dict, String dictName, String keyRule) {
    while (true) {
        IO.println();
        IO.println("РАБОТА СО СЛОВАРЁМ: " + dictName);
        IO.println("Правило для ключа: " + keyRule);
        IO.println("Значение (перевод): любой текст");
        IO.println();
        IO.println("1. Просмотреть содержимое");
        IO.println("2. Добавить запись");
        IO.println("3. Удалить запись");
        IO.println("4. Найти запись");
        IO.println("0. Назад");
        IO.print("Выберите действие: ");

        String opChoice = scanner.nextLine().trim();

        switch (opChoice) {
            case "1":
                viewDictionary(dict, dictName, keyRule);
                break;

            case "2":
                addEntry(scanner, dict);
                break;

            case "3":
                deleteEntry(scanner, dict);
                break;

            case "4":
                findEntry(scanner, dict);
                break;

            case "0":
                return;

            default:
                IO.println("Неверный выбор.");
        }
    }
}

void viewDictionary(Dictionary dict, String dictName, String keyRule) {
    IO.println();
    IO.println("СОДЕРЖИМОЕ " + dictName + " СЛОВАРЯ");
    IO.println("Правило: " + keyRule);
    IO.println();

    List<Entry> entries = dict.getAllEntries();
    if (entries.isEmpty()) {
        IO.println("Словарь пуст");
    } else {
        for (Entry entry : entries) {
            IO.println(entry);
        }
    }

    IO.print("\nНажмите Enter для продолжения...");
    new Scanner(System.in).nextLine();
}

void addEntry(Scanner scanner, Dictionary dict) {
    IO.println();
    IO.println("ДОБАВЛЕНИЕ ЗАПИСИ");
    IO.print("Введите ключ: ");
    String key = scanner.nextLine().trim();
    IO.print("Введите значение (перевод): ");
    String value = scanner.nextLine().trim();

    try {
        dict.addEntry(key, value);
        IO.println("✓ Запись успешно добавлена");
    } catch (ValidationException e) {
        IO.println("✗ Ошибка валидации: " + e.getMessage());
    } catch (DictionaryException e) {
        IO.println("✗ Ошибка: " + e.getMessage());
    }

    IO.print("Нажмите Enter для продолжения...");
    scanner.nextLine();
}

void deleteEntry(Scanner scanner, Dictionary dict) {
    IO.println();
    IO.println("УДАЛЕНИЕ ЗАПИСИ");
    IO.print("Введите ключ для удаления: ");
    String key = scanner.nextLine().trim();

    try {
        dict.deleteByKey(key);
        IO.println("✓ Запись успешно удалена");
    } catch (DictionaryException e) {
        IO.println("✗ Ошибка: " + e.getMessage());
    }

    IO.print("Нажмите Enter для продолжения...");
    scanner.nextLine();
}

void findEntry(Scanner scanner, Dictionary dict) {
    IO.println();
    IO.println("ПОИСК ЗАПИСИ");
    IO.print("Введите ключ для поиска: ");
    String key = scanner.nextLine().trim();

    Optional<Entry> result = dict.findByKey(key);
    if (result.isPresent()) {
        IO.println("✓ Найдено: " + result.get());
    } else {
        IO.println("✗ Запись с ключом '" + key + "' не найдена");
    }

    IO.print("Нажмите Enter для продолжения...");
    scanner.nextLine();
}