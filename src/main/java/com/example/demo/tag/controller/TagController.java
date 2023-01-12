package com.example.demo.tag.controller;

import com.example.demo.exceptionhandler.exception.ServerException;
import com.example.demo.tag.model.Tag;
import com.example.demo.tag.service.TagService;
import com.example.demo.utils.validation.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Tag tag) {
        if (DataValidation.isValidTag(tag)) {

            return ResponseEntity.ok(tagService.createTag(tag));
        }
        throw new ServerException("Something went wrong during the request, check your fields");
    }

    @GetMapping
    public ResponseEntity<?> read() {
        return new ResponseEntity<>(Map.of("tags", tagService.getAllTags()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable("id") long id) {
        if (DataValidation.moreThenZero(id)) {
            return new ResponseEntity<>(Map.of("tag", tagService.getTagById(id)), HttpStatus.OK);
        }
        throw new ServerException("The Tag ID is not valid: id = " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        if (DataValidation.moreThenZero(id)) {

            tagService.deleteTag(id);
            return ResponseEntity.noContent().build();
        }
        throw new ServerException("The Tag ID is not valid: id = " + id);
    }
}
