package com.kanittalab.instergram.postservice.model.response;

import com.kanittalab.instergram.postservice.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
public class PostListResponse implements Serializable {
    private List<Post> posts;
}
