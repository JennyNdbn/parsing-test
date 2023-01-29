package guru.qa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ZipParsingTest {
    ClassLoader cl = ZipParsingTest.class.getClassLoader();

    @Test
    @DisplayName("Парсинг Zip-архива")
    void zipParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("Test.zip")
        ) {
            assert resource != null;
            try (ZipInputStream zis = new ZipInputStream(resource)
            ) {
                ZipEntry entry;
                while((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().contains("people.csv")) {
                        CSVReader reader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> content = reader.readAll();
                        assertThat(content.get(1)[0]).contains("Кощеев Лев Ананьевич");
                    } else if (entry.getName().endsWith(".pdf")) {
                        PDF content = new PDF(zis);
                        assertThat(content.text).contains("Импорт параметров из СК-11");
                    } else if (entry.getName().contains(".xlsx")) {
                        XLS content = new XLS(zis);
                        assertThat(content.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue())
                                .contains("Отдел развития энергосистем и энергообъектов");
                    }
                }
            }
        }
    }
}
