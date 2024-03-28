package raf.dsw.gerumap.app.serializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.dsw.gerumap.app.core.Serializer;
import raf.dsw.gerumap.app.gui.swing.view.dialogs.SaveTemplateDialog;
import raf.dsw.gerumap.app.mapRepository.implementation.MindMap;
import raf.dsw.gerumap.app.mapRepository.implementation.Project;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JacksonSerializer implements Serializer {

    ObjectMapper mapper = new ObjectMapper();

    public JacksonSerializer(){
        SimpleModule awtModule = new SimpleModule("AWT Module");
        awtModule.addSerializer(Color.class, new ColorJsonSerializer());
        awtModule.addDeserializer(Color.class, new ColorJsonDeserializer());
        mapper.registerModule(awtModule);
    }

    @Override
    public Project loadProject(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            Project project = mapper.readValue(file, Project.class);
            return project;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveProject(Project project) {
        try (FileWriter writer = new FileWriter(project.getFilePath())) {
            mapper.setVisibilityChecker(
                    mapper.getSerializationConfig()
                            .getDefaultVisibilityChecker()
                            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
            );
            String mastcina = mapper.writeValueAsString(project);
            writer.write(mastcina);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveTemplate(MindMap map) {

        String mapPath = getClass().getResource("/templates/").getPath();
        //mapPath += map.getName() + ".json";
        SaveTemplateDialog std = new SaveTemplateDialog();
        mapPath += std.getIme() + ".json";
        File file = new File(mapPath);

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter(file)) {
            mapper.setVisibilityChecker(
                    mapper.getSerializationConfig()
                            .getDefaultVisibilityChecker()
                            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
            );
            String mastcina = mapper.writeValueAsString(map);
            writer.write(mastcina);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MindMap loadTemplate(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            MindMap mapa = mapper.readValue(file, MindMap.class);
            return mapa;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    class ColorJsonSerializer extends JsonSerializer<Color> {

        @Override
        public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeNull();
                return;
            }
            gen.writeNumber(value.getRGB());
        }
    }

    class ColorJsonDeserializer extends JsonDeserializer<Color> {

        @Override
        public Color deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new Color(p.getValueAsInt());
        }
    }
}
