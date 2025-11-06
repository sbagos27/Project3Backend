package com.example.Project3Backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Project3Backend.Entities.Posts;

public interface PostRepository extends JpaRepository<Posts, Long>{
}
