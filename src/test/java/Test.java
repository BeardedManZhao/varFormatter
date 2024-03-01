import top.lingyuzhao.varFormatter.core.Formatter;
import top.lingyuzhao.varFormatter.core.VarFormatter;
import top.lingyuzhao.varFormatter.utils.XmlNodeObj;

/**
 * 测试类
 *
 * @author zhao
 */
public class Test {

    public static void main(String[] args) {
        // 使用单例模式 获取到 HTML 格式化组件
        final Formatter formatter0 = VarFormatter.HTML.getFormatter(true);
        // 构建一个 body 和 html 标签
        final XmlNodeObj body = new XmlNodeObj("body");
        final XmlNodeObj xmlNodeObj = new XmlNodeObj("html", body);
        // 设置 html 标签的 lang 属性
        xmlNodeObj.setAttr("lang", "zh");

        // 设置body标签内部的标签
        body.put("p", "这里是一些段落文本");
        // 在body标签内部添加一个div标签
        final XmlNodeObj div = new XmlNodeObj("div");
        // 设置 div 标签的属性 这里是设置的字体颜色
        div.setAttr("style", "color:#0f0");
        // 设置 div 标签内部的文本
        div.put("div", "这里是一些 div 中的段落文本");
        // 把 div 标签提供给 body
        body.put(div);

        // 直接打印出 HTML 格式的文本
        System.out.println(formatter0.format(xmlNodeObj));
    }
}