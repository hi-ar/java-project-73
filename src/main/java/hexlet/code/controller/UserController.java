package hexlet.code.controller;

import hexlet.code.dto.UserDto;
import hexlet.code.model.User;
import hexlet.code.service.userService.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("${base-url}" + "/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(path = "/{id}")
    //@ResponseStatus(HttpStatus.CREATED)
//    Если запрос содержит невалидные данные, то должен возвращаться ответ со статус-кодом 422

    public User getUser(@PathVariable long id) {
        return this.userService.findById(id);
    }

    @GetMapping()
    public Iterable<User> getUsers() {
        return userService.showAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Valid UserDto dto) { //убрал отсюда валид
        return userService.createUser(dto);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable long id, @RequestBody @Valid UserDto dto) {
        return userService.updateUser(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
/*
•	PUT /api/users/{id} - обновление пользователя
Пример тела запроса:
{
    "email": "petr@yahoo.com",
    "firstName": "Petr",
    "lastName": "Sidorov",
    "password": "new-password"
}
Пример ответа:
{
    "id": 2,
    "email": "petr@yahoo.com",
    "firstName": "Petr",
    "lastName": "Sidorov",
    "createdAt": "2021-12-20T12:45:17.173Z"
}
•	DELETE /api/users/{id} - удаление пользователя
•	GET /api/users/{id} - получение пользователя по идентификатору
Пример ответа:
{
    "id": 1,
    "email": "ivan@google.com",
    "firstName": "Ivan",
    "lastName": "Petrov",
    "createdAt": "2021-12-20T12:45:17.173Z"
}
•	GET /api/users - получение списка пользователей
Пример ответа:
[
    {
        "id": 1,
        "email": "ivan@google.com",
        "firstName": "Ivan",
        "lastName": "Petrov",
        "createdAt": "2021-12-20T12:45:17.173Z"
    },
    {
        "id": 2,
        "email": "petr@yahoo.com",
        "firstName": "Petr",
        "lastName": "Sidorov",
        "createdAt": "2021-12-20T12:45:17.173Z"
    }
]
•	POST /api/users - создание пользователя
Пример тела запроса:
{
    "email": "ivan@google.com",
    "firstName": "Ivan",
    "lastName": "Petrov",
    "password": "some-password"
}
Пример ответа:
{
    "id": 1,
    "email": "ivan@google.com",
    "firstName": "Ivan",
    "lastName": "Petrov",
    "createdAt": "2021-12-20T12:45:17.173Z"
}

 */