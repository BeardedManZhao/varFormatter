package top.lingyuzhao.varFormatter.core;

import java.util.Collection;
import java.util.Map;

/**
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

    public String format(Object data, Class<?> dataClassObj, String name, String rowTab) {
        return this.format(StructuralCache.classToMap(dataClassObj, data), name, rowTab);
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
        return this.format(data, name, "\n\t");
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
    public String format(Map<?, ?> data, String name, String rowTab) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        data.forEach((k, v) -> {
            final String subName = k.toString() + ": ";
            if (v == null) {
                stringBuilder.append(rowTab).append(subName).append("null");
            } else if (v instanceof Map) {
                stringBuilder.append(rowTab).append(this.format((Map<?, ?>) v, subName, rowTab + '\t'));
            } else if (v instanceof Collection) {
                stringBuilder.append(rowTab).append(this.format((Collection<?>) v, subName, rowTab + '\t'));
            } else if (v instanceof String || v instanceof Number) {
                stringBuilder.append(rowTab).append(subName).append(v);
            } else {
                stringBuilder.append(rowTab).append(this.format(v, v.getClass(), subName, rowTab + '\t'));
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
        return this.format(data, name, "\t");
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
    public String format(Collection<?> data, String name, String rowTab) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        for (Object v : data) {
            if (v == null) {
                continue;
            }
            if (v instanceof Map) {
                stringBuilder.append(rowTab).append(this.format((Map<?, ?>) v, "- ", rowTab + '\t'));
            } else if (v instanceof Collection) {
                stringBuilder.append(rowTab).append(this.format((Collection<?>) v, "- ", rowTab + '\t'));
            } else if (v instanceof String) {
                stringBuilder.append(rowTab).append('-').append(' ').append('"').append(v).append('"');
            } else if (v instanceof Number) {
                stringBuilder.append(rowTab).append('-').append(' ').append(v);
            } else {
                stringBuilder.append(rowTab).append(this.format(v, v.getClass(), "- ", rowTab + '\t'));
            }
        }
        return stringBuilder.toString();
    }

}
