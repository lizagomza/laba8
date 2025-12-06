package org.bsuir.gr274051.gomza.part8;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import com.example.stringlist.LoggerUtil;

/**
 * Main class demonstrating all required operations.
 */
public class Main {

    public static void main(String[] args) {
        try {
            LoggerUtil.log("=== Инициализация кастомного списка с емкостью 5 ===");
            CustomStringList list = new CustomStringList(5);

            // 1. Добавление и удаление объектов.
            System.out.println("1) Добавление элементов...");
            list.add("apple");
            list.add("banana");
            list.add("cherry");
            list.add("banana");
            list.add("date");
            list.add("elderberry"); // при добавлении шестого элемента должен удалиться первый (apple)
            printList(list);

            System.out.println("Удаляем элемент 'cherry'...");
            list.remove("cherry");
            printList(list);

            // 2. Поиск одинаковых элементов с подсчетом совпадений
            System.out.println("2) Поиск одинаковых элементов:");
            Map<String, Integer> duplicates = Operations.findDuplicates(list);
            duplicates.forEach((k,v) -> System.out.println(k + " -> " + v));

            // 3. Выгрузка в xml-файл.
            System.out.println("3) Экспорт в XML (out.xml)...");
            Operations.exportToXml(list, Paths.get("out.xml"));
            System.out.println("Файл out.xml создан в рабочей директории.");

            // 4. Реверс всех строк
            System.out.println("4) Реверс всех строк:");
            Operations.reverseAllStrings(list);
            printList(list);

            // 5. Статистика по всем символам
            System.out.println("5) Статистика по символам:");
            Map<Character, Integer> charStats = Operations.charStatistics(list);
            charStats.entrySet().stream()
                     .sorted(Map.Entry.comparingByKey())
                     .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));

            // 6. Поиск подстроки в строках коллекции
            System.out.println("6) Поиск подстроки 'an' в коллекции:");
            Map<Integer, List<Integer>> found = Operations.findSubstring(list, "an");
            if (found.isEmpty()) System.out.println("Ничего не найдено.");
            else {
                for (Map.Entry<Integer, List<Integer>> e : found.entrySet()) {
                    System.out.println("Строка индекс " + e.getKey() + ": позиции " + e.getValue());
                }
            }

            // 7. Инициализация листа по текстовому файлу и вывод содержимого
            System.out.println("7) Инициализация из файла sample_input.txt (если файл есть)...");
            Path sample = Paths.get("sample_input.txt");
            if (!Files.exists(sample)) {
                // создадим примерный файл
                Files.write(sample, Arrays.asList("one","two","three","banana","another banana"), StandardOpenOption.CREATE);
                System.out.println("Создан примерный файл sample_input.txt");
            }
            CustomStringList fromFile = Operations.loadFromTextFile(sample, 10);
            System.out.println("Содержимое загруженного списка:");
            printList(fromFile);

            // 8. Расширить функциональность класса ArrayList методом compareInnerObjects
            System.out.println("8) compareInnerObjects(0,1) в списке fromFile:");
            int cmp = fromFile.compareInnerObjects(0, 1);
            System.out.println("Результат сравнения: " + cmp + "  (0: равны, <0: первый меньше, >0: первый больше)");

            // 9. Посчитать длины строк и вывести результат в упорядоченном виде.
            System.out.println("9) Длины строк (упорядоченные):");
            Map<String, Integer> lengthsSorted = Operations.sortedLengths(list);
            lengthsSorted.forEach((k,v)-> System.out.println(k + " -> " + v));

            // 10. Поведение как статическая размерность закреплено в CustomStringList
            System.out.println("10) Тест поведения статической размерности (cap=3):");
            CustomStringList capList = new CustomStringList(3);
            capList.add("A"); capList.add("B"); capList.add("C");
            printList(capList);
            System.out.println("Добавляем D (ожидаем удаление A):");
            capList.add("D");
            printList(capList);

            System.out.println("--- Все методы выполнены. ---");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printList(List<String> list) {
        System.out.println("[" + list.stream().collect(Collectors.joining(", ")) + "]");
    }
}

/**
 * CustomStringList расширяет ArrayList<String> и добавляет:
 * - поле capacity — поведение статической размерности
 * - метод compareInnerObjects(int, int)
 */


/**
 * Операции над коллекцией.
 */


/*
 * build.xml (Ant)
 * Сохраните отдельным файлом build.xml рядом с src/ директорией если вы используете структуру каталогов.
 * Для простоты ниже приведен простой build.xml, который компилирует и создаёт jar с манифестом.
 */

/*
<?xml version="1.0" encoding="UTF-8"?>
<project name="JavaStringListProject" default="jar" basedir=".">
    <property name="src.dir" value="src"/>
    <property name="build.dir" value="build"/>
    <property name="classes.dir" value="${build.dir}/classes"/>
    <property name="jar.dir" value="${build.dir}/jar"/>

    <target name="clean">
        <delete dir="${build.dir}"/>
    </target>

    <target name="compile">
        <mkdir dir="${classes.dir}"/>
        <javac srcdir="${src.dir}" destdir="${classes.dir}" includeantruntime="false"/>
    </target>

    <target name="jar" depends="compile">
        <mkdir dir="${jar.dir}"/>
        <jar destfile="${jar.dir}/JavaStringListProject.jar" basedir="${classes.dir}">
            <manifest>
                <attribute name="Main-Class" value="com.example.stringlist.Main"/>
            </manifest>
        </jar>
        <echo message="Jar создан: ${jar.dir}/JavaStringListProject.jar"/>
    </target>

    <target name="run" depends="jar">
        <java jar="${jar.dir}/JavaStringListProject.jar" fork="true" />
    </target>
</project>
*/

// Конец файла
