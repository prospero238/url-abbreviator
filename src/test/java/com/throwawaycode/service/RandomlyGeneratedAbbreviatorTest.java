package com.throwawaycode.service;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.throwawaycode.bean.UrlAbbreviation;
import com.throwawaycode.dao.UrlAbbreviationRepository;
import com.throwawaycode.exception.AbbreviationGenerationException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RandomlyGeneratedAbbreviatorTest {
    @Rule
    public Timeout timeout = new Timeout(5, TimeUnit.SECONDS);

    @Mock
    private UrlAbbreviationRepository urlAbbreviationRepository;
    protected RandomlyGeneratedAbbreviator abbreviator;

    @Before
    public void setup() {
        abbreviator = new RandomlyGeneratedAbbreviator(urlAbbreviationRepository);
    }
    @Test
    public void should_generate_random_abbreviation() {
        when(urlAbbreviationRepository.findByPathAbbreviation(anyString())).thenReturn(null);
        abbreviator.createUniqueAbbreviation();
    }

    @Test(expected = AbbreviationGenerationException.class)
    public void should_throw_exception() {
        when(urlAbbreviationRepository.findByPathAbbreviation(anyString())).thenReturn(new UrlAbbreviation());
        abbreviator.createUniqueAbbreviation();


    }
}