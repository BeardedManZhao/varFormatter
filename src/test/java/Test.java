import top.lingyuzhao.varFormatter.core.Formatter;
import top.lingyuzhao.varFormatter.core.VarFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 测试类
 *
 * @author zhao
 */
public class Test {

    public static void main(String[] args) {
        // 使用单例模式 获取到 MERMAID 格式化组件
        final Formatter formatter0 = VarFormatter.YAML.getFormatter(true);
        // 将对象进行格式化操作 获取到对象的 MERMAID 结构图的代码 并打印出来
        System.out.println(formatter0.format(new TestObj()));
    }

    // 准备了一个复杂的类
    static class TestObj {
        String name = "zhao";
        int age = 1024;
        HashMap<String, Object> data = new HashMap<>();
        TestObj2 testObj2 = new TestObj2();

        {
            data.put("k", 123123);
            data.put("k1", "123123");
        }

        public static class TestObj2 {
            String name = "zhao123";
            ArrayList<Object> arrayList = new ArrayList<>();

            {
                arrayList.add(1);
                arrayList.add(2);
                arrayList.add(3);
                arrayList.add(4);
                // 这里使用了一个 Date 类 增大 类的复杂度
                arrayList.add(new Date());
            }
        }
    }
}