package com.hyperaccess.gestiondiplome.services;

import com.hyperaccess.gestiondiplome.dto.DiplomeSumDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StatisticsDiplomasService {

    List<DiplomeSumDetails> findSumDiplomasByDate(LocalDate startDate, LocalDate endDate, Integer userId);

    BigDecimal getDiplomasByUserToday(Integer userId);

    BigDecimal tenLastDiplomasByUser(Integer userId);

}
