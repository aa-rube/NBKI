package reportWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Report;


import java.io.File;
import java.util.List;

public class ReportWrite {
    public void writeJason(List<Report> reportList) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.putPOJO("report", reportList);

        try {
            mapper.writeValue(new File("src/arube/main/resources/users_out.json"), rootNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        reportList.clear();
    }
}
