package com.hyperaccess.gestiondiplome.services;

import com.hyperaccess.gestiondiplome.dto.MinistreDto;

public interface MinistreService extends AbstractService<MinistreDto>  {


    Integer update(Integer id, MinistreDto ministreDto);
}
