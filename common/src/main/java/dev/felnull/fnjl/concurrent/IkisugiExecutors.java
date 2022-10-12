package dev.felnull.fnjl.concurrent;

/**
 * イキスギたExecutor
 *
 * @author MORIMORI0317
 * @since 1.65
 */
public class IkisugiExecutors {
    /**
     * 呼び出しExecutorを作成
     *
     * @return InvokeExecutor
     */
    public static InvokeExecutor newInvokeExecutor() {
        return new InvokeExecutor();
    }
}
