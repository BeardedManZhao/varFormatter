package top.lingyuzhao.varFormatter.core;

import java.util.LinkedHashMap;

/**
 * HTML 格式化组件，其可以直接将最基本的 HTML 格式构建出来，其是 XmlFormatter 的子类，相对于 XmlFormatter 来说，能够保证类结构中的上下顺序。
 * <p>
 * HTML formatting component, which can directly construct the most basic HTML format, is a subclass of XmlFormatter. Compared to XmlFormatter, it can ensure the up and down order in the class structure.
 *
 * @author zhao
 */
public class HtmlFormatter extends XmlFormatter {

    /**
     * HTML 格式化组件的实例化函数。
     */
    public HtmlFormatter() {
        super(VarFormatter.HTML);
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
        final Class<?> aClass = data instanceof Class ? (Class<?>) data : data.getClass();
        if (getName) {
            return this.format(StructuralCache.classToMap(aClass, data, new LinkedHashMap<>()), StructuralNameCache.classToName(aClass));
        }
        return this.format(StructuralCache.classToMap(aClass, data, new LinkedHashMap<>()), null);
    }
}
