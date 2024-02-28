package top.lingyuzhao.varFormatter.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 类与Map 结构的缓冲池，在这里将可以自动的将类的结构解析出来，并建立Map的结构
 * <p>
 * A buffer pool for classes and Map structures, where the class structure can be automatically parsed and the Map structure can be established
 *
 * @author zhao
 */
public final class StructuralCache {
    /**
     * 类与 Map 结构的缓冲池，能够减少反射代价。为了安全考虑，此缓冲池不得进行修改！！！
     * <p>
     * A buffer pool with class and Map structures can reduce reflection costs. For safety reasons, this buffer pool cannot be modified!!!
     */
    private final static HashMap<Class<?>, Field[]> CACHE = new HashMap<>();

    static {
        CACHE.put(null, new Field[0]);
    }

    /**
     * 将一个类的结构直接解析为 Map 的结构，便于其它操作所需。
     *
     * @param dataClass 需要被解析的类
     * @param data      对应的实例
     * @return 解析之后的 Map 结构（如果解析失败，返回null）
     */
    public static Map<?, ?> classToMap(Class<?> dataClass, Object data) {
        return classToMap(dataClass, data, new HashMap<>());
    }

    /**
     * 将一个类的结构直接解析为 Map 的结构，便于其它操作所需。
     *
     * @param dataClass 需要被解析的类
     * @param data      对应的实例
     * @param map       用来存储数据的 Map 集合 如果在外界定义好了，不需要重新创建 可以在这里指定
     * @return 解析之后的 Map 结构（如果解析失败，返回null）
     */
    public static Map<?, ?> classToMap(Class<?> dataClass, Object data, HashMap<String, Object> map) {
        final Field[] declaredFields;
        // 检查这个类是否被缓存过
        {
            final Field[] map1 = CACHE.get(dataClass);
            if (map1 != null) {
                declaredFields = map1;
            } else {
                // 没有缓存过就解析结构 然后缓存
                declaredFields = dataClass.getDeclaredFields();
                // 缓存
                CACHE.put(dataClass, declaredFields);
            }
        }
        // 最后计算
        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                final Object o = declaredField.get(data);
                map.put(declaredField.getName(), o);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return map;
    }
}
