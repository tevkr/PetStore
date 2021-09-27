package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Класс представляющий модель пользователей
 * @author Яновский Владислав
 */
@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    /**
     * Идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    /**
     * Электонная почта поьзователя
     */
    @Column(name="email")
    String email;
    /**
     * Никнейм пользователя
     */
    @Column(name="username")
    String username;
    /**
     * Пароль пользователя
     */
    @Column(name="password")
    String password;
    /**
     * Роль пользователя
     */
    @Column(name="role")
    String role;

    /**
     * Переопределенный метод getAuthorities() получающий коллекцию ролей пользователя
     * @return Возвращает коллекцию ролей пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    /**
     * Переопределенный метод isAccountNonExpired() определяющий срок истечения действия учетной записи пользователя
     * @return Возвращает true (Нет срока истечения действия учетной записи)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Переопределенный метод isAccountNonLocked() определяющий блокировку учетной записи
     * @return Возвращает true (Нет блокировок у учетной записи)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Переопределенный метод isCredentialsNonExpired() определяющий срок истечения учетных данных
     * @return Возвращает true (Нет срока истечения учетных данных)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Переопределенный метод isEnabled() определяющий доступность учетной записи
     * @return Возвращает true (Учетная запись всегда доступна)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
