package hexlet.code.model;

/*
•	id – уникальный идентификатор пользователя, генерируется автоматически
•	firstName - имя пользователя
•	lastName - фамилия пользователя
•	email - адрес электронной почты
•	password - пароль
•	createdAt - дата создания (регистрации) пользователя
 */


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;

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
    private Date createdAt;
}
