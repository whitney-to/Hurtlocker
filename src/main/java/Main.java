import org.apache.commons.io.IOUtils;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String[] regexList = {"(?i)(?<=name:)[^;]+", "(?i)(?<=price:)[^;]+", "(?i)(?<=type:)[^\\^;%*@!]+", "(?i)(?<=expiration:)[^#]+"};
    static Integer errors = 0;

    static Map<String, Map<Double,Integer>> map = new LinkedHashMap<>();

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println(process(output));
    }

    public static String process(String data){
        Matcher m  = Pattern.compile(".+?(?=##)").matcher(data);
        while(m.find()){
            try{
                String name = fixName(findMatch(regexList[0],m.group()));
                map.putIfAbsent(name,new HashMap<>());
                map.put(name,addPrice(map.get(name),Double.parseDouble(findMatch(regexList[1],m.group()))));
            } catch (NullPointerException e){
                errors++;
            }
        }
        return getResult(map);
    }


    public static String findMatch(String regex, String str){
        Matcher m = Pattern.compile(regex).matcher(str);
        return m.find() ? m.group() : null;
    }
    public static Map<Double,Integer> addPrice(Map<Double, Integer> innerMap, double price){
        Integer integer = innerMap.containsKey(price) ? innerMap.put(price, innerMap.get(price) + 1) : innerMap.put(price, 1);
        return innerMap;
    }

    public static String fixName(String name){
        return name.matches("(?i)[milk]{4}") ? "Milk" : name.matches("(?i)[bread]{5}") ? "Bread" : name.matches("(?i)[aples]{6}") ? "Apples" : name.matches("(?i)[c(o|0){2}kies]{7}")? "Cookies" : "";
    }

    public static String getResult(Map<String, Map<Double,Integer>> map){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,Map<Double,Integer>> eachItem : map.entrySet()){
            StringBuilder sb2 = new StringBuilder();
            int count = 0;
            for(Map.Entry<Double,Integer> eachPrice : eachItem.getValue().entrySet()){
                sb2.append(String.format("Price: %6.2f\t\tseen:%2d times\n-------------\t\t-------------\n",eachPrice.getKey(),eachPrice.getValue()));
                count+=eachPrice.getValue();
            }
            sb.append(String.format("\nName: %7s\t\tseen:%2d times\n=============\t\t=============\n",eachItem.getKey(),count));
            sb.append(sb2);
        }
        sb.append(String.format("\nErrors:       \t\tseen:%2d times\n",errors));
       return sb.toString();
    }
}
