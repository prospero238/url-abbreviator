package com.throwawaycode.rest;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.throwawaycode.bean.UrlAbbreviation;
import com.throwawaycode.dao.UrlAbbreviationRepository;
import com.throwawaycode.service.AbbreviatorService;

@RestController("/admin")
public class UrlAbbreviationResource {

    @Resource
    AbbreviatorService abbreviatorService;

    @Resource
    UrlAbbreviationRepository urlAbbreviationRepository;

    @RequestMapping(value = "/admin/abbreviate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public String findOrCreate(@RequestBody UrlAbbreviation urlAbbreviation) {
        return abbreviatorService.abbreviate(urlAbbreviation.getUrl());
    }


    @RequestMapping(value = "/admin/create", method = RequestMethod.GET, produces = "text/plain")
    public String create(@RequestParam("url") String url) {
        return abbreviatorService.abbreviate(url);
    }


    @RequestMapping(value = "/admin/all", method = RequestMethod.GET, produces = "application/json")
    public List<UrlAbbreviation> all() {
        Iterable<UrlAbbreviation> all = urlAbbreviationRepository.findAll();

        ArrayList<UrlAbbreviation> urlAbbreviations = new ArrayList<>();
        all.forEach(urlAbbreviations::add);
        return urlAbbreviations;
    }
}
