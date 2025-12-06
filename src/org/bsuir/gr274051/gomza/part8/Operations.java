package org.bsuir.gr274051.gomza.part8;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

class Operations {

    // 2.
    public static Map<String, Integer> findDuplicates(Collection<String> list) {
        Map<String, Integer> counts = new HashMap<>();
        for (String s : list) {
            counts.put(s, counts.getOrDefault(s, 0) + 1);
        }
        // keep only those with count >1
        return counts.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    // 3. экспорт в xml
    public static void exportToXml(Collection<String> list, Path out) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("strings");
        doc.appendChild(rootElement);

        int idx = 0;
        for (String s : list) {
            Element item = doc.createElement("string");
            item.setAttribute("index", String.valueOf(idx++));
            item.appendChild(doc.createTextNode(s == null ? "" : s));
            rootElement.appendChild(item);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        try (OutputStream os = Files.newOutputStream(out, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            StreamResult result = new StreamResult(os);
            transformer.transform(source, result);
        }
    }

    // 4. реверс всех строк
    public static void reverseAllStrings(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s != null) {
                String rev = new StringBuilder(s).reverse().toString();
                list.set(i, rev);
            }
        }
    }

    // 5. статистика по символам во всех строках
    public static Map<Character, Integer> charStatistics(Collection<String> list) {
        Map<Character, Integer> stats = new HashMap<>();
        for (String s : list) {
            if (s == null) continue;
            for (char c : s.toCharArray()) {
                stats.put(c, stats.getOrDefault(c, 0) + 1);
            }
        }
        return stats;
    }

    // 6. Поиск подстроки: возвращает map индекс строки -> список позиций в строке
    public static Map<Integer, List<Integer>> findSubstring(List<String> list, String substr) {
        Map<Integer, List<Integer>> result = new HashMap<>();
        if (substr == null || substr.isEmpty()) return result;
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s == null) continue;
            List<Integer> positions = new ArrayList<>();
            int from = 0;
            while (true) {
                int idx = s.indexOf(substr, from);
                if (idx == -1) break;
                positions.add(idx);
                from = idx + 1; // allow overlapping occurrences
            }
            if (!positions.isEmpty()) result.put(i, positions);
        }
        return result;
    }

    // 7. Инициализация листа по текстовому файлу
    public static CustomStringList loadFromTextFile(Path file, int capacity) throws IOException {
        List<String> lines = Files.readAllLines(file);
        CustomStringList list = capacity > 0 ? new CustomStringList(capacity) : new CustomStringList();
        for (String line : lines) list.add(line);
        return list;
    }

    // 9. Посчитать длины и вывести упорядоченно (по строкам лексикографически)
    public static Map<String, Integer> sortedLengths(Collection<String> list) {
        return list.stream()
                .sorted(Comparator.nullsFirst(Comparator.naturalOrder()))
                .collect(Collectors.toMap(s -> s, s -> (s == null ? 0 : s.length()), (a,b) -> a, LinkedHashMap::new));
    }
}