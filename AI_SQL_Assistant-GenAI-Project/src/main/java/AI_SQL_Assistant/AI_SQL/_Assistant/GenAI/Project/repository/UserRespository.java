package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.repository;

import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);
}
