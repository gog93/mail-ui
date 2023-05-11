package org.medical.hub.marker.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@EnableScheduling
public class ChatServiceImpl {
    //    @Value("${folder.path}")
    private static String baseUrl = "http://localhost:8081/template";


    //    @Value("${base.url}")
    private static String folderPath = "C:\\Users\\on-off\\IdeaProjects\\mail-ui\\mail-ui\\folder\\example1.txt";

    public void write(String search) {
        File file = new File(folderPath);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(search); // write the text to the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 1000)
    public String read() {
        String line = "";
        StringBuilder fileContents = new StringBuilder();

        try {
            Thread.sleep(2000);

            // Open the file for reading
            BufferedReader reader = new BufferedReader(new FileReader(folderPath));

            // Read the contents of the file
            while ((line = reader.readLine()) != null) {
                fileContents.append(line);
                fileContents.append(System.lineSeparator());
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return fileContents.toString();
    }

}
