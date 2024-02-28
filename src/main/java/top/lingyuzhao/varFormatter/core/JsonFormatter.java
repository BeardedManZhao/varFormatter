package top.lingyuzhao.varFormatter.core;

import top.lingyuzhao.utils.ASClass;
import top.lingyuzhao.utils.StrUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 万能格式化器，此格式化器将会自动的迭代一个对象中的所有子参数，并根据子参数的字段类型进行格式化操作。
 * <p>
 * Universal formatter, this formatter will automatically iterate over all sub parameters in an object and perform formatting operations based on the field type of the sub parameters.
 *
 * @author zhao
 */
public class JsonFormatter implements Formatter {

    /**
     * 当前格式化组件的型号，能够进行格式化的数据的名称。
     *
     * <p>
     * The model of the current formatting component and the name of the data that can be formatted.
     */
    private final VarFormatter formatterType;

    /**
     * 实例化格式化组件
     *
     * @param formatterType 当前格式化组件的型号，能够进行格式化的数据的名称。
     *
     *                      <p>
     *                      The model of the current formatting component and the name of the data that can be formatted.
     */
    protected JsonFormatter(VarFormatter formatterType) {
        this.formatterType = formatterType;
    }

    /**
     * 实例化格式化组件
     * <p>
     * The model of the current formatting component and the name of the data that can be formatted.
     */
    protected JsonFormatter() {
        this(VarFormatter.JSON);
    }

    /**
     * @return 当前格式化组件的型号，能够进行格式化的数据的名称。
     * <p>
     * The model of the current formatting component and the name of the data that can be formatted.
     */
    @Override
    public VarFormatter getFormatterType() {
        return this.formatterType;
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
    @Override
    public final String format(Map<?, ?> data) {
        // 完毕之后将结果返回
        return this.format(data, "map");
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
        return this.header(name) + this.formatMap(data) + this.footer(name);
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
            final String[] strings = StrUtils.splitBy(aClass.getName(), '$');
            return this.format(StructuralCache.classToMap(aClass, data), strings[strings.length - 1]);
        }
        return this.format(StructuralCache.classToMap(aClass, data), null);
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
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        final int size = data.size();
        for (Map.Entry<?, ?> stringObjectEntry : data.entrySet()) {
            final String k = stringObjectEntry.getKey().toString();
            final Object v = stringObjectEntry.getValue();
            count = getCount(k, stringBuilder, count, size, v);
        }
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
     * @param name 在格式化操作中 需要做为 key 的名称，在格式化操作中，list 往往会有可能不需要名字，因此这里可以设置为 null，如果格式化操作中需要名字，那么这里可以设置为一个名字。
     *             <p>
     *             In the format operation, it is necessary to use it as the name of the key. In the format operation, the list may not require a name, so it can be set to null here. If a name is required in the format operation, it can be set to a name here.
     * @return Map 对象被格式化操作执行之后的结果
     * <p>
     * The result of a Map object after being formatted
     */
    public String formatList(Collection<?> data, String name) {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        final int size = data.size();
        for (Object v : data) {
            count = getCount(name, stringBuilder, count, size, v);
        }
        return stringBuilder.toString();
    }

    private int getCount(String name, StringBuilder stringBuilder, int count, int size, Object v) {
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
     * 格式化数据
     *
     * @param data 要格式化的数据
     * @return 格式化后的数据
     */
    @Override
    public final String format(Object data) {
        return this.format(data, true);
    }

    /**
     * @param name 需要被格式化的对象的名字。
     * @return 一个数据类型的起始字符串
     */
    protected String header(String name) {
        return "{";
    }

    /**
     * @param name 需要被格式化的对象的名字。
     * @return 一个数据类型的尾部字符串
     */
    protected String footer(String name) {
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


    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     * <p>
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     * <p>
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     * <p>
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     * <p>
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     * @throws UnsupportedOperationException if the object's class does not
     *                                       support the {@code Cloneable} interface. Subclasses
     *                                       that override the {@code clone} method can also
     *                                       throw this exception to indicate that an instance cannot
     *                                       be cloned.
     * @see Cloneable
     */
    @Override
    public JsonFormatter clone() {
        try {
            return (JsonFormatter) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
