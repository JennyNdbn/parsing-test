package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.MeJson;
import org.junit.jupiter.api.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParsingTest {
    ClassLoader cl = JsonParsingTest.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Парсинг Json-файла")
    void jsonParse() throws Exception {

        try (
                InputStream resource = cl.getResourceAsStream("Test.json")
        ) {
            assert resource != null;
            try (InputStreamReader reader = new InputStreamReader(resource)
            ) {
                MeJson manJson = objectMapper.readValue(reader, MeJson.class);
                assertThat(manJson.firstName).isEqualTo("Evgeniia");
                assertThat(manJson.lastName).isEqualTo("Nadobnaia");
                assertThat(manJson.age).isEqualTo(28);
                assertThat(manJson.occupation).isEqualTo("Engineer");
                assertThat(manJson.languages).contains("Spanish", "English");
            }
        }
    }
}
