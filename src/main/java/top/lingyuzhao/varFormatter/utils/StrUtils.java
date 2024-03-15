package top.lingyuzhao.varFormatter.utils;

/**
 * @author zhao
 */
public final class StrUtils {

    /**
     * 将一个字符串中的一些转义字符，变为普通字符串，例如换行符转换为\\n形式，并返回结果
     *
     * @param data 被转义之后的字符串
     * @return 转换后的字符串
     */
    public static String escapeNewline(String data) {
        StringBuilder sb = new StringBuilder(data);
        sb.setLength(0); // 清空StringBuilder内容，以便复用
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            switch (c) {
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 将驼峰形式的字符串转换为下划线形式的字符串
     *
     * @param data 需要被转换的字符串
     * @return 转换之后的字符串
     */
    public static String CamelToSnake(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            final char c = data.charAt(i);
            if (Character.isUpperCase(c)) {
                stringBuilder.append("_");
                stringBuilder.append(Character.toLowerCase(c));
            } else {
                stringBuilder.append(c);
            }
        }
        stringBuilder.setCharAt(0, Character.toLowerCase(data.charAt(0)));
        return stringBuilder.toString();
    }

    /**
     * 将下划线形式的字符串转换为驼峰形式的字符串
     *
     * @param data 需要被转换的字符串
     * @return 转换之后的字符串
     */
    public static String snakeToCamel(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            final char c = data.charAt(i);
            if (c == '_') {
                stringBuilder.append(Character.toUpperCase(data.charAt(i + 1)));
                i++;
            } else {
                stringBuilder.append(c);
            }
        }
        stringBuilder.setCharAt(0, Character.toUpperCase(data.charAt(0)));
        return stringBuilder.toString();
    }


}
