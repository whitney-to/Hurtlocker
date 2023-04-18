import org.apache.commons.io.IOUtils;

public class Main {
    public String readRawDataToString(String fileName) throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString("RawData.txt");
        System.out.println((new DataProcess()).process(output));
    }
}
