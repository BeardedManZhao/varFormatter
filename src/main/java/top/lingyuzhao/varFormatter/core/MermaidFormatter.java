package top.lingyuzhao.varFormatter.core;

import java.util.Collection;
import java.util.Map;

/**
 * Mermaid 格式化器 此格式化器可以将一个类中的结构进行格式化，能够完全的转换为 Mermaid 格式的图代码。
 * <p>
 * The Mermaid formatter is a formatter that can format structures within a class and fully convert them into Mermaid formatted graph code.
 *
 * @author zhao
 */
public class MermaidFormatter extends ManualFormatter {

    protected MermaidFormatter() {
        super(VarFormatter.MERMAID);
    }

    /**
     * 格式化一个 Map 对象，会自动的将其中的 key 和 value 按照一定的格式进行解析和计算，获取到最终结果。
     * <p>
     * Formatting a Map object will automatically parse and calculate the key and value in a certain format to obtain the final result.!
     *
     * @param data 要格式化的 Map 对象
     *             <p>
     *             object to format
     * @param name 在格式化操作中 需要做为结果的名称，不一定会使用，但也说不定会用到！
     *             <p>
     *             The name that needs to be used as the result in the formatting operation may not be used, but it may also be used!
     * @return Map 对象被格式化操作执行之后的结果
     * <p>
     * The result of a Map object after being formatted
     */
    @Override
    public String format(Map<?, ?> data, String name) {
        StringBuilder stringBuilder = new StringBuilder();
        final String s = name + '.';
        data.forEach((k, v) -> {
            if (v == null) {
                return;
            }
            final String s1 = s + k;
            if (v instanceof Map) {
                stringBuilder.append(String.format("%s==Map>Map==>%s\n", name, s1));
                stringBuilder.append(this.format((Map<?, ?>) v, s1));
            } else if (v instanceof Collection) {
                stringBuilder.append(String.format("%s==Map>Collection==>%s\n", name, s1));
                stringBuilder.append(this.format((Collection<?>) v, s1));
            } else if (v instanceof String || v instanceof Number) {
                stringBuilder.append(String.format("%s==Map>String/Number==>%s\n", name, s1));
                stringBuilder.append(String.format("%s--Map>value-->%s\n", s1, s1 + "v{\"" + v + "\"}"));
            } else {
                stringBuilder.append(String.format("%s==Map>Object==>%s\n", name, s1));
                stringBuilder.append(this.format(v, v.getClass(), s1));
            }
        });
        return stringBuilder.toString();
    }

    /**
     * 格式化一个 List 对象，会自动的将其中的 key 和 value 按照一定的格式进行解析和计算，获取到最终结果。
     * <p>
     * Formatting a Map object will automatically parse and calculate the key and value in a certain format to obtain the final result.!
     *
     * @param data 要格式化的 List 对象
     *             <p>
     *             object to format
     * @param name 在格式化操作中 需要做为 key 的名称
     * @return Map 对象被格式化操作执行之后的结果
     * <p>
     * The result of a Map object after being formatted
     */
    @Override
    public String format(Collection<?> data, String name) {
        StringBuilder stringBuilder = new StringBuilder();
        final String s = name + '.';
        int index = 0;
        for (Object datum : data) {
            final String s1 = s + (++index);
            if (datum == null) {
                continue;
            }
            if (datum instanceof Map) {
                stringBuilder.append(String.format("%s==Collection>Map==>%s\n", name, s1));
                stringBuilder.append(this.format((Map<?, ?>) datum, s1));
            } else if (datum instanceof Collection) {
                stringBuilder.append(String.format("%s==Collection>Collection==>%s\n", name, s1));
                stringBuilder.append(this.format((Collection<?>) datum, s1));
            } else if (datum instanceof String || datum instanceof Number) {
                stringBuilder.append(String.format("%s==Collection>String/Number==>%s\n", name, s1));
                stringBuilder.append(String.format("%s--Collection>value-->%s\n", s1, s1 + "v((\"" + datum + "\"))"));
            } else {
                stringBuilder.append(String.format("%s==Collection>Object==>%s\n", name, s1));
                stringBuilder.append(this.format(datum, datum.getClass(), s1));
            }
        }
        return stringBuilder.toString();
    }
}
