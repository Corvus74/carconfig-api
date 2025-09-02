package com.applicationdemo.carconfig.dto;

import com.applicationdemo.carconfig.enums.TransferStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto implements Serializable {
    private String text;
    private TransferStatus status;
    private String errorMessage;

    public ResponseDto(String text,TransferStatus status){
        this.text=text;
        this.status=status;
    }

}
