package top.lingyuzhao.varFormatter.core;

/**
 * 格式化器类型，此类型用于标记不同的数据的格式化类型。
 * <p>
 * Formatter type, which is used to mark the formatting types of different data.
 */
public enum VarFormatter {

    JSON(new JsonFormatter()),
    XML(new XmlFormatter()),
    //    CSV(null),
//    TEXT(null),
    HTML(new HtmlFormatter());

    /**
     * 格式化器的对象，在这里存储的就是当前类型的格式化器的实现。
     * <p>
     * The object of the formatter, stored here, is the implementation of the current type of formatter.
     */
    private final Formatter formatter;

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
