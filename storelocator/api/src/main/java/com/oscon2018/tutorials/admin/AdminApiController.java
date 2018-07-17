package com.oscon2018.tutorials.admin;

import com.oscon2018.tutorials.models.Index;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminApiController implements AdminApi {

    private static final Logger log = LoggerFactory.getLogger(AdminApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AdminService service;

    @org.springframework.beans.factory.annotation.Autowired
    public AdminApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Index> createIndex(@ApiParam(value = "New index to create"  )  @Valid @RequestBody Index newIndex) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                service.createIndex(newIndex);
                return new ResponseEntity<Index>(newIndex, HttpStatus.CREATED);

            } catch (Exception e) {
                log.error("Error calling createIndex on AdminService", e);
                return new ResponseEntity<Index>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Index>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteIndex(@ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName) {
        String accept = request.getHeader("Accept");
        try{
            service.deleteIndex(indexName);

        } catch(Exception ex) {
            log.error("Error calling deleteIndex on AdminService", ex);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
