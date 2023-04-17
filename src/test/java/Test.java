import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void testFindMatch(){
        // Given
        String regex = ".+?(?=##)";
        String expected = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";

        // When
        String actual = Main.findMatch(regex,"naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##");

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
        Assert.assertNull(Main.findMatch(regex,str));
    }

    @org.junit.Test
    public void testFixName(){
        // Given
        String expected = "Milk";

        // When
        String actual = Main.fixName("mIlK");

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
        Main.addPrice(innerMap,5.0);
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
        String actual = Main.getResult(map);

        // Then
        Assert.assertEquals(expected,actual);
    }
    @org.junit.Test
    public void testProcess(){
        // Given
        Map<Double,Integer> innerMap = new HashMap<>();
        Integer expected = 6;
        // When
        innerMap.put(5.0,5);
        Main.addPrice(innerMap,5.0);
        Integer actual = innerMap.get(5.0);
        // Then
        Assert.assertEquals(expected,actual);
    }

}
