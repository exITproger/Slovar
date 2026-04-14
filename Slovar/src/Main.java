import java.util.*;

void main() {
    Scanner scanner = new Scanner(System.in);
    DictionaryManager manager = new DictionaryManager();
    String commonFile = "dictionary.txt";

    while (true) {
        IO.println();
        IO.println("ГЛАВНОЕ МЕНЮ");
        IO.println("1. Латинский словарь (ключ: 4 лат. буквы)");
        IO.println("2. Цифровой словарь (ключ: 5 цифр)");
        IO.println("3. Просмотреть всё содержимое файла");
        IO.println("0. Выход");
        IO.print("Выберите действие: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                String latinPath = getFilePath(scanner, "латинского");
                if (latinPath != null) {
                    loadAndWork(scanner, manager.getLatinDictionary(), "ЛАТИНСКИЙ", manager.getLatinKeyRule(), latinPath);
                }
                break;

            case "2":
                String digitsPath = getFilePath(scanner, "цифрового");
                if (digitsPath != null) {
                    loadAndWork(scanner, manager.getDigitsDictionary(), "ЦИФРОВОЙ", manager.getDigitsKeyRule(), digitsPath);
                }
                break;

            case "3":
                viewAllFile(scanner, commonFile);
                break;

            case "0":
                IO.println("До свидания!");
                return;

            default:
                IO.println("Неверный выбор. Попробуйте снова.");
        }
    }
}

String getFilePath(Scanner scanner, String dictType) {
    while (true) {
        IO.println();
        IO.println("РАБОТА С " + dictType.toUpperCase() + " СЛОВАРЁМ");
        IO.print("Введите путь к файлу (или 0 для выхода): ");
        String filePath = scanner.nextLine().trim();

        if (filePath.equals("0")) {
            IO.println("Возврат в главное меню");
            return null;
        }

        if (filePath.isEmpty()) {
            filePath = "dictionary.txt";
            IO.println("Используем путь по умолчанию: " + filePath);
        }

        return filePath;
    }
}

void loadAndWork(Scanner scanner, Dictionary dict, String dictName, String keyRule, String filePath) {
    try {
        dict.loadFromFile(filePath);
        IO.println("Словарь успешно загружен из файла: " + filePath);
        workWithDictionary(scanner, dict, dictName, keyRule, filePath);
    } catch (DictionaryException e) {
        IO.println("ОШИБКА: " + e.getMessage());
        IO.println("Файл не найден или повреждён. Создаётся новый пустой словарь.");
        workWithDictionary(scanner, dict, dictName, keyRule, filePath);
    }
}

void workWithDictionary(Scanner scanner, Dictionary dict, String dictName, String keyRule, String filePath) {
    while (true) {
        IO.println();
        IO.println("РАБОТА СО СЛОВАРЁМ: " + dictName);
        IO.println("Файл: " + filePath);
        IO.println("Правило для ключа: " + keyRule);
        IO.println();
        IO.println("1. Просмотреть содержимое словаря");
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

void viewAllFile(Scanner scanner, String filePath) {
    IO.println();
    IO.println("ВСЁ СОДЕРЖИМОЕ ФАЙЛА: " + filePath);
    IO.println();

    java.nio.file.Path path = java.nio.file.Paths.get(filePath);
    if (!java.nio.file.Files.exists(path)) {
        IO.println("Файл не существует");
        IO.print("\nНажмите Enter для продолжения...");
        scanner.nextLine();
        return;
    }

    try (BufferedReader reader = java.nio.file.Files.newBufferedReader(path)) {
        String line;
        int lineNum = 0;
        while ((line = reader.readLine()) != null) {
            lineNum++;
            line = line.trim();
            if (!line.isEmpty()) {
                IO.println(lineNum + ". " + line);
            }
        }
        if (lineNum == 0) {
            IO.println("Файл пуст");
        }
    } catch (IOException e) {
        IO.println("Ошибка чтения файла: " + e.getMessage());
    }

    IO.print("\nНажмите Enter для продолжения...");
    scanner.nextLine();
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
        IO.println("Запись успешно добавлена");
    } catch (ValidationException e) {
        IO.println("Ошибка: " + e.getMessage());
    } catch (DictionaryException e) {
        IO.println("Ошибка: " + e.getMessage());
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
        IO.println("Запись успешно удалена");
    } catch (DictionaryException e) {
        IO.println("Ошибка: " + e.getMessage());
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
        IO.println("Найдено: " + result.get());
    } else {
        IO.println("Запись с ключом '" + key + "' не найдена");
    }

    IO.print("Нажмите Enter для продолжения...");
    scanner.nextLine();
}