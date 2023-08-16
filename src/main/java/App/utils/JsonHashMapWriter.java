package App.utils;

import App.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JsonHashMapWriter {

    public synchronized void writeUserHashMap(User user) {
        JsonHashMapReader reader = new JsonHashMapReader();
        HashMap<Long, User> usersMap = reader.getUsersHashMap();

        usersMap.remove(user.getChatId());
        usersMap.put(user.getChatId(), user);
        writeToFile(usersMap);
    }

    public synchronized void writeToFile(HashMap<Long, User> usersMap) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("src/main/resources/users_tg.json"), usersMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}