package dev.felnull.fnjl.concurrent;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

/**
 * 呼び出されたときにタスクを実行するExecutor
 *
 * @author MORIMORI0317
 * @since 1.65
 */
public class InvokeExecutor implements Executor {
    private final ConcurrentLinkedQueue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    @Override
    public void execute(@NotNull Runnable command) {
        tasks.add(command);
    }

    /**
     * すべてのタスクを実行する
     *
     * @return 実行したタスク数
     */
    public int runTasks() {
        int ct = 0;
        while (!tasks.isEmpty()) {
            tasks.poll().run();
            ct++;
        }
        return ct;
    }

    /**
     * 最大指定した数のタスクをすべて実行する
     *
     * @param max 実行する最大タスク数
     * @return 実行したタスク数
     */
    public int runTasks(int max) {
        int ct = 0;
        while (!tasks.isEmpty()) {
            tasks.poll().run();
            ct++;
            if (ct >= max)
                break;
        }
        return ct;
    }

    /**
     * 現在の実行待ちタスクの数
     *
     * @return タスク数
     */
    public int getTaskCount() {
        return tasks.size();
    }
}
