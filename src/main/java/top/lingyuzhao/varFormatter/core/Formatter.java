package top.lingyuzhao.varFormatter.core;


import java.util.Collection;
import java.util.Map;

/**
 * 格式化器对象，此类能够直接作用在数据上，能够进行格式化操作，您可以通过 VarFormatter.getFormatter() 获取一个格式化器对象。
 * <p>
 * Formatter object, which can directly act on data and perform formatting operations. You can obtain a formatter object through VarFormatter. getFormatter().
 *
 * @author zhao
 */
public interface Formatter extends Cloneable {

    /**
     * @return 当前格式化组件的型号，同时也是能够进行格式化的数据的类型。
     * <p>
     * The current model of the formatted component, which is also the type of data that can be formatted.
     */
    VarFormatter getFormatterType();

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
    String format(Map<?, ?> data);

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
    String format(Map<?, ?> data, String name);

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
    String format(Collection<?> data, String name);

    /**
     * 格式化数据函数，您可以在这里直接将需要被进行格式化的数据传递进来！
     * <p>
     * Format data function, you can directly pass in the data that needs to be formatted here!
     *
     * @param data    要格式化的数据对象，会将其中的所有字段进行格式化操作，字段名字做为 key 字段类型做为value。
     * @param getName 在格式化操作开始之前，先获取到当前类的名字，用来进行根节点生成，有些数据类型可能会需要，有些则不会需要 如果不需要就设置为 false
     *                <p>
     *                Before starting the formatting operation, obtain the name of the current class for root node generation. Some data types may require it, while others may not
     * @return 格式化后的数据
     */
    String format(Object data, boolean getName);

    /**
     * 格式化数据函数，您可以在这里直接将需要被进行格式化的数据传递进来！
     * <p>
     * Format data function, you can directly pass in the data that needs to be formatted here!
     *
     * @param data 要格式化的数据对象，会将其中的所有字段进行格式化操作，字段名字做为 key 字段类型做为value。
     * @return 格式化后的数据
     */
    String format(Object data);

    /**
     * @return 将当前的格式化器进行克隆和复制，获取到一个新格式化器对象。
     * <p>
     * Clone and copy the current formatter to obtain a new formatter object.
     */
    Formatter clone();
}
