package com.libero.service.domain;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class Review implements Serializable{
	
		private int reviewNo;
		private String userId;
		private int buyNo;
		private String reviewName;
		private String reviewContent;
		private String reviewImage;
		private int starRate;
		private Date regDate;
		private int prodNo;

}
