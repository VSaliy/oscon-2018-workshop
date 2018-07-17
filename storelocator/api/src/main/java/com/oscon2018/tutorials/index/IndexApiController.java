package com.oscon2018.tutorials.index;

import com.oscon2018.tutorials.models.IndexerResponse;
import com.oscon2018.tutorials.models.QueryRequest;
import com.oscon2018.tutorials.models.QueryResponse;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class IndexApiController implements IndexApi {

    @Autowired
    private IndexService service;

    private static final Logger log = LoggerFactory.getLogger(IndexApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public IndexApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<IndexerResponse> index(
            @ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName,
            @ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile csvPayload) {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            try{
                IndexerResponse response = service.indexData(indexName, csvPayload.getInputStream());
                return new ResponseEntity<IndexerResponse>(response, HttpStatus.OK);

            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<IndexerResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<IndexerResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<QueryResponse> queryStores(
            @ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName,
            @ApiParam(value = "" ,required=true )  @Valid @RequestBody QueryRequest queryRequest) {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                QueryResponse response = service.query(indexName, queryRequest);
                return new ResponseEntity<QueryResponse>(response, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Error querying for stores", e);
                return new ResponseEntity<QueryResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<QueryResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
