package top.lingyuzhao.varFormatter.core;

import top.lingyuzhao.utils.StrUtils;

import java.util.HashMap;

/**
 * 类与类名字的缓冲池，在这里将可以自动的将类的名字解析出来并进行缓存！
 * <p>
 * <p>
 * A buffer pool for classes and class names, where class names can be automatically parsed and cached!
 *
 * @author zhao
 */
public final class StructuralNameCache {
    /**
     * 类与 Map 结构的缓冲池，能够减少反射代价。为了安全考虑，此缓冲池不得进行修改！！！
     * <p>
     * A buffer pool with class and Map structures can reduce reflection costs. For safety reasons, this buffer pool cannot be modified!!!
     */
    private final static HashMap<Class<?>, String> CACHE = new HashMap<>();

    static {
        CACHE.put(null, "root");
    }

    /**
     * 将一个类的结构直接解析为 类的名字，便于其它操作所需。
     *
     * @param dataClass 需要被解析的类
     * @return 解析之后的 Map 结构（如果解析失败，返回null）
     */
    public static String classToName(Class<?> dataClass) {
        final String name;
        // 检查这个类是否被缓存过
        {
            final String name_temp = CACHE.get(dataClass);
            if (name_temp != null) {
                name = name_temp;
            } else {
                // 没有缓存过就解析结构 然后缓存
                final String[] strings = StrUtils.splitBy(dataClass.getName(), '$');
                name = strings[strings.length - 1];
                // 缓存
                CACHE.put(dataClass, name);
            }
        }
        return name;
    }
}
