package com.example.Project3Backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Project3Backend.Entities.Posts;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Long>{
}
