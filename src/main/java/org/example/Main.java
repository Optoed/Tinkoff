package org.example;

import org.example.controller.CatController;
import org.example.controller.dto.CatDto;
import org.example.exception.EntityNotFoundException;
import org.example.repository.CatRepositoryImpl;
import org.example.service.CatServiceImpl;
import org.example.service.model.Cat;
import org.example.utils.ReadUtils;

public class Main {
    public static void main(String[] args) {

        while (true) {
            CatController catController = new CatController(new CatServiceImpl(new CatRepositoryImpl()));
            printMenu();
            String chosenService = ReadUtils.readLine();

            if (chosenService.equals("1")) {
                System.out.println("Введите исходный (длинный) URL-адрес");
                String longURLString = "";
                while (longURLString == "") {
                    longURLString = readAndValidateURL();
                }
                String id = catController.addCat(new CatDto(longURLString));
                System.out.printf("Создан исходный URL-адрес %s \n С идентификатором %s%n", longURLString, id);

            } else if (chosenService.equals("2")) {
                System.out.println("Введите идентификатор (id) URL-адреса:");
                String id = ReadUtils.readLine();
                try {
                    CatDto catDto = catController.getCat(id);
                    System.out.printf(
                            "Найден URL-адрес \n С идентификатором %s \n Длинный (long) URL-адрес: %s\n Сокращенный (short) URL-адрес: %s\n%n",
                            catDto.id(), catDto.longURL(), catDto.shortURL()
                    );
                } catch (EntityNotFoundException ex) {
                    System.out.printf("URL-адрес с идентификатором %s не найден%n", id);
                }
            } else if (chosenService.equals("3")) {
                return;
            } else {
                System.out.println("Выберите валидный вариант");
            }
        }
    }

    private static String readAndValidateURL() {
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
                2. Найти исходный url-адрес и сокращенный по id
                3. Найти исходный url-адрес по укороченной версии
                4. Найти укороченную версию url-адреса по его исходной версии
                5. Выйти
                                
                """);
    }


}