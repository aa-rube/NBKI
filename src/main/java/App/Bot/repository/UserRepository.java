package App.Bot.repository;

import App.Bot.model.UserNBKI;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserNBKI, Long> {
    List<UserNBKI> findByPaidTrue();

}