package de.structuremade.ms.sicksercvice.utils.database.repo;

import de.structuremade.ms.sicksercvice.utils.database.entity.Sick;
import de.structuremade.ms.sicksercvice.utils.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SickRepo extends JpaRepository<Sick, String> {
    Sick findByUserAndDateOfSickness(User user, Date time);

    List<Sick> findAllByUser(User one);
}
