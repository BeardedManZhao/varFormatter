package top.lingyuzhao.varFormatter.core;

import top.lingyuzhao.varFormatter.utils.StrUtils;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

/**
 * 将一个类的 Java 对象实例 变为 python 类的代码！
 *
 * @author zhao
 */
public class JToPythonFormatter extends ManualFormatter {

    private static final Formatter JSON_FORMATTER = VarFormatter.JSON.getFormatter(true);

    protected JToPythonFormatter() {
        super(VarFormatter.J_TO_PYTHON);
    }

    @Override
    public void formatToStream(Map<?, ?> data, String name, PrintWriter printWriter) {
        printWriter.println("class " + StrUtils.snakeToCamel(name) + ":");
        printWriter.println("    # 构造函数 / Constructor");
        printWriter.println("    def __init__(self):");
        // 开始迭代字段
        data.forEach((k, v) -> {
            if (v instanceof Number) {
                printWriter.println("        self." + k + " = " + v);
            } else if (v instanceof String) {
                // 将 字符串中的所有 \ 做转义
                final String temp = StrUtils.escapeNewline(((String) v));
                printWriter.println("        self." + k + " = '" + temp + "'");
            } else if (v instanceof Map<?, ?>) {
                printWriter.println("        self." + k + " = " + JSON_FORMATTER.format((Map<?, ?>) v));
            } else {
                printWriter.println("        self." + k + " = " + JSON_FORMATTER.format(v));
            }
        });
        // 结束构造函数字段，开始迭代方法
        data.forEach(
                (k, v) -> {
                    // 将 k 做命名重置
                    final String name_k = StrUtils.CamelToSnake(k.toString());
                    printWriter.println("\n    def get_" + name_k + "(self):");
                    printWriter.println("        return self." + k);
                    printWriter.println("\n    def set_" + name_k + "(self, v):");
                    printWriter.println("        self." + k + " = v");
                }
        );
    }

    @Override
    public void formatToStream(Collection<?> data, String name, PrintWriter printWriter) {
        printWriter.println(data == null ? "[]" : data.toString());
    }
}
