import java.util.*;

void main() {
    String latinFile = "dictionary_latin.txt";
    String digitsFile = "dictionary_digits.txt";

    Dictionary latinDict = DictionaryFactory.createLatinDictionary();
    Dictionary digitsDict = DictionaryFactory.createDigitsDictionary();
    DictionaryManager manager = new DictionaryManager(latinDict, digitsDict);
    Scanner scanner = new Scanner(System.in);

    try {
        manager.loadAll(latinFile, digitsFile);
        IO.println("Словари загружены успешно");
    } catch (DictionaryException e) {
        IO.println("Ошибка загрузки: " + e.getMessage());
        IO.println("Будут созданы новые пустые словари");
    }

    while (true) {
        IO.println();
        IO.println("ГЛАВНОЕ МЕНЮ");
        IO.println("1. Просмотр содержимого словарей");
        IO.println("2. Работа со словарём");
        IO.println("0. Выход");
        IO.print("Выберите действие: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                IO.println();
                IO.println("СОДЕРЖИМОЕ СЛОВАРЕЙ");
                IO.println();

                IO.println("ЛАТИНСКИЙ СЛОВАРЬ (гипотетический язык)");
                IO.println("Правило: " + manager.getLatinKeyRule());
                IO.println("Значение (перевод на русский): любой текст");
                IO.println();

                List<Entry> latinEntries = manager.getAllLatinEntries();
                if (latinEntries.isEmpty()) {
                    IO.println("пусто");
                } else {
                    for (Entry entry : latinEntries) {
                        IO.println(entry);
                    }
                }

                IO.println();
                IO.println("ЦИФРОВОЙ СЛОВАРЬ (гипотетический язык)");
                IO.println("Правило: " + manager.getDigitsKeyRule());
                IO.println("Значение (перевод на русский): любой текст");
                IO.println();

                List<Entry> digitsEntries = manager.getAllDigitsEntries();
                if (digitsEntries.isEmpty()) {
                    IO.println("пусто");
                } else {
                    for (Entry entry : digitsEntries) {
                        IO.println(entry);
                    }
                }

                IO.println();
                IO.print("Нажмите Enter для продолжения...");
                scanner.nextLine();
                break;

            case "2":
                while (true) {
                    IO.println();
                    IO.println("ВЫБОР СЛОВАРЯ");
                    IO.println("1. Латинский словарь (ключ: 4 лат. буквы)");
                    IO.println("2. Цифровой словарь (ключ: 5 цифр)");
                    IO.println("0. Назад");
                    IO.print("Ваш выбор: ");

                    String dictChoice = scanner.nextLine().trim();
                    Dictionary dict = null;
                    String dictName = "";
                    String keyRule = "";

                    switch (dictChoice) {
                        case "1":
                            dict = manager.getLatinDictionary();
                            dictName = "ЛАТИНСКИЙ";
                            keyRule = manager.getLatinKeyRule();
                            break;
                        case "2":
                            dict = manager.getDigitsDictionary();
                            dictName = "ЦИФРОВОЙ";
                            keyRule = manager.getDigitsKeyRule();
                            break;
                        case "0":
                            break;
                        default:
                            IO.println("Неверный выбор.");
                            continue;
                    }

                    if (dictChoice.equals("0")) break;

                    while (true) {
                        IO.println();
                        IO.println("РАБОТА СО СЛОВАРЁМ: " + dictName);
                        IO.println("Правило для ключа: " + keyRule);
                        IO.println("Значение (перевод): любой текст");
                        IO.println();
                        IO.println("1. Добавить запись");
                        IO.println("2. Удалить запись");
                        IO.println("3. Найти запись");
                        IO.println("0. Назад");
                        IO.print("Выберите действие: ");

                        String opChoice = scanner.nextLine().trim();

                        switch (opChoice) {
                            case "1":
                                IO.println();
                                IO.println("ДОБАВЛЕНИЕ ЗАПИСИ");
                                IO.print("Введите ключ (слово на гипотетическом языке): ");
                                String key = scanner.nextLine().trim();
                                IO.print("Введите перевод на русский: ");
                                String value = scanner.nextLine().trim();

                                try {
                                    dict.addEntry(key, value);
                                    IO.println("Запись успешно добавлена");
                                } catch (DictionaryException e) {
                                    IO.println("Ошибка: " + e.getMessage());
                                }

                                IO.print("Нажмите Enter для продолжения...");
                                scanner.nextLine();
                                break;

                            case "2":
                                IO.println();
                                IO.println("УДАЛЕНИЕ ЗАПИСИ");
                                IO.print("Введите ключ для удаления: ");
                                String delKey = scanner.nextLine().trim();

                                try {
                                    dict.deleteByKey(delKey);
                                    IO.println("Запись успешно удалена");
                                } catch (DictionaryException e) {
                                    IO.println("Ошибка: " + e.getMessage());
                                }

                                IO.print("Нажмите Enter для продолжения...");
                                scanner.nextLine();
                                break;

                            case "3":
                                IO.println();
                                IO.println("ПОИСК ЗАПИСИ");
                                IO.print("Введите ключ для поиска: ");
                                String searchKey = scanner.nextLine().trim();

                                Optional<Entry> result = dict.findByKey(searchKey);
                                if (result.isPresent()) {
                                    IO.println("Найдено: " + result.get());
                                } else {
                                    IO.println("Запись с ключом '" + searchKey + "' не найдена");
                                }

                                IO.print("Нажмите Enter для продолжения...");
                                scanner.nextLine();
                                break;

                            case "0":
                                break;

                            default:
                                IO.println("Неверный выбор.");
                                continue;
                        }

                        if (opChoice.equals("0")) break;
                    }
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