package top.lingyuzhao.varFormatter.utils;

import java.util.HashMap;

/**
 * XML 中的节点对象，此节点对象类型继承自 DataObj 因此速度与性能较快，其拓展了一个属性键值对的操作，允许在 xml 节点中添加属性和配置。
 * <p>
 * The node object in XML inherits the type of DataObj, making it faster and more efficient. It extends the operation of attribute key value pairs, allowing for the addition of attributes and configurations in XML nodes.
 *
 * @author zhao
 */
public class XmlNodeObj extends DataObj {
    /**
     * 节点属性存储集合，用于存储某个节点的属性键值对。
     * <p>
     * Node attribute storage collection, used to store the attribute key value pairs of a node.
     */
    private final HashMap<String, Object> hashMap = new HashMap<>();

    /**
     * 当前数据对象的构造函数
     * <p>
     * The constructor of the current data object
     *
     * @param name     当前数据对象的名称，可能会被格式化器用作根节点或其它部分。
     *                 <p>
     *                 The name of the current data object may be used by the formatter as a root node or other part.
     * @param dataObjs 当前数据对象内部需要存储的子数据对象
     *                 <p>
     *                 The child data objects that need to be stored inside the current data object
     */
    public XmlNodeObj(String name, DataObj... dataObjs) {
        super(name, dataObjs);
    }

    /**
     * 获取到当前节点中的某个属性的值，如果值不存在则直接返回null
     * <p>
     * Retrieve the value of a property in the current node, and return null if the value does not exist
     *
     * @param key 需要获取到的目标属性对应的名字
     *            <p>
     *            The name corresponding to the target attribute that needs to be obtained
     * @return 指定的 key 对应的值，如果值不存在则直接返回null
     * <p>
     * The value corresponding to the specified key, if the value does not exist, returns null directly
     */
    public Object getAttr(String key) {
        return hashMap.get(key);
    }

    /**
     * 设置当前节点对象的属性值
     *
     * @param key   当前节点属性对应的 key
     * @param value key 对应的配置项的值
     */
    public void setAttr(String key, String value) {
        hashMap.put(key, value);
    }

    /**
     * 获取当前节点对象的属性值的字符串
     * <p>
     * The string of attribute values for the current node object
     *
     * @return 当前节点对象的属性值的字符串
     * <p>
     * The string of attribute values for the current node object
     */
    public String getAttrStr() {
        StringBuilder stringBuilder = new StringBuilder();
        hashMap.forEach((k, v) -> {
            stringBuilder.append(' ').append(k).append('=');
            if (v instanceof String) {
                stringBuilder.append('"').append(v).append('"');
            } else {
                stringBuilder.append(v.toString());
            }
        });
        return stringBuilder.toString();
    }
}
