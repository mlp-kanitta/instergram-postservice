package com.kanittalab.instergram.postservice.service;

import com.kanittalab.instergram.postservice.model.Post;
import com.kanittalab.instergram.postservice.model.request.PostRequest;
import com.kanittalab.instergram.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
@Service("postService")
public class PostService {

   // @Autowired
    private PostRepository feedRepository;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public String getReleaseVersion() {
        return cassandraTemplate
                .getCqlOperations()
                .queryForObject("select release_version from system.local", String.class);
    }

    public Post createPost(PostRequest postRequest) throws IOException {
        Post post = new Post();
        post.setCaption(postRequest.getCaption());
        post.setContentType(postRequest.getImageFile().getContentType());
        post.setImage( Base64.getEncoder().encodeToString(postRequest.getImageFile().getBytes()));

        return feedRepository.insert(post);
    }

    public void deletePost(String id, String userId) {
    }

    public List<Post> getPostsByUserId(String userId) {
        return   feedRepository.findAll(CassandraPageRequest.first(10)).toList();
    }
}
