package com.throwawaycode.bean;

import io.swagger.annotations.ApiModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@ApiModel
public class UrlAbbreviation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;
    private String pathAbbreviation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPathAbbreviation() {
        return pathAbbreviation;
    }

    public void setPathAbbreviation(String pathAbbreviation) {
        this.pathAbbreviation = pathAbbreviation;
    }
}
