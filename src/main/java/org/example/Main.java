package org.example;

import org.example.controller.UrlController;
import org.example.controller.dto.UrlDto;
import org.example.exception.EntityNotFoundException;
import org.example.repository.UrlRepositoryImpl;
import org.example.service.UrlServiceImpl;
import org.example.service.model.Url;
import org.example.utils.ReadUtils;

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
                String id = urlController.addUrl(new UrlDto(longURLString));
                System.out.printf("Создан исходный URL-адрес %s \n С идентификатором %s%n", longURLString, id);

            } else if (chosenService.equals("2")) {
                System.out.println("Введите идентификатор (id) URL-адреса:");
                String id = ReadUtils.readLine();
                try {
                    UrlDto urlDto = urlController.getUrl(id);
                    System.out.printf(
                            "Найден URL-адрес \n С идентификатором %s \n Длинный (long) URL-адрес: %s\n Сокращенный (short) URL-адрес: %s\n%n",
                            urlDto.id(), urlDto.longURL(), urlDto.shortURL()
                    );
                } catch (EntityNotFoundException ex) {
                    System.out.printf("URL-адрес с идентификатором %s не найден%n", id);
                }
            } else if (chosenService.equals("3")) {
                //реализуй код
                return;
            } else if (chosenService.equals("4")) {
                //реализуй код
                return;
            } else if (chosenService.equals("5")) {
                return;
            } else {
                System.out.println("Выберите валидный вариант");
            }
        }
    }

    private static String readAndValidateURL() {

        // !!! добавь валидатор try catch на начало строки http:// или https://
        // можно ещё потом проверять чтобы были только допустимые символы, но думаю не стоит пока что

        // Убрана конструкция try catch
        String URLString = ReadUtils.readLine();
        //если пустая строка или состоит только из пробелов
        if (URLString.isBlank()) {
            System.out.println("Введена пустая строка. Введите url-адрес, содержащий символы");
            return null;
        }
        return URLString;
    }

    private static void printMenu() {
        System.out.println("""
                
                
                Приложения для работы с url-адресами
                                
                Выберите действие:
                                
                1. Добавить url-адрес в укороченном формате
                2. Найти исходный url-адрес и сокращенный по идентификатору id
                3. Найти исходный url-адрес по укороченной версии
                4. Найти укороченную версию url-адреса по его исходной версии
                5. Выйти
                                
                """);
    }


}