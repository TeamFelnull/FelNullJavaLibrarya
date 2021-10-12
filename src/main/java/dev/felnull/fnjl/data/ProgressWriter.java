package dev.felnull.fnjl.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 進行率を取得しながら書き込む
 *
 * @author MORIMORI0317
 * @since 1.8あたり
 */
public class ProgressWriter {
    private final InputStream stream;
    private final long length;
    private final Function<WriteData, IOException> writer;
    private final Consumer<WriteProgressListener> progress;

    public ProgressWriter(InputStream stream, long length, Function<WriteData, IOException> writer, Consumer<WriteProgressListener> progress) {
        this.stream = stream;
        this.length = length;
        this.writer = writer;
        this.progress = progress;
    }

    public void start() throws IOException {
        BufferedInputStream bstream = new BufferedInputStream(stream);
        byte[] data = new byte[1024];
        long ct = 0;
        int x;
        while ((x = bstream.read(data, 0, 1024)) >= 0) {
            int finalX = x;
            ct += x;
            IOException ex = writer.apply(new WriteData() {
                @Override
                public byte[] getBytes() {
                    return data;
                }

                @Override
                public long getReadSize() {
                    return finalX;
                }
            });

            long finalCt = ct;
            progress.accept(new WriteProgressListener() {
                @Override
                public long getLength() {
                    return length;
                }

                @Override
                public long getWrittenLength() {
                    return finalCt;
                }
            });

            if (ex != null)
                throw ex;
        }
    }

    public static interface WriteData {
        byte[] getBytes();

        long getReadSize();
    }

    public static interface WriteProgressListener {
        long getLength();

        long getWrittenLength();

        default double getProgress() {
            return (float) getWrittenLength() / (float) getLength();
        }
    }
}
