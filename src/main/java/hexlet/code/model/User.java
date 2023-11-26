package hexlet.code.model;

/* шаг 2
•	id – уникальный идентификатор пользователя, генерируется автоматически
•	firstName - имя пользователя
•	lastName - фамилия пользователя
•	email - адрес электронной почты
•	password - пароль
•	createdAt - дата создания (регистрации) пользователя

шаг 3
•	id – уникальный идентификатор пользователя
•	firstName - обязательное. Минимум 1 символ
•	lastName - обязательное. Минимум 1 символ
•	email - обязательное. Только формата email
•	password - обязательное. Минимум 3 символа
    Пароль не должен храниться в базе данных в открытом виде. Используйте хеширование пароля
•	createdAt - заполняется автоматически. Дата создания (регистрации) пользователя

 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import hexlet.code.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class) //для дата_созд, дата_изм ниже
//@Setter //update user
@Data
@NoArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    @Email
    private String email;

    @JsonIgnore
    private String passwordDigest;

//    @CreationTimestamp
//    private Date createdAt;

    @CreatedDate  //после добавления в main JpaAuditing и @EntityListeners(AuditingEntityListener.class) выше
    private Date createdAt;

    public User(UserDto dto) {
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.passwordDigest = dto.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> default_authorities = List.of(new SimpleGrantedAuthority("default"));
        return default_authorities;
    }

    @Override
    public String getPassword() {
        return passwordDigest;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //t
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //t
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //t
    }

    @Override
    public boolean isEnabled() {
        return true; //t
    }
//    @LastModifiedDate
//    private Date updatedAt;


}
