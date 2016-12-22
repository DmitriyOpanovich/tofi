package com.tofi.repository;

import com.tofi.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by ulian_000 on 13.12.2016.
 */
@Repository
public interface BotUserRepository extends JpaRepository<BotUser, Long> {
    Optional<BotUser> findByUserName(String username);
    Optional<BotUser> findByEmail(String email);
    Optional<BotUser> findByTelegramId(Long telegramId);

}
