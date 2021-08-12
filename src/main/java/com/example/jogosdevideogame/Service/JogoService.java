package com.example.jogosdevideogame.Service;

import com.example.jogosdevideogame.Model.Jogo;
import com.example.jogosdevideogame.Repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogoService {

    JogoRepository repository;

    @Autowired
    public void setRepository(JogoRepository repository){
        this.repository = repository;
    }

    public List<Jogo> findAll(){
        return repository.findAll();
    }

    //public List<Jogo> findAll2(){
      //  return repository.findAllByDeletedIsNotNull();
    //}

    public void save(Jogo jogo){
        repository.save(jogo);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public Jogo findById(Long id){
        return repository.getById(id);
    }
}
