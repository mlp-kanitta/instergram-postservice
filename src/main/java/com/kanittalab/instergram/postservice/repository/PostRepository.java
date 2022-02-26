package com.kanittalab.instergram.postservice.repository;

import com.kanittalab.instergram.postservice.model.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CassandraRepository<Post, String> {}