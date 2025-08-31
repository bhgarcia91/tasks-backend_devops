package br.ce.bruno.taskbackend.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DataUtilsTest {




    @Test
    public void dataFuturaDeveRetornarPositivo(){
        LocalDate date = LocalDate.of(2026, 1, 1);
        DateUtils dateUtils = new DateUtils();
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void dataAtualDeveRetornarPositivo(){
        LocalDate date = LocalDate.now();
        DateUtils dateUtils = new DateUtils();
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void dataPassadaDeveRetornarNegativo(){
        LocalDate date = LocalDate.of(2025, 4, 4);
        DateUtils dateUtils = new DateUtils();
        Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
    }

}
