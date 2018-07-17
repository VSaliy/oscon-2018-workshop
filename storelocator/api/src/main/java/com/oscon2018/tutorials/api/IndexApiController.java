package com.oscon2018.tutorials.api;

import com.oscon2018.tutorials.models.Error;
import com.oscon2018.tutorials.models.IndexerResponse;
import com.oscon2018.tutorials.models.QueryRequest;
import com.oscon2018.tutorials.models.QueryResponse;
import org.springframework.core.io.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class IndexApiController implements IndexApi {

    private static final Logger log = LoggerFactory.getLogger(IndexApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public IndexApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<IndexerResponse> index(@ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName,@ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile csvPayload) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<IndexerResponse>(objectMapper.readValue("{  \"IndexName\" : \"storelocator\",  \"StoresIndexed\" : [ {    \"StoreCode\" : \"LTDC - 1243\",    \"BusinessName\" : \"T-Mobile Oak Lawn\",    \"Address1\" : \"9300 S Cicero Ave\",    \"Address2\" : \"\",    \"City\" : \"Oak Lawn\",    \"State\" : \"IL\",    \"PostalCode\" : \"60453-2500\",    \"Country\" : \"US\",    \"PrimaryPhone\" : \"(708) 424-4222\",    \"Website\" : \"http://www.t-mobile.com/store/cell-phone-oak_lawn-il-998.html\",    \"Description\" : \"Visit T-Mobile Oak Lawn cell phone stores and discover T-Mobile's best smartphones, cell phones, tablets, and internet devices. View our low cost plans with no annual service contracts.\",    \"PaymentTypes\" : [ \"MasterCard\", \"Visa\", \"American Express\", \"Cash\", \"Checks\" ],    \"PrimaryCategory\" : \"Cell Phone Store\",    \"Photo\" : \"http://www.t-mobile.com/content/dam/tmo/store-locator-images/440_360_t-mobile-logo-default.jpg\",    \"Hours\" : \"11:00AM-05:00PM\",    \"Location\" : {      \"Latitude\" : \"41.7235523\",      \"Longitude\" : -87.7414581    },    \"SapId\" : \"1243\"  } ],  \"StoresFailedToIndex\" : [ {    \"StoreCode\" : \"LTDC - 1243\",    \"BusinessName\" : \"T-Mobile Oak Lawn\",    \"Address1\" : \"9300 S Cicero Ave\",    \"Address2\" : \"\",    \"City\" : \"Oak Lawn\",    \"State\" : \"IL\",    \"PostalCode\" : \"60453-2500\",    \"Country\" : \"US\",    \"PrimaryPhone\" : \"(708) 424-4222\",    \"Website\" : \"http://www.t-mobile.com/store/cell-phone-oak_lawn-il-998.html\",    \"Description\" : \"Visit T-Mobile Oak Lawn cell phone stores and discover T-Mobile's best smartphones, cell phones, tablets, and internet devices. View our low cost plans with no annual service contracts.\",    \"PaymentTypes\" : [ \"MasterCard\", \"Visa\", \"American Express\", \"Cash\", \"Checks\" ],    \"PrimaryCategory\" : \"Cell Phone Store\",    \"Photo\" : \"http://www.t-mobile.com/content/dam/tmo/store-locator-images/440_360_t-mobile-logo-default.jpg\",    \"Hours\" : \"11:00AM-05:00PM\",    \"Location\" : {      \"Latitude\" : \"41.7235523\",      \"Longitude\" : -87.7414581    },    \"SapId\" : \"1243\"  } ]}", IndexerResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<IndexerResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<IndexerResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<QueryResponse> queryStores(@ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName,@ApiParam(value = "" ,required=true )  @Valid @RequestBody QueryRequest queryRequest) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<QueryResponse>(objectMapper.readValue("{  \"Errors\" : [ {    \"Description\" : \"This is a sample error\",    \"Code\" : \"001\"  }, {    \"Description\" : \"This is a sample error\",    \"Code\" : \"001\"  } ],  \"Hits\" : 0,  \"TookInMillis\" : 6,  \"Stores\" : [ {    \"StoreCode\" : \"LTDC - 1243\",    \"BusinessName\" : \"T-Mobile Oak Lawn\",    \"Address1\" : \"9300 S Cicero Ave\",    \"Address2\" : \"\",    \"City\" : \"Oak Lawn\",    \"State\" : \"IL\",    \"PostalCode\" : \"60453-2500\",    \"Country\" : \"US\",    \"PrimaryPhone\" : \"(708) 424-4222\",    \"Website\" : \"http://www.t-mobile.com/store/cell-phone-oak_lawn-il-998.html\",    \"Description\" : \"Visit T-Mobile Oak Lawn cell phone stores and discover T-Mobile's best smartphones, cell phones, tablets, and internet devices. View our low cost plans with no annual service contracts.\",    \"PaymentTypes\" : [ \"MasterCard\", \"Visa\", \"American Express\", \"Cash\", \"Checks\" ],    \"PrimaryCategory\" : \"Cell Phone Store\",    \"Photo\" : \"http://www.t-mobile.com/content/dam/tmo/store-locator-images/440_360_t-mobile-logo-default.jpg\",    \"Hours\" : \"11:00AM-05:00PM\",    \"Location\" : {      \"Latitude\" : \"41.7235523\",      \"Longitude\" : -87.7414581    },    \"SapId\" : \"1243\"  }, {    \"StoreCode\" : \"LTDC - 1243\",    \"BusinessName\" : \"T-Mobile Oak Lawn\",    \"Address1\" : \"9300 S Cicero Ave\",    \"Address2\" : \"\",    \"City\" : \"Oak Lawn\",    \"State\" : \"IL\",    \"PostalCode\" : \"60453-2500\",    \"Country\" : \"US\",    \"PrimaryPhone\" : \"(708) 424-4222\",    \"Website\" : \"http://www.t-mobile.com/store/cell-phone-oak_lawn-il-998.html\",    \"Description\" : \"Visit T-Mobile Oak Lawn cell phone stores and discover T-Mobile's best smartphones, cell phones, tablets, and internet devices. View our low cost plans with no annual service contracts.\",    \"PaymentTypes\" : [ \"MasterCard\", \"Visa\", \"American Express\", \"Cash\", \"Checks\" ],    \"PrimaryCategory\" : \"Cell Phone Store\",    \"Photo\" : \"http://www.t-mobile.com/content/dam/tmo/store-locator-images/440_360_t-mobile-logo-default.jpg\",    \"Hours\" : \"11:00AM-05:00PM\",    \"Location\" : {      \"Latitude\" : \"41.7235523\",      \"Longitude\" : -87.7414581    },    \"SapId\" : \"1243\"  } ]}", QueryResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<QueryResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<QueryResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
