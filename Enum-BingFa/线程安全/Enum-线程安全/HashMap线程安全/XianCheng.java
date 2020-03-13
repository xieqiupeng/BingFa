import java.util.HashMap;
import java.util.Map;

public class XianCheng {
    // 下列代码块存在线程安全问题
    // 请修改代码，使线程安全
    // 有多种方式
    public Map<String, Integer> map = new HashMap<>();

    public void add(String key, Integer value) {
        if (map.containsKey(key)) {
            map.put(key, map.get(key) + value);
        } else {
            map.put(key, value);
        }
    }
}
