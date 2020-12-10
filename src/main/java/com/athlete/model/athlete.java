package com.athlete.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "athlete")
public class athlete {

    private String eventId;
    private String firstName;
    private String lastName;
    private String personalBest;




    @DynamoDBHashKey(attributeName = "eventId")
    @DynamoDBAutoGeneratedKey
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @DynamoDBAttribute
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBRangeKey
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute
    public String getPersonalBest() {
        return personalBest;
    }

    public void setPersonalBest(String personalBest) {
        this.personalBest = personalBest;
    }


}

