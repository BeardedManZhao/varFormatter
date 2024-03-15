package top.lingyuzhao.varFormatter.core;

/**
 * 格式化器类型，此类型用于标记不同的数据的格式化类型。
 * <p>
 * Formatter type, which is used to mark the formatting types of different data.
 */
public enum VarFormatter {

    /**
     * 将任意对象转换为 json 字符串的格式化组件类型，通过此类型可以直接获取到对应的格式化组件。
     * <p>
     * Convert any object to a JSON string format component type, through which the corresponding format component can be directly obtained.
     */
    JSON(new JsonFormatter()),

    /**
     * 将任意对象转换为 xml 字符串的格式化组件类型，通过此类型可以直接获取到对应的格式化组件。
     * <p>
     * Convert any object to a xml string format component type, through which the corresponding format component can be directly obtained.
     */
    XML(new XmlFormatter()),

    /**
     * 将任意对象转换为 html 字符串的格式化组件类型，通过此类型可以直接获取到对应的格式化组件。
     * <p>
     * Convert any object to a html string format component type, through which the corresponding format component can be directly obtained.
     */
    HTML(new HtmlFormatter()),

    /**
     * 将任意对象转换为 yaml 字符串的格式化组件类型，通过此类型可以直接获取到对应的格式化组件。
     * <p>
     * Convert any object to a yaml string format component type, through which the corresponding format component can be directly obtained.
     */
    YAML(new YamlFormatter()),

    /**
     * 将任意对象转换为 mermaid 字符串的格式化组件类型，通过此类型可以直接获取到对应的格式化组件。
     * <p>
     * Convert any object to a mermaid string format component type, through which the corresponding format component can be directly obtained.
     */
    MERMAID(new MermaidFormatter()),

    /**
     * 将任意的对象，转换为一个python类的代码的格式化转换组件，其可以将任何类型的数据转换为 python 代码！
     * <p>
     * Convert any object into a formatted conversion component of Python class code, which can convert any type of data into Python code!
     */
    J_TO_PYTHON(new JToPythonFormatter());

    /**
     * 格式化器的对象，在这里存储的就是当前类型的格式化器的实现。
     * <p>
     * The object of the formatter, stored here, is the implementation of the current type of formatter.
     */
    private final Formatter formatter;

    /**
     * 实例化一个类型的格式化器对象。
     *
     * @param formatter 此类型对应的格式化器对象。
     */
    VarFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    /**
     * 获取格式化器对象，此函数支持使用单例或者克隆的方式获取到格式化对象。
     * <p>
     * Get the formatter object, this function supports obtaining the formatted object using singleton or clone methods.
     *
     * @param singleton 是否使用单例模式 如果需要使用则设置为 true 这样的所获取到的所有格式化组件都是同一个内存地址的。
     *                  <p>
     *                  Whether to use singleton mode. If necessary, set it to true. All formatting components obtained are at the same memory address.
     * @return 格式化器对象
     * <p>
     * Formatter Object
     */
    public Formatter getFormatter(boolean singleton) {
        return singleton ? formatter : formatter.clone();
    }

}
