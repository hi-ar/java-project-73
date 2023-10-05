package hexlet.code.repository;

import hexlet.code.model.QUser;
import hexlet.code.model.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends
        CrudRepository <User, Long>, //сущность и праймари кей этой сущности
        QuerydslPredicateExecutor<User> { //userRepository.findAll(QUser.user.firstName.eq...and...)
    Optional<User> findById(long id); //return user.orElseThrow(new exception)
}
