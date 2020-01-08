package com.baizhi.gjc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
  @Id
  private String id;
  private String title;
  private String img;
  private String content;
  private Date createDate;
  private Date publishDate;
  private String status;
  private String guruId;

}
