package com.example.TaskBoard.repository;

import com.example.TaskBoard.entity.Comments;
import com.example.TaskBoard.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByIssue(Optional<Issue> issue);

}
