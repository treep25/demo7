package com.example.demo.giftcertficate.repository;

import com.example.demo.giftcertficate.model.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    @Query("select case when count(gc)> 0 then true else false end from GiftCertificate gc where gc.name = :name")
    boolean isGiftCertificateExistByName(@Param("name") String name);
    //todo
    @Query(value = "INSERT INTO gift_certificate_tags (gift_certificate_id,tags_id) VALUES (:giftCertificateId,:tagId)",nativeQuery = true)
    void createGiftCertificateTagRelationship(@Param("tagId") long tagId,@Param("giftCertificateId") long giftCertificateId);
    //todo
//    @Query(value = "DELETE  FROM gift_certificate_tag WHERE gift_certificate_id = ?1 AND tag_id = ?",nativeQuery = true)
//    boolean deleteGiftCertificateTagRelationship(List<Long> tagsId, long giftCertificateId);
    @Query(value = "SELECT gc.id FROM GiftCertificate gc WHERE gc.name = :name")
    long getIdByGiftCertificateName(@Param("name")String giftCertificateName);
}
