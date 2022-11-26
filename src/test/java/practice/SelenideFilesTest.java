package practice;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import practice.domain.SDamir;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFilesTest {
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    void selenideDownloadTest() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadedFile = Selenide.$("#raw-url").download();
        try (InputStream is = new FileInputStream(downloadedFile)) {
            assertThat(new String(is.readAllBytes(), UTF_8)).contains("This repository is the home of the next generation of JUnit");
        }
    }

    @Test
    void uploadSelenideTest() {
        Selenide.open("https://the-internet.herokuapp.com/upload");
        Selenide.$("input[type='file']")
//                .uploadFile(new File("\Users\onyxx\lession_09\src\test\resources\Files\1.txt")); // bad practice!
                .uploadFromClasspath("files/1.txt");
        Selenide.$("#file-submit").click();
        Selenide.$("div.example").shouldHave(Condition.text("File Uploaded!"));
        Selenide.$("#uploaded-files").shouldHave(Condition.text("1.txt"));
    }

    @Test
    void jsonTypeTest() throws Exception {
        Gson gson = new Gson();
        try (InputStream is = classLoader.getResourceAsStream("files/simple.json")) {
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            SDamir jsonObject = gson.fromJson(json, SDamir.class);
            assertThat(jsonObject.name).isEqualTo("Damir");
            assertThat(jsonObject.address.street).isEqualTo("Kirov");
        }
    }

}
