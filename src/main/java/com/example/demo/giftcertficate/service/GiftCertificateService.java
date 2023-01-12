package com.example.demo.giftcertficate.service;


import com.example.demo.exceptionhandler.exception.ItemNotFoundException;
import com.example.demo.exceptionhandler.exception.ServerException;
import com.example.demo.giftcertficate.model.GiftCertificate;
import com.example.demo.giftcertficate.repository.GiftCertificateRepository;
import com.example.demo.tag.model.Tag;
import com.example.demo.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@EnableTransactionManagement
public class GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;

    private static final String TAGS = "tags";
    private static final String NAME = "name";

    @Transactional
    public GiftCertificate createGiftCertificate(GiftCertificate giftCertificate) {
        if (!giftCertificateRepository.isGiftCertificateExistByName(giftCertificate.getName())) {

            tagService.verifyIsTagsExistWhenCreatingGiftCertificate(giftCertificate.getTags());

            return giftCertificateRepository.saveAndFlush(giftCertificate);
        }
        throw new ServerException("This gift certificate with (name = " + giftCertificate.getName() + ") has already existed");
    }

    public List<GiftCertificate> getAll() {
        return giftCertificateRepository.findAll();
    }

    public GiftCertificate getOneGiftCertificateById(long id) {
        return giftCertificateRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("There are no gift certificate with (id = " + id + ")"));
    }

    public boolean deleteGiftCertificate(long id) {
        if (giftCertificateRepository.existsById(id)) {
            giftCertificateRepository.deleteById(id);

            return true;
        }
        throw new ItemNotFoundException("There are no gift certificate with (id = " + id + ")");
    }

    //   @Transactional
//    public List<GiftCertificate> updateGiftCertificate(long id, List<Tag> tags, Map<String, String> updatesMap) {
//
//        updatesMap.remove(TAGS);
//
//        if((!giftCertificateRepository.isGiftCertificateExist(updatesMap.get(NAME)))){
//            if (giftCertificateRepository.updateGiftCertificate(id, updatesMap)) {
//
//                if (tags != null) {
//
//                    List<Tag> tagThatExistInThisGiftCertificate = tagService.getAllTagsByCertificateId(id);
//
//                    if (!tagThatExistInThisGiftCertificate.isEmpty()) {
//                        giftCertificateRepository.deleteGiftCertificateTagRelationship(tagService.getTagsIdByTags(tagThatExistInThisGiftCertificate), id);
//                    }
//                    if (!tags.isEmpty()) {
//                        tagService.isTagsExistOrElseCreate(tags);
//                        giftCertificateRepository.createGiftCertificateTagRelationship(tagService.getTagsIdByTags(tags), id);
//                    }
//                }
//                return giftCertificateRepository.getCertificateById(id);
//            } else {
//                throw new ItemNotFoundException("There is no gift certificate to update with id= " + id);
//            }
//        }else{
//            throw new ServerException("The certificate with name "+updatesMap.get(NAME)+" has already existed");
//        }
//    }
    public long getGiftCertificateId(String giftCertificateName) {
        return giftCertificateRepository.getIdByGiftCertificateName(giftCertificateName);
    }
}

