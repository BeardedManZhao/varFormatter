import top.lingyuzhao.varFormatter.core.Formatter;
import top.lingyuzhao.varFormatter.core.VarFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 测试类
 *
 * @author zhao
 */
public class Test {

    public static void main(String[] args) {
        // 获取到 json 格式化组件
        final Formatter formatter = VarFormatter.JSON.getFormatter(true);
        // 将 对象转为 json 格式
        System.out.println(formatter.format(new TestObj()));

        // 获取到 xml 格式化组件
        final Formatter formatter1 = VarFormatter.XML.getFormatter(true);
        // 将 对象转为 xml 格式
        System.out.println(formatter1.format(new TestObj()));

        // 获取到 html 格式化组件
        final Formatter formatter2 = VarFormatter.HTML.getFormatter(true);
        // 将 对象转为 html 格式
        System.out.println(formatter2.format(new HtmlObj()));
    }

    static class HtmlObj {
        String h1 = "大标题";
        String h2 = "小标题";
        List<li> ul = new ArrayList<>();

        {
            ul.add(new li());
            ul.add(new li());
            ul.add(new li());
        }

        static class li {
            String li = "行数据";
        }
    }

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
