package com.example.jogosdevideogame.Repository;

import com.example.jogosdevideogame.Model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
    //public ArrayList<Jogo> findAllByDeletedIsNull();
    //public ArrayList<Jogo> findAllByDeletedIsNotNull();
}
