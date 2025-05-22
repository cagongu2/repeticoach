package com.cagongu.repeticoach.repository;

import com.cagongu.repeticoach.model.Topic;
import com.cagongu.repeticoach.model.TopicRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, String> {
    Optional<Topic> findByName(String name);
}
