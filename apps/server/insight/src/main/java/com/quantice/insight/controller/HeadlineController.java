package com.quantice.insight.controller;

import com.quantice.insight.model.Headline;
import com.quantice.insight.service.RssArticleService;
import com.rometools.rome.io.FeedException;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeadlineController {

    private final RssArticleService rssArticleService;

    @GetMapping(value = "/headlines", produces = { "application/json" })
    public ResponseEntity<List<Headline>> getHeadlines() throws FeedException, IOException {
        return new ResponseEntity<>(rssArticleService.getHeadlines(), HttpStatus.OK);
    }


}
