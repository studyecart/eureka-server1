package com.example.demo.repo;

import java.util.List;

import com.example.demo.model.DemoModel;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DemoRepository  extends JpaRepository<DemoModel, Long> {

        List<DemoModel> findByPublished(boolean published);
        List<DemoModel> findByTitleContaining(String title);
    }
