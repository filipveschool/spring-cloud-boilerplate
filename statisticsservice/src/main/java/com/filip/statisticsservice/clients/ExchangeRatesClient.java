package com.filip.statisticsservice.clients;

import com.filip.statisticsservice.model.Currency;
import com.filip.statisticsservice.model.ExchangeRatesContainer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${rates.url}", name = "rates-client")
public interface ExchangeRatesClient {

    @GetMapping(value = "/latest")
    ExchangeRatesContainer getRates(@RequestParam("base") Currency base);

}