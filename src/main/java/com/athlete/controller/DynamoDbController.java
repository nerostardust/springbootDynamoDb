package com.athlete.controller;

import com.athlete.model.athlete;
import com.athlete.repository.DynamoDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dynamoDb")
public class DynamoDbController {

    @Autowired
    private DynamoDbRepository repository;

    @PostMapping
    //@RequestBody: converts the body we entered in post request of postman to java class object = athlete.
    public String insertIntoDynamoDB(@RequestBody athlete athlete){
        repository.insertIntoDynamoDB(athlete);
        return "Successfully inserted into DynamoDB table";
    }

    @GetMapping("/get")
    //the data we get from the postman get request is bound to the parameters found in the bracket of this method
    //using @RequestParam
    public ResponseEntity<athlete> getFromDynamoDB(@RequestParam String eventId,@RequestParam String lastName){
       athlete athlete = repository.getFromDynamoDB(eventId,lastName);
        return new ResponseEntity<athlete>(athlete, HttpStatus.OK);
    }

    @GetMapping("/count")

    public Integer countAthlete(@RequestParam String eventId,@RequestParam String lastName,
                                @RequestParam String personalBest){
        System.out.println("aaa");
        Integer athlete = repository.countAthlete(eventId,lastName,personalBest);
        return athlete;
    }


    @PutMapping
    public void updateDynamoDB(@RequestBody athlete athlete){
        repository.updateDynamoDB(athlete);

//        if (athlete.getStatus()=="c"){
//            repository.updateDynamoDB(athlete);
//
//        }


//        return "Successfully updated DynamoDB table";


    }




}
