import java.util.HashMap;
import java.util.Map;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/5 17:54
 */
public class A {
    public static void main(String[] args) {
        mapTest();
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static void mapTest() {
        int aHundredMillion = 10000000;
        Map<Integer, Integer> map = new HashMap<>();
        map.size();
        long s1 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map.put(i, i);
        }

        long s2 = System.currentTimeMillis();
        System.out.println("未初始化容量，耗时" + (s2 - s1));


        int initNum = (int) ((aHundredMillion / 0.75) + 1);
        Map<Integer, Integer> map1 = new HashMap<>(initNum);

        long s3 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map1.put(i, i);
        }
        long s4 = System.currentTimeMillis();
        System.out.println("初始化容量,容量:"+initNum+"，耗时" + (s4 - s3));


        initNum = aHundredMillion / 2;
        Map<Integer, Integer> map2 = new HashMap<>(initNum);

        long s5 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map2.put(i, i);
        }
        long s6 = System.currentTimeMillis();
        System.out.println("初始化容量,容量:"+initNum+"，耗时" + (s6 - s5));

        initNum = aHundredMillion;
        Map<Integer, Integer> map3 = new HashMap<>(initNum);

        long s7 = System.currentTimeMillis();
        for (int i = 0; i < aHundredMillion; i++) {
            map3.put(i, i);
        }
        long s8 = System.currentTimeMillis();
        System.out.println("初始化容量,容量:"+initNum+"，耗时" + (s8 - s7));


    }
}
