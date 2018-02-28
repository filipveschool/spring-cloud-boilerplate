package com.filip.statisticsservice.repository;

import com.filip.statisticsservice.model.timeseries.DataPoint;
import com.filip.statisticsservice.model.timeseries.DataPointId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataPointRepository extends CrudRepository<DataPoint, DataPointId> {

    List<DataPoint> findByIdAccount(String account);

}
