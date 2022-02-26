package com.kanittalab.instergram.postservice.repository;

import com.kanittalab.instergram.postservice.model.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends CassandraRepository<Post, String> {
    @Query("select * from post where user_id = ?0 LIMIT 20 ALLOW FILTERING")
    List<Post> findPostByUserId(String userId);

    @Query("delete from post where uid=?0 AND user_id = ?1")
    int deletePostbyId(String id,String userId);

    @Query("select * from post where uid=?0")
    Optional<Post> findById(UUID id);
}