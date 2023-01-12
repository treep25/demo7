package com.example.demo.giftcertficate.model;

import com.example.demo.tag.model.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class GiftCertificate extends RepresentationModel<GiftCertificate> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer price;
    @Column(name = "duration")
    private Integer durationDays;
    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private Set<Tag> tags;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date lastUpdateDate;

    public GiftCertificate() {

    }
}
