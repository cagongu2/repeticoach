package com.cagongu.repeticoach.service;

import com.cagongu.repeticoach.dto.request.CreateTopicRequest;
import com.cagongu.repeticoach.dto.request.UpdateTopicRequest;
import com.cagongu.repeticoach.model.Topic;
import com.cagongu.repeticoach.model.TopicRecord;
import com.cagongu.repeticoach.repository.TopicRepository;
import com.cagongu.repeticoach.utils.ConvertCSV;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepo;

    public List<Topic> findAll() {
        return topicRepo.findAll();
    }

    public Topic findById(String name) {
        return topicRepo.findById(name).orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    public Topic save(CreateTopicRequest request) {
        Topic topic = Topic.builder().name(request.getName()).build();
        return topicRepo.save(topic);
    }
    
    public Topic update(String name, UpdateTopicRequest request){
        Topic topic = topicRepo.findById(name).orElseThrow(() -> new RuntimeException("Topic not found"));
        if(StringUtils.hasText(request.getName())){
            topic.setName(request.getName());
        }
        
        return topicRepo.save(topic);
    }

    public void deleteById(String name) {
        Topic topic =topicRepo.findById(name).orElseThrow(() -> new RuntimeException("Topic not found"));
        topicRepo.delete(topic);
    }

    public void loadCsvData() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/topics.csv");
        ConvertCSV cvts = new ConvertCSV();
        List<TopicRecord> recs = cvts.convertCSV(file, TopicRecord.class);
        recs.forEach(topicRecord -> {
            topicRepo.save(Topic.builder().name(topicRecord.getName()).build());
        });
    }
}
