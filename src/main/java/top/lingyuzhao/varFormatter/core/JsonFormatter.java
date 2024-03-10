package top.lingyuzhao.varFormatter.core;

import top.lingyuzhao.utils.ASClass;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

/**
 * JSON格式化器，同时也是基础格式化器，此格式化器将会自动的迭代一个对象中的所有子参数，并根据子参数的字段类型进行格式化操作，如果子类没有具体的实现，则这里返回的就是 json 数据类型。
 * <p>
 * The JSON formatter, also known as the basic formatter, automatically iterates over all sub parameters in an object and performs formatting operations based on the field type of the sub parameters. If the sub class does not have a specific implementation, the returned data type is the JSON data class.
 *
 * @author zhao
 */
public class JsonFormatter extends ManualFormatter {

    /**
     * 实例化格式化组件
     * <p>
     * The model of the current formatting component and the name of the data that can be formatted.
     */
    protected JsonFormatter() {
        this(VarFormatter.JSON);
    }

    /**
     * 实例化格式化组件
     *
     * @param formatterType 当前格式化组件的型号，能够进行格式化的数据的名称。
     *
     *                      <p>
     *                      The model of the current formatting component and the name of the data that can be formatted.
     */
    protected JsonFormatter(VarFormatter formatterType) {
        super(formatterType);
    }

    @Override
    public void formatToStream(Map<?, ?> data, String name, PrintWriter printWriter) {
        int count = 0;
        final int size = data.size();
        for (Map.Entry<?, ?> stringObjectEntry : data.entrySet()) {
            final String k = stringObjectEntry.getKey().toString();
            final Object v = stringObjectEntry.getValue();
            count = getCount(k, printWriter, count, size, v);
        }
    }

