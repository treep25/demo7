package com.example.demo.giftcertficate.controller;

import com.example.demo.exceptionhandler.exception.ServerException;
import com.example.demo.giftcertficate.model.GiftCertificate;
import com.example.demo.giftcertficate.service.GiftCertificateService;
import com.example.demo.utils.validation.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping(value = "/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GiftCertificate giftCertificate) {
        if (DataValidation.isValidCertificate(giftCertificate)) {

            return ResponseEntity.ok(giftCertificateService.createGiftCertificate(giftCertificate));
        }
        throw new ServerException("Something went wrong during the request, check your fields");
    }

    @GetMapping
    public ResponseEntity<?> read() {
        return ResponseEntity.ok(giftCertificateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable("id") long id) {
        if (DataValidation.moreThenZero(id)) {

            GiftCertificate giftCertificateWithCurrentId = giftCertificateService.getOneGiftCertificateById(id);
            giftCertificateWithCurrentId
                    .add(linkTo(methodOn(GiftCertificateController.class)
                            .delete(giftCertificateWithCurrentId.getId()))
                            .withRel(() -> "delete gift-certificate"))
                    .add(linkTo(methodOn(GiftCertificateController.class)
                            .create(null))
                            .withRel(() -> "create gift-certificate"))
                    .add(linkTo(methodOn(GiftCertificateController.class)
                            .read())
                            .withRel(() -> "get all gift-certificates"));

            return ResponseEntity.ok(Map.of("gift certificate", giftCertificateWithCurrentId));
        }
        throw new ServerException("The Gift Certificate ID is not valid: id = " + id);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<?> updateCertificate(@RequestBody GiftCertificate giftCertificate, @PathVariable("id") long id) {
//        if (DataValidation.moreThenZero(id)) {
//
//            Optional<Map<String, String>> updatesMap = DataValidation.isGiftCertificateValidForUpdating(giftCertificate);
//
//            if (updatesMap.isPresent()) {
//                return ResponseEntity.ok(giftCertificateService.updateGiftCertificate(id, giftCertificate.getTags(), updatesMap.get()));
//            }
//            throw new ServerException("There are no fields to update");
//        }
//        throw new ServerException("The ID is not valid: id = " + id);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        if (DataValidation.moreThenZero(id)) {

            giftCertificateService.deleteGiftCertificate(id);
            return ResponseEntity.noContent().build();
        }
        throw new ServerException("The Gift Certificate ID is not valid: id = " + id);
    }
}
