package acropollis.municipali.omega.common.utils.csv;

import java.util.List;
import java.util.function.Function;

public class CsvUtils {
    public static <T> String produce(List<String> header, List<T> data, Function<T, List<String>> extractor) {
        StringBuilder res = new StringBuilder();

        res.append(produceHeader(header));

        for (T dataRow : data) {
            res.append(produceRow(dataRow, extractor));
        }

        return res.toString();
    }

    private static String produceHeader(List<String> header) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < header.size() - 1; i++) {
            res.append(nonTerminatingElement(header.get(i)));
        }

        res.append(terminatingElement(header.get(header.size() - 1)));

        return res.toString();
    }

    private static <T> String produceRow(T dataRow, Function<T, List<String>> extractor) {
        StringBuilder res = new StringBuilder();

        List<String> dataRowElements = extractor.apply(dataRow);

        for (int i = 0; i < dataRowElements.size() - 1; i++) {
            res.append(nonTerminatingElement(dataRowElements.get(i)));
        }

        res.append(terminatingElement(dataRowElements.get(dataRowElements.size() - 1)));

        return res.toString();
    }

    private static String nonTerminatingElement(String e) {
        return e + ",";
    }

    private static String terminatingElement(String e) {
        return e + "\n\r";
    }
}