    @Override
    public void formatToStream(Collection<?> data, String name, PrintWriter printWriter) {
        int count = 0;
        final int size = data.size();
        for (Object v : data) {
            count = getCount(name, printWriter, count, size, v);
        }
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
    public final String format(Map<?, ?> data, String name) {
        // 完毕之后将结果返回
        return this.header(name, data) + this.formatMap(data) + this.footer(name, data);
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
    public final String format(Collection<?> data, String name) {
        // 完毕之后将结果返回
        return this.formatList(data, null);
    }

    /**
     * 格式化一个 Map 对象，会自动的将其中的 key 和 value 按照一定的格式进行解析和计算，获取到最终结果。
     * <p>
     * Formatting a Map object will automatically parse and calculate the key and value in a certain format to obtain the final result.!
     *
     * @param data 要格式化的 Map 对象
     *             <p>
     *             object to format
     * @return Map 对象被格式化操作执行之后的结果
     * <p>
     * The result of a Map object after being formatted
     */
    public String formatMap(Map<?, ?> data) {
        try(StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)){
            this.formatToStream(data, printWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 格式化一个 List 对象，会自动的将其中的 key 和 value 按照一定的格式进行解析和计算，获取到最终结果。
     * <p>
     * Formatting a Map object will automatically parse and calculate the key and value in a certain format to obtain the final result.!
     *
     * @param data 要格式化的 List 对象
     *             <p>
     *             object to format
     * @param name 在格式化操作中 需要做为 key 的名称，在格式化操作中，list 往往会有可能不需要名字，因此这里可以设置为 null，如果格式化操作中需要名字，那么这里可以设置为一个名字。
     *             <p>
     *             In the format operation, it is necessary to use it as the name of the key. In the format operation, the list may not require a name, so it can be set to null here. If a name is required in the format operation, it can be set to a name here.
     * @return Map 对象被格式化操作执行之后的结果
     * <p>
     * The result of a Map object after being formatted
     */
    public String formatList(Collection<?> data, String name) {
        try(StringWriter stringWriter = new StringWriter(); PrintWriter printWriter = new PrintWriter(stringWriter)){
            this.formatToStream(data, name, printWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getCount(String name, PrintWriter stringBuilder, int count, int size, Object v) {
        if (++count == size) {
            // 如果是最后一个就直接将 formatName_start formatName_EndLast 调用
            stringBuilder.append(this.formatName_start(name, v)).append(this.formatValue(name, v)).append(this.formatName_EndLast(name, v));
        } else {
            // 如果是中间的就直接 将 formatName_startLast formatName_EndLast 调用
            stringBuilder.append(this.formatName_start(name, v)).append(this.formatValue(name, v)).append(this.formatName_End(name, v));
        }
        return count;
    }

    /**
     * @param name 需要被进行格式化的字段的名字。
     *             <p>
     *             The name of the field that needs to be formatted.
     * @param o    需要被进行格式化的字段的值。
     *             <p>
     *             The value of the field that needs to be formatted.
     * @return 一个数据类型的起始字符串
     * <p>
     * The starting string of a data type
     */
    protected String header(String name, Object o) {
        return "{";
    }

    /**
     * @param name 需要被进行格式化的字段的名字。
     *             <p>
     *             The name of the field that needs to be formatted.
     * @param o    需要被进行格式化的字段的值。
     *             <p>
     *             The value of the field that needs to be formatted.
     * @return 一个数据类型的终止字符串
     * <p>
     * The end string of a data type
     */
    protected String footer(String name, Object o) {
        return "}";
    }

    /**
     * 格式化数据 此函数用来将字段的名字进行格式化操作，需要注意的是，这里务必将所有的字符添加，例如 `json` 中的格式化就是 `return "\"" + name + "\":"`
     * <p>
     * Format Data This function is used to format the name of a field. It should be noted that all characters must be added here. For example, in ` JSON `, formatting is ` return "\" "+name+" \ ":"`
     *
     * @param name 需要被进行格式化的字段的名字。
     *             <p>
     *             The name of the field that needs to be formatted.
     * @param o    需要被进行格式化的字段的值。
     *             <p>
     *             The value of the field that needs to be formatted.
     * @return 字段名字的生成规则
     * <p>
     * Rules for generating field names
     */
    protected String formatName_start(String name, Object o) {
        final String s = name == null ? "" : "\"" + name + "\":";
        if (o instanceof Map) {
            return s + '{';
        } else if (o instanceof Collection) {
            return s + '[';
        } else {
            return s;
        }
    }


    /**
     * 格式化数据 此函数用来将字段的名字进行格式化操作，需要注意的是 这里是用于在数值格式化完毕之后的格式化，也是结尾格式化，例如 `json` 中的格式化就是 `return ","`
     *
     * @param name 需要被进行格式化的字段的名字。
     *             <p>
     *             The name of the field that needs to be formatted.
     * @param o    需要被进行格式化的字段的值。
     *             <p>
     *             The value of the field that needs to be formatted.
     * @return 字段名字的生成规则
     * <p>
     * Rules for generating field names
     */
    protected String formatName_End(String name, Object o) {
        return this.formatName_EndLast(name, o) + ',';
    }

    /**
     * 格式化数据 此函数用来将字段的名字进行格式化操作，同样是结尾格式化，不过此函数是用于最后一个字段的结尾格式化，例如 `json` 中的格式化就是 `return ""`
     *
     * @param name 需要被进行格式化的字段的名字。
     *             <p>
     *             The name of the field that needs to be formatted.
     * @param o    需要被进行格式化的字段的值。
     *             <p>
     *             The value of the field that needs to be formatted.
     * @return 字段名字的生成规则
     * <p>
     * Rules for generating field names
     */
    protected String formatName_EndLast(String name, Object o) {
        final String s = "";
        if (o instanceof Map) {
            return s + '}';
        } else if (o instanceof Collection) {
            return s + ']';
        } else {
            return s;
        }
    }


    /**
     * 格式化数据 此函数一般是将所有的字段按照类型分类处理并进行结果的追加。
     *
     * @param name  需要被进行格式化的字段的名字。
     *              <p>
     *              The name of the field that needs to be formatted.
     * @param value 格式化操作中要用来进行存储的对象。
     * @return 字段名字的生成规则
     */
    protected String formatValue(String name, Object value) {
        if (value instanceof Number) {
            return value.toString();
        }
        if (value instanceof Map) {
            return this.formatMap(ASClass.transform(value));
        }
        if (value instanceof Collection) {
            return this.formatList(ASClass.transform(value), null);
        }
        if (value instanceof String) {
            return "\"" + value + "\"";
        }
        return this.format(value);
    }
}
