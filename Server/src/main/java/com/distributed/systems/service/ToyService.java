package com.distributed.systems.service;

import com.distributed.systems.model.Toy;
import com.distributed.systems.repository.ToyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToyService {

    @Autowired
    ToyRepository toyRepository;

    // Check if Toy exists in db
    public boolean verify(Toy toy) {
        Toy verifyToy = toyRepository.getOne(toy.getCode());
        return verifyToy.getCode().equals(toy.getCode());
    }

    // Save Toy
    public void saveToy(Toy toy) {
        toyRepository.save(toy);
    }
}
