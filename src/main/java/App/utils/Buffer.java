package App.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Buffer {
    private final List<Long> syncList = Collections.synchronizedList(new ArrayList<>());

    public void add(Long chatId) {
        syncList.add(chatId);
    }

    public void delete(Long chatId) {
        syncList.remove(chatId);
    }

    public boolean isExist(Long chatId) {
        return syncList.contains(chatId);
    }
}
