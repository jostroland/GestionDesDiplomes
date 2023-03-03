package com.hyperaccess.gestiondiplome.controller;


import com.hyperaccess.gestiondiplome.controller.api.MinistreApi;
import com.hyperaccess.gestiondiplome.dto.MinistreDto;
import com.hyperaccess.gestiondiplome.services.MinistreService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.nonNull;

@RestController
public class MinistreController implements MinistreApi {

    private final MinistreService ministreService;

    public MinistreController(MinistreService ministreService) {
        this.ministreService = ministreService;
    }

    @Override
    public Integer save(MinistreDto ministreDto) {
        return ministreService.save(ministreDto);
    }

    @Override
    public Integer update(Integer id, MinistreDto ministreDto) {
        if(nonNull(this.findById(id))){
            return ministreService.update(id,ministreDto);
        }

        return ministreService.save(ministreDto);
    }

    @Override
    public MinistreDto findById(Integer id) {
        return ministreService.findById(id);
    }

    @Override
    public List<MinistreDto> findAll() {
        return ministreService.findAll();
    }

    @Override
    public void delete(Integer id) {
        ministreService.delete(id);
    }
}
