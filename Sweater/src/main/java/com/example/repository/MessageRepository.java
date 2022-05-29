package com.example.repository;

import com.example.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository  extends CrudRepository<Message, Integer> {
    List<Message> findByTag(String tag);
}
