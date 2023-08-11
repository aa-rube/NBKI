package Multithread;

import model.User;

import java.io.File;
import java.util.List;

public class Partition {
    private static void startTheProgram(List<User> users) {
        Runtime runtime = Runtime.getRuntime();
        int threads = runtime.availableProcessors() - 1;

        assert users != null && users.size() > 0;
        int part = users.size() / threads;

        for (int i = 0; i < threads; i++) {
            File[] files;

            if (i < threads - 1) {
                files = new File[part];
            } else {
                files = new File[users.size() - (part * i)];
            }
            resizerInThreads(users, files, part, i, dstFolder, System.currentTimeMillis());
        }
    }

    public static void resizerInThreads(File[] allFiles, File[] files, int part, int i, String dst, long start) {
        System.arraycopy(allFiles, part * i, files, 0, files.length);
        ResizerImage resizer = new ResizerImage(files, newWidth, dst, start);
        resizer.start();
    }
}
