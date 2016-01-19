/**
 * Copyright (c) Life Technologies 2012. All rights reserved.
 * <p/>
 * The license terms of this software are contained in LICENSE.txt which accompanies this distribution.
 */
package kumsher.ryan.finra.web.rest.phonenumber;

import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kumsher.ryan.finra.service.PhoneNumberService;
import kumsher.ryan.finra.web.rest.util.PaginationUtil;

/**
 * Controller for viewing and generating phone number permutations.
 */
@RestController
@RequestMapping("/api")
public class PhoneNumberResource {

    @Inject
    private PhoneNumberService phoneNumberService;

    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/phonenumbers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getAllPhoneNumberPermutations(String phoneNumber, Pageable pageable)
        throws URISyntaxException {
        Page<String> page = phoneNumberService.generateAllPossibleAlphanumericRepresentations(phoneNumber, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/phonenumbers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
