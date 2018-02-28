package com.filip.statisticsservice.services;

import com.filip.statisticsservice.clients.ExchangeRatesClient;
import com.filip.statisticsservice.model.Currency;
import com.filip.statisticsservice.model.ExchangeRatesContainer;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRatesServiceImpl.class);

    private ExchangeRatesContainer container;

    @Autowired
    private ExchangeRatesClient client;

    @Override
    public Map<Currency, BigDecimal> getCurrentRates() {

        if (container == null || !container.getDate().equals(LocalDate.now())) {
            container = client.getRates(Currency.getBase());
            logger.info("Exchange rates has been updated: {}", container);
        }

        return ImmutableMap.of(
                Currency.EUR, container.getRates().get(Currency.EUR.name()),
                Currency.RUB, container.getRates().get(Currency.RUB.name()),
                Currency.USD, BigDecimal.ONE
        );

    }

    @Override
    public BigDecimal convert(Currency from, Currency to, BigDecimal amount) {

        Map<Currency, BigDecimal> rates = getCurrentRates();
        BigDecimal ratio = rates.get(to).divide(rates.get(from), 4, RoundingMode.HALF_UP);
        return amount.multiply(ratio);

    }
}
