package com.example.demo.tag.service;

import com.example.demo.exceptionhandler.exception.ItemNotFoundException;
import com.example.demo.exceptionhandler.exception.ServerException;
import com.example.demo.tag.model.Tag;
import com.example.demo.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagService {
    private final TagRepository tagRepository;


    public Tag createTag(Tag tag) {
        if (isTagExistsByName(tag.getName())) {
            return tagRepository.save(tag);
        }

        throw new ServerException("This tag with (name = " + tag.getName() + ") has already existed");
    }

    public boolean isTagExistsByName(String name) {
        return !tagRepository.isTagExistsByName(name);
    }

    public List<Tag> getAllTags() {

        return tagRepository.findAll();
    }

    public Tag getTagById(long id) {

        return tagRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("There are no tags with ID = " + id));
    }

    public boolean deleteTag(long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);

            return true;
        }
        throw new ItemNotFoundException("There is no tag with id = " + id);
    }

    public boolean isTagsExistOrElseCreate(List<Tag> tags) {
        for (Tag tag : tags) {
            if (isTagExistsByName(tag.getName())) {
                createTag(tag);
            }
        }
        return true;
    }

    public long getTagIdByTag(Tag tag) {
        return tagRepository.getIdByTagName(tag.getName());
    }

    public List<Long> getTagsIdByTags(List<Tag> tags) {
        List<Long> listTagsId = new ArrayList<>();
        for (Tag tag : tags) {
            listTagsId.add(getTagIdByTag(tag));
        }
        return listTagsId;
    }

    public List<Tag> getAllTagsByGiftCertificateId(long id) {
        return tagRepository.getAllTagsByGiftCertificateId(id);
    }

    public Set<Tag> verifyIsTagsExistWhenCreatingGiftCertificate(Set<Tag> tags) {
        tags.forEach(tag -> {
            if (!isTagExistsByName(tag.getName())) {
                tag.setId(getTagIdByTag(tag));
            }
        });
        return tags;
    }
}
