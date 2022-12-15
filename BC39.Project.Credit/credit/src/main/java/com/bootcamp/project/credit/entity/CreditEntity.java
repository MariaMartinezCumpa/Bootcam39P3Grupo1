package com.bootcamp.project.credit.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Credit")
public class CreditEntity {
    @Id
    private String creditNumber;
    private String idProduct;
    private String clientDocumentNumber;
    private String accountNumber;
    private double totalDebt;
    private double currentDebt;
    private double creditLimit;
    private Date dueDate;
    private Date createDate;
    private Date modifyDate;

    /*Temp */
    private String clientType;
    private String productCode;
    /*Temp */
}
