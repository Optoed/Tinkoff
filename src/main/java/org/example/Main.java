package org.example;

import org.example.controller.UrlController;
import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepositoryImpl;
import org.example.service.UrlServiceImpl;
import org.example.service.model.Url;
import org.example.utils.ReadUtils;

import java.net.URL;

public class Main {
    public static void main(String[] args) {

        while (true) {
            UrlController urlController = new UrlController(new UrlServiceImpl(new UrlRepositoryImpl()));
            printMenu();
            String chosenService = ReadUtils.readLine();

            if (chosenService.equals("1")) {
                System.out.println("Введите исходный (длинный) URL-адрес");
                String longURLString = "";
                while (longURLString == "") {
                    longURLString = readAndValidateURL();
                }
                UrlDto urlDto = urlController.addUrl(new UrlDto(longURLString));
                System.out.printf("Добавлен/найден URL-адрес \n С идентификатором %s \n Длинный (long) URL-адрес: %s\n Сокращенный (short) URL-адрес: %s\n%n",
                        urlDto.id(), urlDto.longURL(), urlDto.shortURL()
                );

            } else if (chosenService.equals("2")) {
                System.out.println("Введите сокращенный (short) URL-адрес:");
                String shortURLString = "";
                while (shortURLString.equals("")) {
                    shortURLString = readAndValidateURL();
                }
                try {
                    UrlDto inputUrlDto = new UrlDto("", shortURLString);
                    UrlDto urlDto = urlController.getUrl(inputUrlDto);

                    System.out.printf(
                            "Найден URL-адрес \n С идентификатором %s \n Длинный (long) URL-адрес: %s\n Сокращенный (short) URL-адрес: %s\n%n",
                            urlDto.id(), urlDto.longURL(), urlDto.shortURL()
                    );
                } catch (EntityNotFoundException ex) {
                    System.out.printf("URL-адрес с таким сокращенным (short) URl %s не найден%n", shortURLString);
                }

            } else if (chosenService.equals("3")) {
                return;

            } else {
                System.out.println("Выберите валидный вариант");
            }
        }
    }

    private static String readAndValidateURL() {

        // можно ещё потом проверять чтобы были только допустимые символы, но думаю не стоит пока что

        try {
            String URLString = ReadUtils.readLine();
            //если пустая строка или состоит только из пробелов
            if (URLString.isBlank()) {
                System.out.println("Введена пустая строка. Введите url-адрес, содержащий символы");
                return "";
            }

            if (URLString.length() > 8) {
                if (URLString.substring(0, 8).equals("https://")) {
                    return URLString;
                }
            }
            if (URLString.length() > 7) {
                if (URLString.substring(0, 7).equals("http://")) {
                    return URLString;
                }
            }


            System.out.println("Неправильный формат URL-адреса. Должен начинаться с https:// (или http://)" +
                    " и содержать после хотя бы один символ");

            return "";

        } catch (StringIndexOutOfBoundsException exception) {
            System.out.println("Неправильный формат URL-адреса. Должен начинаться с https:// (или http://)" +
                    " и содержать после хотя бы один символ");
        }
        return "";
    }


    private static void printMenu() {
        System.out.println("""
                
                
                Приложения для работы с url-адресами
                                
                Выберите действие:
                                
                1. Добавить url-адрес в укороченном формате
                2. Найти исходный url-адрес по укороченной версии shortURL
                3. Выйти
                                
                """);
    }


}