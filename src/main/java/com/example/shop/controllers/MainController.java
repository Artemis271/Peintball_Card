package com.example.shop.controllers;

import com.example.shop.models.Item;
import com.example.shop.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    // Автоматическое внедрение зависимости (бин) для работы с репозиторием товаров
    @Autowired
    private ItemRepository itemRepository; // Бин для взаимодействия с базой данных товаров

    // Метод для обработки GET-запроса на главную страницу сайта
    @GetMapping("/")
    public String index(Model model) {
        // Получение всех элементов (товаров) из базы данных
        Iterable<Item> items = itemRepository.findAll();
        // Добавление списка товаров в модель для отображения на странице
        model.addAttribute("items", items);
        // Возвращаемое значение указывает на имя шаблона HTML, который будет отображён
        return "index";
    }

    // Метод для обработки GET-запроса на страницу "О нас" с возможностью передать имя пользователя
    @GetMapping("/about-us")
    public String about(@RequestParam(name = "userName", required = false, defaultValue = "World") String userName, Model model) {
        // Добавление переданного или дефолтного имени пользователя в модель
        model.addAttribute("name", userName);
        // Возвращаемое значение указывает на шаблон страницы "about"
        return "about";
    }

}
