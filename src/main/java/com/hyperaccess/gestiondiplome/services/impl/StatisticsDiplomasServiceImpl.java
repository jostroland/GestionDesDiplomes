package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.dto.DiplomeSumDetails;
import com.hyperaccess.gestiondiplome.services.StatisticsDiplomasService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class StatisticsDiplomasServiceImpl implements StatisticsDiplomasService {
    @Override
    public List<DiplomeSumDetails> findSumDiplomasByDate(LocalDate startDate, LocalDate endDate, Integer userId) {
        return null;
    }

    @Override
    public BigDecimal getDiplomasByUserToday(Integer userId) {
        return null;
    }

    @Override
    public BigDecimal tenLastDiplomasByUser(Integer userId) {
        return null;
    }
}
