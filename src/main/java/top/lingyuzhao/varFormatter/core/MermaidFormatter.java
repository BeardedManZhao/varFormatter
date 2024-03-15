package top.lingyuzhao.varFormatter.core;

import java.io.PrintWriter;
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
     * 格式化一个 List 对象，会自动的将其中的 key 和 value 按照一定的格式进行解析和计算，获取到最终结果。
     * <p>
     * Formatting a Map object will automatically parse and calculate the key and value in a certain format to obtain the final result.!
     *
     * @param data        要格式化的 List 对象
     *                    <p>
     *                    object to format
     * @param name        在格式化操作中 需要做为 key 的名称
     * @param printWriter 转换结果的数据输出流，转换的结果会存储进这个数据流中！
     *                    <p>
     *                    The data output stream of the conversion result will be stored in this data stream!
     */
    @Override
    public void formatToStream(Map<?, ?> data, String name, PrintWriter printWriter) {
        final String s = name + '.';
        data.forEach((k, v) -> {
            if (v == null) {
                return;
            }
            final String s1 = s + k;
            if (v instanceof Map) {
                printWriter.append(String.format("%s==Map>Map==>%s\n", name, s1));
                this.formatToStream((Map<?, ?>) v, s1, printWriter);
            } else if (v instanceof Collection) {
                printWriter.append(String.format("%s==Map>Collection==>%s\n", name, s1));
                this.formatToStream((Collection<?>) v, s1, printWriter);
            } else if (v instanceof String || v instanceof Number) {
                printWriter.append(String.format("%s==Map>String/Number==>%s\n", name, s1));
                printWriter.append(String.format("%s--Map>value-->%s\n", s1, s1 + "v{\"" + v + "\"}"));
            } else {
                printWriter.append(String.format("%s==Map>Object==>%s\n", name, s1));
                this.formatToStream(v, v.getClass(), s1, printWriter);
            }
        });
    }

    /**
     * 格式化一个 List 对象，会自动的将其中的 key 和 value 按照一定的格式进行解析和计算，获取到最终结果。
     * <p>
     * Formatting a Map object will automatically parse and calculate the key and value in a certain format to obtain the final result.!
     *
     * @param data        要格式化的 List 对象
     *                    <p>
     *                    object to format
     * @param name        在格式化操作中 需要做为 key 的名称
     * @param printWriter 转换结果的数据输出流，转换的结果会存储进这个数据流中！
     *                    <p>
     *                    The data output stream of the conversion result, and the result of the conversion will be stored in this data stream!
     */
    @Override
    public void formatToStream(Collection<?> data, String name, PrintWriter printWriter) {
        final String s = name + '.';
        int index = 0;
        for (Object datum : data) {
            final String s1 = s + (++index);
            if (datum == null) {
                continue;
            }
            if (datum instanceof Map) {
                printWriter.append(String.format("%s==Collection>Map==>%s\n", name, s1));
                printWriter.append(this.format((Map<?, ?>) datum, s1));
            } else if (datum instanceof Collection) {
                printWriter.append(String.format("%s==Collection>Collection==>%s\n", name, s1));
                printWriter.append(this.format((Collection<?>) datum, s1));
            } else if (datum instanceof String || datum instanceof Number) {
                printWriter.append(String.format("%s==Collection>String/Number==>%s\n", name, s1));
                printWriter.append(String.format("%s--Collection>value-->%s\n", s1, s1 + "v((\"" + datum + "\"))"));
            } else {
                printWriter.append(String.format("%s==Collection>Object==>%s\n", name, s1));
                printWriter.append(this.format(datum, datum.getClass(), s1));
            }
        }
    }
}
