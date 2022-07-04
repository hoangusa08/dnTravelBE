package com.example.dnTravelBE.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditAvatarCus {
    private Integer customerId;
    private String link;
}
