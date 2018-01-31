package com.core.domain;

import com.core.util.datetime.converter.JodaDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by himanshu.virmani on 08/12/15.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = {"id"})
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @JsonProperty(value = "id")
    private Long id;

    @Version
    @Column(name = "version")
    @JsonIgnore
    private Long version;

    @Column(name = "created_at")
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    @Convert(converter = JodaDateTimeConverter.class)
    @JsonProperty("created_at")
    private DateTime createdAt;

    @Column(name = "updated_at")
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Convert(converter = JodaDateTimeConverter.class)
    @LastModifiedDate
    @JsonProperty("updated_at")
    private DateTime updatedAt;

}
