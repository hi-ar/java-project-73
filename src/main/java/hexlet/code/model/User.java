package hexlet.code.model;

/*
•	id – уникальный идентификатор пользователя, генерируется автоматически
•	firstName - имя пользователя
•	lastName - фамилия пользователя
•	email - адрес электронной почты
•	password - пароль
•	createdAt - дата создания (регистрации) пользователя
 */


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @CreationTimestamp
    private Instant createdAt;
}
