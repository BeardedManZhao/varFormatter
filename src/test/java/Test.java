import top.lingyuzhao.varFormatter.core.Formatter;
import top.lingyuzhao.varFormatter.core.VarFormatter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 测试类
 *
 * @author zhao
 */
public class Test {

    public static void main(String[] args) {
        // 使用单例模式 获取到 json 格式化组件
        final Formatter formatter0 = VarFormatter.J_TO_PYTHON.getFormatter(true);
        // 实例化 TestObj 对象
        TestObj testObj1 = new TestObj();
        // 转换成 python 类的代码 并打印出来
        System.out.println(formatter0.format(testObj1));
    }

    // 准备了一个复杂的类
    static class TestObj {
        String name = "zhao\nling\nyu\tzhao";
        int age = 1024;
        HashMap<String, Object> data = new HashMap<>();
        TestObj2 testObj2 = new TestObj2();

        {
            data.put("k", 123123);
            data.put("k1", "123\n\n123");
        }

        public static class TestObj2 {
            String name = "zhao123";
            ArrayList<Integer> arrayList = new ArrayList<>();

            {
                arrayList.add(1);
                arrayList.add(2);
                arrayList.add(3);
                arrayList.add(4);
            }
        }
    }
}