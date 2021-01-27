package de.structuremade.ms.sicksercvice.utils.database.repo;

import de.structuremade.ms.sicksercvice.utils.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
