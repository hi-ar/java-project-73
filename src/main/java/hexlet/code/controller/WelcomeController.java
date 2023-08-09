package hexlet.code.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping(value = "/welcome", produces = "application/json")
    public String welcome() {
        return "Welcome to Spring";
    }
}
// Создайте контроллер WelcomeController. Реализуйте обработчик, который будет обрабатывать GET-запросы к корневой
// странице приложения по пути /welcome. Запрос на эту страницу должен вернуть строку "Welcome to Spring"