package App.utils;

import App.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JsonHashMapReader {

    public synchronized HashMap<Long, User> getUsersHashMap() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());

        HashMap<Long, User> usersMap = new HashMap<>();

        try {
            usersMap = mapper.readValue(new File("src/main/resources/users_tg.json"),
                    new TypeReference<HashMap<Long, User>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usersMap;
    }
}