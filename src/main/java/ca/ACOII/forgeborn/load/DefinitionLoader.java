package ca.ACOII.forgeborn.load;

import ca.ACOII.forgeborn.core.model.Frame;
import ca.ACOII.forgeborn.core.model.Part;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DefinitionLoader {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static List<Frame> loadFrames() {
        try (InputStream inStrm = DefinitionLoader.class.getResourceAsStream("/defs/frames.json")) {
            if (inStrm == null) throw new RuntimeException("frames.json not found");
            return mapper.readValue(inStrm, mapper.getTypeFactory().constructCollectionType(List.class, Frame.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read frames.json | ", e);
        }
    }
    public static List<Part> loadParts() {
        try (InputStream inStrm = DefinitionLoader.class.getResourceAsStream("/defs/parts.json")) {
            if (inStrm == null) throw new RuntimeException("parts.json not found");
            return mapper.readValue(inStrm, mapper.getTypeFactory().constructCollectionType(List.class, Part.class));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read parts.json | ", e);
        }
    }
    public static void resolve() {

    }
}
