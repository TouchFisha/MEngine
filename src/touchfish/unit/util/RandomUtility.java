package touchfish.unit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtility {
    public static Random random = new Random();
    public static int range(int min,int max) {
        return random.nextInt(max-min) + min;
    }
    public static void setSeed(int seed){
        random.setSeed(seed);
    }
    public static String getRandomIP() {
        return range(1,256)+"."+range(0,256)+"."+range(0,256)+"."+range(1,256);
    }
    public static <T> List<T> getRandomItems(List<T> array, int seed, int count){
        Random random = new Random();
        random.setSeed(seed);
        List<T> res = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            res.add(array.get(random.nextInt(array.size())));
        }
        return res;
    }
}
