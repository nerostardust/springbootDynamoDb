package com.athlete.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.*;
import com.athlete.model.athlete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class DynamoDbRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbRepository.class);

    @Autowired
    //DynamoDBMapper is a built in class which helps to access tables
    //save method is used to insert data and load method is used to get data to and from the table respectively.
    private DynamoDBMapper mapper;



    public void insertIntoDynamoDB(athlete athlete) {
        mapper.save(athlete);
    }


    public athlete getFromDynamoDB(String eventId, String lastName) {
        return mapper.load(athlete.class, eventId, lastName);

    }

    public void updateDynamoDB(athlete athlete) {

        try {
//            if (athlete.getStatus()=="a")
//            System.out.println("Attempting a conditional update...");
            {
                mapper.save(athlete, buildDynamoDBSaveExpression(athlete));
            }

        } catch (ConditionalCheckFailedException exception) {
            LOGGER.error("Unable to update the status" + exception.getMessage());
        }

    }

    public DynamoDBSaveExpression buildDynamoDBSaveExpression(athlete athlete) {
//        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
//        Map<String, ExpectedAttributeValue> expected = new HashMap<>();
//
//
//        expected.put("eventId",new ExpectedAttributeValue(new AttributeValue(athlete.getEventId()))
//                .withComparisonOperator(ComparisonOperator.EQ));
//        saveExpression.setExpected(expected);
//        return saveExpression;


        DynamoDBSaveExpression exp = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> map = new HashMap<>();
        map.put("eventId", new ExpectedAttributeValue(new AttributeValue(athlete.getEventId())).withExists(true));
        map.put("lastName", new ExpectedAttributeValue(new AttributeValue(athlete.getLastName())).withExists(true));
        map.put("status", new ExpectedAttributeValue(new AttributeValue("a")).withExists(true));
        exp.withExpected(map);
        return exp;



//        expected.put("status",new ExpectedAttributeValue(new AttributeValue("c"))

//        expected.put("status",new ExpectedAttributeValue(new AttributeValue(athlete.getStatus()))
//                .withComparisonOperator(ComparisonOperator.EQ));


//        if(athlete.getStatus()=="a"){
//            expected.put("eventId",new ExpectedAttributeValue(new AttributeValue(athlete.getEventId()))
//                    .withComparisonOperator(ComparisonOperator.EQ));
//            saveExpression.setExpected(expected);
//
//        }
//        else if (athlete.getStatus()=="c"){
//            System.out.println("Cannot update from c to a ");
//        }
//        return saveExpression;


////        if(athlete.getStatus()=="a"){
//            expected.put("status",new ExpectedAttributeValue(new AttributeValue(athlete.getStatus())));
////                    .withComparisonOperator(ComparisonOperator.EQ));
//            saveExpression.setExpected(expected);
//////
//////        }
//////        else if (athlete.getStatus()=="c"){
//////            System.out.println("Cannot update from c to a ");
//////        }
//        return saveExpression;


//        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
//
//        eav.put(":v1",new AttributeValue().withS(athlete.getStatus()));
//        eav.put(":v2",new AttributeValue().withS(athlete.getEventId()));
//        eav.put(":v3",new AttributeValue().withS(athlete.getLastName()));
//
//        DynamoDBQueryExpression<athlete> queryExpression = new DynamoDBQueryExpression<athlete>()
//                .withKeyConditionExpression("firstName = :v1 and eventId = :v2 and lastName = :v3")
//                .withExpressionAttributeValues(eav);
//
//        List<athlete> latestReplies = mapper.query(athlete.class, queryExpression);
//        for (athlete a : latestReplies){
//            System.out.println(a.getStatus());
//        }
//    return null;

}

    public Integer countAthlete(String eventId,String lastName, String personalBest) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
//        List<Map<String,AttributeValue>> ath = new ArrayList<>();

        Map<String,String>expressionAttributeNames = new HashMap<>();
        expressionAttributeNames.put("#eventId","eventId");
        expressionAttributeNames.put("#lastName","lastName");
        expressionAttributeNames.put("#personalBest","personalBest");
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":eventIdValue",new AttributeValue().withS(eventId));
        expressionAttributeValues.put(":lastNameValue",new AttributeValue().withS(lastName));
        expressionAttributeValues.put(":personalBestValue",new AttributeValue().withS(personalBest));

        ScanRequest scan = new ScanRequest().withTableName("athlete");
        ScanResult result =client.scan(scan);


        int count=0;
        for(Map<String,AttributeValue> i:result.getItems()){
            count++;
        }
        return  count;

//        QueryRequest queryRequest= new QueryRequest().withTableName("athlete")
//                .withKeyConditionExpression("#eventId = :eventIdValue AND #lastName = :lastNameValue AND #personalBest < : 10")
//                .withExpressionAttributeNames(expressionAttributeNames)
//                .withExpressionAttributeValues(expressionAttributeValues)
//                .withSelect(Select.COUNT);



//        Map<String,AttributeValue> lastKey=null;
//        do {
//            QueryResult queryResult = client.query(queryRequest);
//            List<Map<String,AttributeValue>> results = queryResult.getItems();
//            ath.addAll(results);
//            lastKey=queryResult.getLastEvaluatedKey();
//            queryRequest.setExclusiveStartKey(lastKey);
//
//        } while
//        (lastKey!=null);



//        return ath.size();

    }

}
