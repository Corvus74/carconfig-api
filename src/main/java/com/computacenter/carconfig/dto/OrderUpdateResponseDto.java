package com.computacenter.carconfig.dto;

import com.computacenter.carconfig.enums.TransferStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateResponseDto implements Serializable {
    private String text;
    private String orderId;
    private TransferStatus status;
    private String errorMessage;

    public OrderUpdateResponseDto(String text, String orderId, TransferStatus status){
        this.text=text;
        this.orderId = orderId;
        this.status=status;
    }

    public OrderUpdateResponseDto(String text, TransferStatus status, String errorMessage){
        this.text=text;
        this.status=status;
        this.errorMessage=errorMessage;
    }
}
