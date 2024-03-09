package top.lingyuzhao.varFormatter.core;

import top.lingyuzhao.utils.ASClass;
import top.lingyuzhao.varFormatter.utils.XmlNodeObj;

import java.util.Collection;
import java.util.Map;

/**
 * xml格式化组件，其中具有自动根据字段返回格式化的名字的功能，此格式化组件需要在格式化的时候将 getName 设置为 true 便于获取到根节点名字。
 * <p>
 * XML formatting component, which has the function of automatically returning formatted names based on fields. This formatting component needs to set getName to true during formatting to obtain the root node name.
 *
 * @author zhao
 */
public class XmlFormatter extends JsonFormatter {

    public XmlFormatter() {
        super(VarFormatter.XML);
    }

    protected XmlFormatter(VarFormatter formatterType) {
        super(formatterType);
    }

    @Override
    protected String header(String name, Object o) {
        if (name == null) {
            return "";
        }
        return this.formatName_start(name, o);
    }

    /**
     * @param name 需要被格式化的对象的名字。
     * @return 一个数据类型的起始字符串
     */
    @Override
    protected String footer(String name, Object o) {
        if (name == null) {
            return "";
        }
        return this.formatName_EndLast(name, o);
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
    @Override
    protected String formatName_start(String name, Object o) {
        if (o instanceof XmlNodeObj) {
            // 代表可能有属性
            name += ((XmlNodeObj) o).getAttrStr();
        }
        return '<' + name + '>';
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
    @Override
    protected String formatName_End(String name, Object o) {
        return "</" + name + '>';
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
    @Override
    protected String formatName_EndLast(String name, Object o) {
        return this.formatName_End(name, o);
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
    @Override
    protected String formatValue(String name, Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Number || value instanceof String) {
            return value.toString();
        }
        if (value instanceof Map) {
            return this.formatMap(ASClass.transform(value));
        }
        if (value instanceof Collection) {
            return this.formatList(ASClass.transform(value), name);
        }
        return this.format(value, false);
    }
}
