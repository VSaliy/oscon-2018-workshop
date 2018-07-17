package com.oscon2018.tutorials.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.oscon2018.tutorials.models.Error;
import com.oscon2018.tutorials.models.Store;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Store locator query response
 */
@ApiModel(description = "Store locator query response")
@Validated

public class QueryResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("Hits")
  private Integer hits = null;

  @JsonProperty("TookInMillis")
  private Integer tookInMillis = null;

  @JsonProperty("Stores")
  @Valid
  private List<Store> stores = null;

  @JsonProperty("Errors")
  @Valid
  private List<Error> errors = null;

  public QueryResponse hits(Integer hits) {
    this.hits = hits;
    return this;
  }

  /**
   * Total number of hits
   * @return hits
  **/
  @ApiModelProperty(value = "Total number of hits")


  public Integer getHits() {
    return hits;
  }

  public void setHits(Integer hits) {
    this.hits = hits;
  }

  public QueryResponse tookInMillis(Integer tookInMillis) {
    this.tookInMillis = tookInMillis;
    return this;
  }

  /**
   * Time took to execute the geo distance search
   * @return tookInMillis
  **/
  @ApiModelProperty(value = "Time took to execute the geo distance search")


  public Integer getTookInMillis() {
    return tookInMillis;
  }

  public void setTookInMillis(Integer tookInMillis) {
    this.tookInMillis = tookInMillis;
  }

  public QueryResponse stores(List<Store> stores) {
    this.stores = stores;
    return this;
  }

  public QueryResponse addStoresItem(Store storesItem) {
    if (this.stores == null) {
      this.stores = new ArrayList<Store>();
    }
    this.stores.add(storesItem);
    return this;
  }

  /**
   * Collection to stores that matched the query criteria
   * @return stores
  **/
  @ApiModelProperty(value = "Collection to stores that matched the query criteria")

  @Valid

  public List<Store> getStores() {
    return stores;
  }

  public void setStores(List<Store> stores) {
    this.stores = stores;
  }

  public QueryResponse errors(List<Error> errors) {
    this.errors = errors;
    return this;
  }

  public QueryResponse addErrorsItem(Error errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<Error>();
    }
    this.errors.add(errorsItem);
    return this;
  }

  /**
   * Errors occurred when executing storelocator query
   * @return errors
  **/
  @ApiModelProperty(value = "Errors occurred when executing storelocator query")

  @Valid

  public List<Error> getErrors() {
    return errors;
  }

  public void setErrors(List<Error> errors) {
    this.errors = errors;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryResponse queryResponse = (QueryResponse) o;
    return Objects.equals(this.hits, queryResponse.hits) &&
        Objects.equals(this.tookInMillis, queryResponse.tookInMillis) &&
        Objects.equals(this.stores, queryResponse.stores) &&
        Objects.equals(this.errors, queryResponse.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hits, tookInMillis, stores, errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryResponse {\n");
    
    sb.append("    hits: ").append(toIndentedString(hits)).append("\n");
    sb.append("    tookInMillis: ").append(toIndentedString(tookInMillis)).append("\n");
    sb.append("    stores: ").append(toIndentedString(stores)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

