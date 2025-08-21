package com.edu.kobridge.module.lesson.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.kobridge.module.lesson.domain.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
	public List<Lesson> findAllByOrderByNumberAsc();
}
