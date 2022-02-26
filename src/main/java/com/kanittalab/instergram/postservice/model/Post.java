package com.kanittalab.instergram.postservice.model;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("post")
@RequiredArgsConstructor
@Data
public class Post implements Serializable {
    @PrimaryKey
    @CassandraType(type = Name.UUID)
    private UUID uid = UUID.randomUUID();

    @CassandraType(type = Name.UUID)
    @Column("user_id")
    private UUID userId;

    private String caption;

    //Base64
    private String image;

    @Column("content_type")
    private String contentType;

    private float width;
    private float height;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column("dt_created")
    private Instant createdDate;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column("dt_updated")
    private Instant updatedDate;

}
