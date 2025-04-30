package org.demointernetshop45efs.repository;

import org.demointernetshop45efs.entity.ConfirmationCode;
import org.demointernetshop45efs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Integer> {

    Optional<ConfirmationCode> findByCode(String code);

    Optional<ConfirmationCode> findByCodeAndExpireDataTimeAfter(String code, LocalDateTime currentDataTime);

    List<ConfirmationCode> findByUser(User user);
}
