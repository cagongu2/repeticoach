package com.cagongu.repeticoach.controller;

import com.cagongu.repeticoach.dto.request.CreateTopicRequest;
import com.cagongu.repeticoach.dto.request.UpdateTopicRequest;
import com.cagongu.repeticoach.model.Topic;
import com.cagongu.repeticoach.model.TopicRecord;
import com.cagongu.repeticoach.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    @GetMapping()
    public List<Topic> getAllVocabularies() {
        return topicService.findAll();
    }

    @GetMapping("/{id}")
    public Topic getTopicById(@PathVariable String name) {
        return topicService.findById(name);
    }

    @PostMapping()
    public Topic createTopic(@RequestBody CreateTopicRequest topic) {
        return topicService.save(topic);
    }

    @PutMapping("/{id}")
    public Topic updateTopic(@PathVariable String id, @RequestBody UpdateTopicRequest topic) {

        return topicService.update(id, topic);
    }

    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable String id) {
        topicService.deleteById(id);
    }

    @PostMapping("/import-data")
    public void loadCsvData() throws FileNotFoundException {
        topicService.loadCsvData();

    }
}
