package com.example.dnTravelBE.dto;

import java.util.HashMap;
import java.util.Map;

public class ResponseDto {

    private  ResponseDto(){}
    public static Map<String, Object> response( Object data ){
        Map<String, Object> response = new HashMap<>();
        response.put("message" , "Thực hiện thành công");
        response.put("result" , true);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> responseWithoutData(){
        Map<String, Object> response = new HashMap<>();
        response.put("message" , "Thực hiện thành công");
        response.put("result" , true);
        return response;
    }
}
