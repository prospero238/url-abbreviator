package com.throwawaycode.dao;

import org.springframework.data.repository.CrudRepository;

import com.throwawaycode.bean.UrlAbbreviation;

public interface UrlAbbreviationRepository extends CrudRepository<UrlAbbreviation, Long> {

    UrlAbbreviation findByPathAbbreviation(String path);

    UrlAbbreviation findByUrl(String url);
}
