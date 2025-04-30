package org.demointernetshop45efs.repository;

import org.demointernetshop45efs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    // этот метод будет необходим в процессе регистрации нового пользователя


    Optional<User> findByEmail(String email);
    // этот метод будет необходим для ответа на запрос "предоставьте данные о пользователе с таким-то email"

    List<User> findByLastName(String lastName);
    // этот метод будет возвращать коллекцию , которая будет состоять из
    // записей в которых поле last_name будет совпадать с тем, что нам передали
    // в lastName

}
