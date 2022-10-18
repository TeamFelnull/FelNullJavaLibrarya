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
     * タスクを実行する
     */
    public void runTasks() {
        while (!tasks.isEmpty())
            tasks.poll().run();
    }

    /**
     * タスクを実行する
     *
     * @param max 実行する最大タスク数
     */
    public void runTasks(int max) {
        int ct = 0;
        while (!tasks.isEmpty()) {
            tasks.poll().run();
            ct++;
            if (ct >= max)
                break;
        }
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
