import org.junit.Assert;
import org.junit.Before;

import java.util.HashMap;
import java.util.Map;

public class Test {
    DataProcess dataProcess;
    @Before
    public void setUp(){
        dataProcess = new DataProcess();
    }
    @org.junit.Test
    public void testFindMatch(){
        // Given
        String regex = ".+?(?=##)";
        String expected = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";

        // When
        String actual = dataProcess.findMatch(regex,"naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##");

        // Then
        Assert.assertEquals(expected,actual);
    }

    @org.junit.Test
    public void testFindMatchNull(){
        // Given
        String regex = ".+?(?=##)";
        String str = "";

        // When
        // Then
        Assert.assertNull(dataProcess.findMatch(regex,str));
    }

    @org.junit.Test
    public void testFixName(){
        // Given
        String expected = "Milk";

        // When
        String actual = dataProcess.fixName("mIlK");

        // Then
        Assert.assertEquals(expected,actual);
    }
    @org.junit.Test
    public void testAddPrice(){
        // Given
        Map<Double,Integer> innerMap = new HashMap<>();
        Integer expected = 6;
        // When
        innerMap.put(5.0,5);
        dataProcess.addPrice(innerMap,5.0);
        Integer actual = innerMap.get(5.0);
        // Then
        Assert.assertEquals(expected,actual);
    }
    @org.junit.Test
    public void testGetResult(){
        // Given
        Map<String,Map<Double,Integer>> map = new HashMap<>();
        Map<Double,Integer> innerMap = new HashMap<>();
        String expected = "\nName:    HELP\t\tseen: 5 times\n" +
                "=============\t\t=============\n" +
                "Price:   5.00\t\tseen: 5 times\n" +
                "-------------\t\t-------------\n" +
                "\nErrors:       \t\tseen: 0 times\n";

        // When
        innerMap.put(5.0,5);
        map.put("HELP",innerMap);
        String actual = dataProcess.getResult(map);

        // Then
        Assert.assertEquals(expected,actual);
    }

    @org.junit.Test
    public void testProcess(){
        // Given
        String expected = "\nErrors:       \t\tseen: 0 times\n";

        // When
        String actual = dataProcess.process("");

        // Then
        Assert.assertEquals(expected,actual);
    }

    @org.junit.Test
    public void testProcess2(){
        // Given
        String expected =
                "\nName:    Milk\t\tseen: 1 times\n" +
                "=============\t\t=============\n" +
                "Price:   3.23\t\tseen: 1 times\n" +
                "-------------\t\t-------------\n" +
                "\nErrors:       \t\tseen: 1 times\n";

        // When
        String actual = dataProcess.process("naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naMe:;price:;type:Food;expiration:1/25/2016##");

        // Then
        Assert.assertEquals(expected,actual);
    }

}
