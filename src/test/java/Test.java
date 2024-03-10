import top.lingyuzhao.varFormatter.core.Formatter;
import top.lingyuzhao.varFormatter.core.VarFormatter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 测试类
 *
 * @author zhao
 */
public class Test {

    public static void main(String[] args) throws IOException {
        // 使用单例模式 获取到 json 格式化组件
        final Formatter formatter0 = VarFormatter.XML.getFormatter(true);
        // 实例化两个 TestObj 对象
        TestObj testObj1 = new TestObj();
        TestObj testObj2 = new TestObj();
        // 修改第二个对象中的 age 为 2048
        testObj2.age = 2048;
        // 将两个对象进行格式化操作 获取到对象的 json 结构
        try(final StringWriter stringWriter = new StringWriter(); final PrintWriter printWriter = new PrintWriter(stringWriter)){
            formatter0.formatToStream(testObj1, printWriter);
            System.out.println(stringWriter);
        }
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