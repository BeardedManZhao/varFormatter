package top.lingyuzhao.varFormatter.core;

import top.lingyuzhao.varFormatter.utils.DataObj;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 手动格式化组件，在此组件中，您可以实现您需要的格式化操作与模式，同时库中的所有格式化器皆是此格式化器的子类实现。
 * <p>
 * Manually format the component, in which you can implement the formatting operations and patterns you need, and all formatters in the library are subclasses of this formatter implementation.
 *
 * @author zhao
 */
public abstract class ManualFormatter implements Formatter {
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
    protected ManualFormatter(VarFormatter formatterType) {
        this.formatterType = formatterType;
    }

    /**
     * @return 当前格式化组件的型号，同时也是能够进行格式化的数据的类型。
     * <p>
     * The current model of the formatted component, which is also the type of data that can be formatted.
     */
    @Override
    public VarFormatter getFormatterType() {
        return this.formatterType;
    }

    /**
     * 格式化数据函数，您可以在这里直接将需要被进行格式化的数据传递进来！
     * <p>
     * Format data function, you can directly pass in the data that needs to be formatted here!
     *
     * @param data 要格式化的数据对象，会将其中的所有字段进行格式化操作，字段名字做为 key 字段类型做为value。
     * @return 格式化后的数据
     */
    @Override
    public final String format(Object data) {
        return this.format(data, true);
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
        if (data == null) {
            return this.format(new HashMap<>());
        }
        final Class<?> aClass = data instanceof Class ? (Class<?>) data : data.getClass();
        return this.format(data, aClass, getName ? StructuralNameCache.classToName(aClass) : null);
    }

    @Override
    public String format(Object data, Class<?> dataClassObj, String name) {
        return this.format(StructuralCache.classToMap(dataClassObj, data), name);
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
        return this.format(data, data instanceof DataObj ? ((DataObj) data).getName() : "map");
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
        try (final StringWriter stringWriter = new StringWriter();
             final PrintWriter printWriter = new PrintWriter(stringWriter)) {
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
     * @param name 在格式化操作中 需要做为 key 的名称
     * @return Map 对象被格式化操作执行之后的结果
     * <p>
     * The result of a Map object after being formatted
     */
    @Override
    public String format(Collection<?> data, String name) {
        try (final StringWriter stringWriter = new StringWriter();
             final PrintWriter printWriter = new PrintWriter(stringWriter)) {
            this.formatToStream(data, printWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void formatToStream(Object data, PrintWriter printWriter) {
        this.formatToStream(data, true, printWriter);
    }

    @Override
    public void formatToStream(Object data, boolean getName, PrintWriter printWriter) {
        if (data == null) {
            this.formatToStream(new HashMap<>(), printWriter);
            return;
        }
        final Class<?> aClass = data instanceof Class ? (Class<?>) data : data.getClass();
        this.formatToStream(data, aClass, getName ? StructuralNameCache.classToName(aClass) : null, printWriter);
    }

    @Override
    public void formatToStream(Object data, Class<?> dataClassObj, String name, PrintWriter printWriter) {
        this.formatToStream(StructuralCache.classToMap(dataClassObj, data), name, printWriter);
    }

    @Override
    public void formatToStream(Map<?, ?> data, PrintWriter printWriter) {
        this.formatToStream(data, data instanceof DataObj ? ((DataObj) data).getName() : "map", printWriter);
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
    public ManualFormatter clone() {
        try {
            return (ManualFormatter) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
