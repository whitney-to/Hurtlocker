import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DataProcess {
    private Integer errors = 0;
    private Map<String, Map<Double, Integer>> map = new LinkedHashMap<>();

    public String process(String data) {
        Pattern.compile(
                "(?i)name:(\\w+)*;price:(\\d\\.\\d+)*;type:(\\w+)*[;\\^%*!@]expiration:([0-9]+/[0-9]+/[0-9]+)*(?=##)")
                .matcher(data).results().forEach(item -> {
                    try {
                        map.putIfAbsent(fixName(item.group(1)), new HashMap<>());
                        Integer i = map.get(fixName(item.group(1))).containsKey(Double.parseDouble(item.group(2)))
                                ? map.get(fixName(item.group(1))).put(Double.parseDouble(item.group(2)),
                                        map.get(fixName(item.group(1))).get(Double.parseDouble(item.group(2))) + 1)
                                : map.get(fixName(item.group(1))).put(Double.parseDouble(item.group(2)), 1);
                    } catch (Exception e) {
                        errors++;
                    }
                });
        return getResult(map);
    }

    public String fixName(String name) {
        return name.matches("(?i)[milk]{4}") ? "Milk"
                : name.matches("(?i)[bread]{5}") ? "Bread"
                        : name.matches("(?i)[aples]{6}") ? "Apples"
                                : name.matches("(?i)[c(o|0){2}kies]{7}") ? "Cookies" : null;
    }

    public String getResult(Map<String, Map<Double, Integer>> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((name, innerMap) -> {
            StringBuilder sb2 = new StringBuilder();
            innerMap.forEach((price, occurrences) -> sb2.append(String
                    .format("Price: %6.2f\t\tseen:%2d times\n-------------\t\t-------------\n", price, occurrences)));
            sb.append(String.format("\nName: %7s\t\tseen:%2d times\n=============\t\t=============\n", name,
                    innerMap.values().stream().mapToInt(Integer::intValue).sum())).append(sb2);
        });
        sb.append(String.format("\nErrors:       \t\tseen:%2d times\n", errors));
        return sb.toString();
    }
}
