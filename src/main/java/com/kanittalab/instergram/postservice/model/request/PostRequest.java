package com.kanittalab.instergram.postservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostRequest {
    @Max(250)
    private String caption;

    private MultipartFile imageFile;
}
