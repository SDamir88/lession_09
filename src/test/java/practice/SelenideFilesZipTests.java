package practice;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import practice.domain.SDamir;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import org.codehaus.jackson.map.ObjectMapper;

public class SelenideFilesZipTests {
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void pdfTest() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/files/files.zip");
        ZipEntry zipEntry = zipFile.getEntry("junit-user-guide-5.9.1.pdf");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        PDF pdf = new PDF(inputStream);
        assertThat(pdf.text).contains("JUnit 5 User Guide");
    }

    @Test
    void xlsTest() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/files/files.zip");
        ZipEntry zipEntry = zipFile.getEntry("prajs_ot_2311.xls");
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        XLS xls = new XLS(inputStream);
        assertThat(xls.excel
                .getSheetAt(0)
                .getRow(12)
                .getCell(1)
                .getStringCellValue()).contains("636");
    }

    @Test
    void csvTest() throws Exception {
        ZipFile zipFile = new ZipFile("src/test/resources/files/files.zip");
        ZipEntry zipEntry = zipFile.getEntry("business-financial-data-june-2022-quarter-csv.csv");
        try (InputStream inputStream = zipFile.getInputStream(zipEntry);
             CSVReader csv = new CSVReader(new InputStreamReader(inputStream))) {
            List<String[]> content = csv.readAll();
            assertThat(content.get(1)).contains("Dollars");
        }
    }

    @Test
    void jsonTypeTest2() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
        SDamir sdamir = mapper.readValue(Paths.get("src/test/resources/files/simple.json").toFile(), SDamir.class);
        assertThat(sdamir.name).isEqualTo("Damir");
        assertThat(sdamir.address.street).isEqualTo("Kirov");
        }
}
