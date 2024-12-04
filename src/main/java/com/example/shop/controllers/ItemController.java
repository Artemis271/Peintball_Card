package com.example.shop.controllers;

import org.springframework.ui.Model;
import com.example.shop.models.Item;
import com.example.shop.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController {

    // Автоматическое внедрение зависимости (бин) для работы с репозиторием товаров
    @Autowired
    private ItemRepository itemRepository;

    // Метод для обработки GET-запроса на страницу добавления нового товара
    @GetMapping("/item/add")
    public String add() {
        // Возвращает название шаблона страницы добавления товара
        return "add-item";
    }

    // Метод для обработки POST-запроса на добавление нового товара
    @PostMapping("/item/add")
    public String store(
            @RequestParam String title,  // Получение параметра "title" из запроса
            @RequestParam String image,  // Получение параметра "image" из запроса
            @RequestParam String price,  // Получение параметра "price" из запроса
            @RequestParam String info    // Получение параметра "info" из запроса
    ) {
        // Создание нового объекта Item с переданными значениями
        Item item = new Item(title, info, image, Short.parseShort(price));
        // Сохранение нового товара в базу данных
        itemRepository.save(item);
        // Перенаправление на главную страницу после добавления товара
        return "redirect:/";
    }

    @GetMapping("/item/{id}")
    public String showItem(@PathVariable(value = "id") long id, Model model){
        Item item = itemRepository.findById(id).orElse(new Item());
        model.addAttribute("item", item);
        return "show-item";
    }

    @GetMapping("/item/{id}/update")
    public String update(@PathVariable(value = "id") long id, Model model){
        Item item = itemRepository.findById(id).orElse(new Item());
        model.addAttribute("item", item);
        return "item-update";
    }


    @PostMapping("/item/{id}/update")
    public String updateItem(
            @PathVariable(value = "id") long id,
            @RequestParam String title,  // Получение параметра "title" из запроса
            @RequestParam String image,  // Получение параметра "image" из запроса
            @RequestParam String price,  // Получение параметра "price" из запроса
            @RequestParam String info    // Получение параметра "info" из запроса
    ) {

        Item item = itemRepository.findById(id).orElse(new Item());
        item.setTitle(title);
        item.setImage(image);
        item.setInfo(info);
        item.setPrice(Short.parseShort(price));

        // Сохранение нового товара в базу данных
        itemRepository.save(item);
        // Перенаправление на главную страницу после добавления товара
        return "redirect:/item/" + id;
    }


    @PostMapping("/item/{id}/delete")
    public String itemDelete(@PathVariable(value = "id") long id){
        Item item = itemRepository.findById(id).orElse(new Item());
        itemRepository.delete(item);
        return "redirect:/";
    }

}
