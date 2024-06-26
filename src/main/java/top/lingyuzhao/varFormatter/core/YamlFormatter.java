package top.lingyuzhao.varFormatter.core;

import top.lingyuzhao.varFormatter.utils.StrUtils;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * yaml 格式化转换器，能够将一个对象进行格式化，生成 yaml 格式的数据！
 * <p>
 * YAML formatting converter, capable of formatting an object and generating YAML formatted data!
 *
 * @author zhao
 */
public class YamlFormatter extends ManualFormatter {

    protected YamlFormatter() {
        super(VarFormatter.YAML);
    }

    /**
     * 格式化数据函数，您可以在这里直接将需要被进行格式化的数据传递进来！
     * <p>
     * Format data function, you can directly pass in the data that needs to be formatted here!
     *
     * @param data    要格式化的数据对象，会将其中的所有字段进行格式化操作，字段名字做为 key 字段类型做为value。
     *                <p>
     *                The data object to be formatted will have all its fields formatted, with field names as key and field types as value.
     * @param getName 设置为 true 可以在格式化操作开始之前，先获取到当前类的名字，用来进行根节点生成，有些数据类型可能会需要，有些则不会需要
     *                <p>
     *                Before starting the formatting operation, obtain the name of the current class for root node generation. Some data types may require it, while others may not
     * @return 格式化后的数据
     */
    @Override
    public String format(Object data, boolean getName) {
        return super.format(data, getName);
    }

    @Override
    public String format(Object data, Class<?> dataClassObj, String name) {
        return super.format(data, dataClassObj, name + ": ");
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
    public void formatToStream(Map<?, ?> data, String name, PrintWriter printWriter) {
        this.format(data, name, "\n\t", printWriter);
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
        this.format(data, name, "\n\t", printWriter);
    }


    public void format(Object data, Class<?> dataClassObj, String name, String rowTab, PrintWriter printWriter) {
        this.format(StructuralCache.classToMap(dataClassObj, data), name, rowTab, printWriter);
    }

    /**
     * 格式化一个 Map 对象，会自动的将其中的 key 和 value 按照一定的格式进行解析和计算，获取到最终结果。
     * <p>
     * Formatting a Map object will automatically parse and calculate the key and value in a certain format to obtain the final result.!
     *
     * @param data          要格式化的 Map 对象
     *                      <p>
     *                      object to format
     * @param rowTab        制表符前缀
     *                      <p>
     *                      Tab prefix
     * @param name          在格式化操作中 需要做为结果的名称，不一定会使用，但也说不定会用到！
     *                      <p>
     *                      The name that needs to be used as the result in the formatting operation may not be used, but it may also be used!
     * @param stringBuilder 转换结果的数据输出流，转换的结果会存储进这个数据流中！
     *                      <p>
     *                      The data output stream of the conversion result will be stored in this data stream!
     */
    public void format(Map<?, ?> data, String name, String rowTab, PrintWriter stringBuilder) {
        stringBuilder.append(name);
        data.forEach((k, v) -> {
            final String subName = k.toString() + ": ";
            if (v == null) {
                stringBuilder.append(rowTab).append(subName).append("null");
            } else if (v instanceof Map) {
                stringBuilder.append(rowTab);
                this.format((Map<?, ?>) v, subName, rowTab + '\t', stringBuilder);
            } else if (v instanceof Collection) {
                stringBuilder.append(rowTab);
                this.format((Collection<?>) v, subName, rowTab + '\t', stringBuilder);
            } else if (v instanceof String || v instanceof Number) {
                stringBuilder.append(rowTab).append(subName).append(StrUtils.escapeNewline(Objects.toString(v)));
            } else {
                stringBuilder.append(rowTab);
                this.format(v, v.getClass(), subName, rowTab + '\t', stringBuilder);
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
     * @param rowTab      制表符前缀
     *                    <p>
     *                    Tab prefix
     * @param printWriter 转换结果的数据输出流，转换的结果会存储进这个数据流中！
     *                    <p>
     *                    The data output stream of the conversion result will be stored in this data stream!
     */
    public void format(Collection<?> data, String name, String rowTab, PrintWriter printWriter) {
        printWriter.append(name);
        for (Object v : data) {
            if (v == null) {
                continue;
            }
            if (v instanceof Map) {
                printWriter.append(rowTab);
                this.format((Map<?, ?>) v, "- ", rowTab + '\t', printWriter);
            } else if (v instanceof Collection) {
                printWriter.append(rowTab);
                this.format((Collection<?>) v, "- ", rowTab + '\t', printWriter);
            } else if (v instanceof String) {
                printWriter.append(rowTab).append('-').append(' ').append('"').append(Objects.toString(v)).append('"');
            } else if (v instanceof Number) {
                printWriter.append(rowTab).append('-').append(' ').append(Objects.toString(v));
            } else {
                printWriter.append(rowTab);
                this.format(v, v.getClass(), "- ", rowTab + '\t', printWriter);
            }
        }
    }

}
