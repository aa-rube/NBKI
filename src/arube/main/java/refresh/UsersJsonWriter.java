package refresh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.User;

import java.io.File;
import java.util.List;

public class UsersJsonWriter {

    public void writeJason(List<User> users) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.putPOJO("users", users);

        try {
            mapper.writeValue(new File("src/arube/main/resources/users_in.json"), rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
