package dev.felnull.fnjl;

import dev.felnull.fnjl.util.FNStringUtil;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 進捗率をわかりやすくバーで表示させる
 *
 * @author MORIMORI0317
 * @since 1.15
 */
public class ProgressLogger {
    private int size;
    private int complete;
    private int total;
    private BarType barType;
    private Consumer<String> logger = n -> System.out.print(n + "\r");
    private Function<ProgressLogger, String> startStr = n -> FNStringUtil.getPercentage(n.complete, n.total) + " ";
    private Function<ProgressLogger, String> endStr = n -> " " + n.getComplete() + "/" + n.getTotal();

    /**
     * 進捗度バー表示
     *
     * @param size    進捗度バーの大きさ
     * @param total   合計数
     * @param barType バータイプ
     */
    public ProgressLogger(int size, int total, BarType barType) {
        this(size, 0, total, barType);
    }

    /**
     * 進捗度バー表示
     *
     * @param size     進捗度バーの大きさ
     * @param complete 書記達成率
     * @param total    合計数
     * @param barType  バータイプ
     */
    public ProgressLogger(int size, int complete, int total, BarType barType) {
        this.size = size;
        this.complete = complete;
        this.total = total;
        this.barType = barType;
    }

    public BarType getBarType() {
        return barType;
    }

    public void setBarType(BarType barType) {
        this.barType = barType;
    }

    public int getComplete() {
        return complete;
    }

    public int getSize() {
        return size;
    }

    public int getTotal() {
        return total;
    }

    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setTotal(int total) {
        this.total = total;
    }


    public String getProgressBar(int complete) {
        setComplete(complete);
        return getProgressBar();
    }

    public String getProgressBar() {
        StringBuilder sb = new StringBuilder();
        float b = (float) complete / total;
        int ct = (int) ((float) size * b);
        for (int i = 0; i < ct; i++) {
            if (i == ct - 1) {
                sb.append(barType.getTip());
                break;
            }
            sb.append(barType.getFill());
        }
        for (int i = 0; i < size - ct; i++) {
            sb.append(barType.getEmpty());
        }
        return sb.toString();
    }

    /**
     * 更新するたびに呼び出せば進捗が表示される
     *
     * @param complete 達成率
     */
    public void printProgress(int complete) {
        setComplete(complete);
        printProgress();
    }

    public void setEndStr(Function<ProgressLogger, String> endStr) {
        this.endStr = endStr;
    }

    public void setStartStr(Function<ProgressLogger, String> startStr) {
        this.startStr = startStr;
    }

    public void printProgress() {
        logger.accept((startStr != null ? startStr.apply(this) : "") + barType.getStart() + getProgressBar() + barType.getEnd() + (endStr != null ? endStr.apply(this) : ""));
    }

    public static enum BarType {
        CUBE("■", "□", "■", "", ""),
        ARROW(">", " ", "=", "[", "]"),
        SHARP("#", " ", "#", "[", "]");
        private final String tip;
        private final String empty;
        private final String fill;
        private final String start;
        private final String end;

        private BarType(String tip, String empty, String fill, String start, String end) {
            this.tip = tip;
            this.empty = empty;
            this.fill = fill;
            this.start = start;
            this.end = end;
        }

        public String getEmpty() {
            return empty;
        }

        public String getFill() {
            return fill;
        }

        public String getTip() {
            return tip;
        }

        public String getEnd() {
            return end;
        }

        public String getStart() {
            return start;
        }
    }
}
