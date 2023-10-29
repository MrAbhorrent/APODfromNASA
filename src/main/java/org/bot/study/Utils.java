package org.bot.study;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    public static String readFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(";");
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
