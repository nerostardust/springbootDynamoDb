package com.athlete.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.athlete.model.athlete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class DynamoDbRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbRepository.class);

    @Autowired
    //DynamoDBMapper is a built in class which helps to access tables
    //save method is used to insert data and load method is used to get data to and from the table respectively.
    private DynamoDBMapper mapper;

    public void insertIntoDynamoDB(athlete athlete){
    mapper.save(athlete);
    }


    public athlete getFromDynamoDB(String eventId, String lastName){
        return mapper.load(athlete.class,eventId,lastName);
    }

}
