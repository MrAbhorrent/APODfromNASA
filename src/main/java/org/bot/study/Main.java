package org.bot.study;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.time.LocalDate;

public class Main {
    private static final String WELCOME = "-= Программа получения картинки дня с сайта NASA =-";
    private static final String FILE_WITH_KEY = "api.key";

    public static void main(String[] args) {
        System.out.println(WELCOME);
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = currentDate.minusDays(10);
        String apiKey = readAPIKey(FILE_WITH_KEY);
        System.out.println(apiKey);
        System.out.printf("Выбранная дата - %s%n", selectedDate);
        String url = String.format("https://api.nasa.gov/planetary/apod?api_key=%s&date=%s", apiKey, selectedDate);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    APODModel apodModel = mapper.readValue(httpEntity.getContent(), APODModel.class);
                    System.out.printf("Тип полученного медиа-контента - %s%nURL полученного медиа-контента - %s", apodModel.getMediaType(), apodModel.geturl());
                    if (apodModel.getMediaType().equals("image")) {
                        String urlImage = apodModel.geturl();
                        response = httpClient.execute(new HttpGet(urlImage));
                        String[] ulrPath = apodModel.geturl().split("/");
                        String fileName = ulrPath[ulrPath.length - 1];
                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                        response.getEntity().writeTo(fileOutputStream);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readAPIKey(String configFile) {

        String contentFile = readFile(configFile);
        //TODO: Необходимо проверить что мы действительно прочитали ключ
        String result = contentFile;
        return result;
    }

    private static String readFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Файл с ключом доступа не найден");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println();
        return stringBuilder.toString();
    }
}
