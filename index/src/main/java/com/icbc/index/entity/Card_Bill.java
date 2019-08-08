package com.icbc.index.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Card_Bill {

    private Integer id;
    private Integer bankId;
    private Date day;
    private Integer num;
}
