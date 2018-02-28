package com.filip.statisticsservice.services;

import com.filip.statisticsservice.model.*;
import com.filip.statisticsservice.model.timeseries.DataPoint;
import com.filip.statisticsservice.model.timeseries.DataPointId;
import com.filip.statisticsservice.model.timeseries.ItemMetric;
import com.filip.statisticsservice.model.timeseries.StatisticMetric;
import com.filip.statisticsservice.repository.DataPointRepository;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Autowired
    private DataPointRepository repository;

    @Autowired
    private ExchangeRatesService ratesService;

    @Override
    public List<DataPoint> findByAccountName(String accountName) {
        return repository.findByIdAccount(accountName);
    }

    @Override
    public DataPoint save(String accountName, Account account) {
        Instant instant = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        DataPointId pointId = new DataPointId(accountName, Date.from(instant));
        Set<ItemMetric> incomes = account.getIncomes().stream().map(this::createItemMetric).collect(Collectors.toSet());
        Set<ItemMetric> expenses = account.getExpenses().stream().map(this::createItemMetric).collect(Collectors.toSet());
        Map<StatisticMetric, BigDecimal> statistics = createStatisticsMetrics(incomes, expenses, account.getSaving());
        DataPoint dataPoint = new DataPoint();
        dataPoint.setId(pointId);
        dataPoint.setIncomes(incomes);
        dataPoint.setExpenses(expenses);
        dataPoint.setStatistics(statistics);
        dataPoint.setRates(ratesService.getCurrentRates());

        logger.debug("new datapoint has been created: {}", pointId);

        return repository.save(dataPoint);

    }


    private Map<StatisticMetric, BigDecimal> createStatisticsMetrics(Set<ItemMetric> incomes, Set<ItemMetric> expenses, Saving saving) {
        BigDecimal savingAmount = ratesService.convert(saving.getCurrency(), Currency.getBase(), saving.getAmount());
        BigDecimal expensesAmount = expenses.stream().map(ItemMetric::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal incomesAmount = incomes.stream().map(ItemMetric::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return ImmutableMap.of(
                StatisticMetric.EXPENSES_AMOUNT, expensesAmount,
                StatisticMetric.INCOMES_AMOUNT, incomesAmount,
                StatisticMetric.SAVING_AMOUNT, savingAmount);
    }

    /**
     * Normalizes given item amount to {@link Currency#getBase()} currency with
     * {@link TimePeriod#getBase()} } time period
     */
    private ItemMetric createItemMetric(Item item) {
        BigDecimal amount = ratesService
                .convert(item.getCurrency(), Currency.getBase(), item.getAmount())
                .divide(item.getPeriod().getBaseRatio(), 4, RoundingMode.HALF_UP);

        return new ItemMetric(item.getTitle(), amount);


    }
}
