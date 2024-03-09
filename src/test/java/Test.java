import top.lingyuzhao.varFormatter.core.Formatter;
import top.lingyuzhao.varFormatter.core.VarFormatter;

public class Test {

    public static void main(String[] args) {
        // 使用单例模式 获取到 json 格式化组件
        final Formatter formatter0 = VarFormatter.JSON.getFormatter(true);
        // 使用单例模式 再次获取到 json 的格式化组件
        final Formatter formatter1 = VarFormatter.JSON.getFormatter(true);
        // 使用多例模式 获取到 json 格式化组件
        final Formatter formatter2 = VarFormatter.JSON.getFormatter(false);
        // 判断三个组件的内存地址是否相同
        // 结论 单例获取到的都是同一个内存地址的组件
        System.out.println(formatter0 == formatter1);
        System.out.println(formatter0 == formatter2);
        System.out.println(formatter1 == formatter2);
    }
}