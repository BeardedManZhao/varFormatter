package top.lingyuzhao.varFormatter.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 通用数据对象，此数据对象将可以免去对象检查，直接被做为 Map 进行使用，因此对于此对象的解析是非常快速的，此对象还适合做为复杂的嵌套操作！
 * <p>
 * Universal data object, this data object can be used directly as a Map without object checking, so the parsing of this object is very fast, and it is also suitable for complex nested operations!
 *
 * @author zhao
 */
public class DataObj extends LinkedHashMap<String, Object> implements Serializable {
    /**
     * 序列化编号
     * <p>
     * Serialization number
     */
    private static final long serialVersionUID = 1L;

    /**
     * 当前数据对象的名称，可能会被格式化器用作根节点或其它部分。
     * <p>
     * The name of the current data object may be used by the formatter as a root node or other part.
     */
    private final String name;

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
    public DataObj(String name, DataObj... dataObjs) {
        this.name = name;
        for (DataObj dataObj : dataObjs) {
            this.put(dataObj.getName(), dataObj);
        }
    }

    /**
     * 获取当前数据对象的名称
     * <p>
     * Get the name of the current data object
     *
     * @return 当前数据对象的名称，可能会被格式化器用作根节点或其它部分。
     * <p>
     * The name of the current data object may be used by the formatter as a root node or other part.
     */
    public String getName() {
        return name;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param dataObj 需要被做为子节点的节点数据对象。
     *                <p>
     *                Node data objects that need to be treated as child nodes.
     */
    public void put(DataObj dataObj) {
        super.put(dataObj.getName(), dataObj);
    }
}
