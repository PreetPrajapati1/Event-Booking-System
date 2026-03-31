package com.example.demo.repository;

import com.example.demo.entity.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
        List<Event> findByEventNameContainingIgnoreCase(String keyword);

}
