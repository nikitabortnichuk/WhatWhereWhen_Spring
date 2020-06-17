package com.bortni.model.repository;

import com.bortni.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g INNER JOIN g.users u WHERE u.id = :id")
    Set<Game> findByUserId(@Param("id") Long id);

    Game findByGameIdentification(String identification);

}
