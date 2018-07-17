package com.oscon2018.tutorials.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * IndexerResponse
 */
@Validated

public class IndexerResponse  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("IndexName")
  private String indexName = null;

  @JsonProperty("StoresIndexed")
  @Valid
  private List<Store> storesIndexed = null;

  @JsonProperty("StoresFailedToIndex")
  @Valid
  private List<Store> storesFailedToIndex = null;

  public IndexerResponse indexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  /**
   * index name
   * @return indexName
  **/
  @ApiModelProperty(example = "storelocator", value = "index name")


  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public IndexerResponse storesIndexed(List<Store> storesIndexed) {
    this.storesIndexed = storesIndexed;
    return this;
  }

  public IndexerResponse addStoresIndexedItem(Store storesIndexedItem) {
    if (this.storesIndexed == null) {
      this.storesIndexed = new ArrayList<Store>();
    }
    this.storesIndexed.add(storesIndexedItem);
    return this;
  }

  /**
   * stores that got successfully added to index
   * @return storesIndexed
  **/
  @ApiModelProperty(value = "stores that got successfully added to index")

  @Valid

  public List<Store> getStoresIndexed() {
    return storesIndexed;
  }

  public void setStoresIndexed(List<Store> storesIndexed) {
    this.storesIndexed = storesIndexed;
  }

  public IndexerResponse storesFailedToIndex(List<Store> storesFailedToIndex) {
    this.storesFailedToIndex = storesFailedToIndex;
    return this;
  }

  public IndexerResponse addStoresFailedToIndexItem(Store storesFailedToIndexItem) {
    if (this.storesFailedToIndex == null) {
      this.storesFailedToIndex = new ArrayList<Store>();
    }
    this.storesFailedToIndex.add(storesFailedToIndexItem);
    return this;
  }

  /**
   * stores documents that filed to index
   * @return storesFailedToIndex
  **/
  @ApiModelProperty(value = "stores documents that filed to index")

  @Valid

  public List<Store> getStoresFailedToIndex() {
    return storesFailedToIndex;
  }

  public void setStoresFailedToIndex(List<Store> storesFailedToIndex) {
    this.storesFailedToIndex = storesFailedToIndex;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IndexerResponse indexerResponse = (IndexerResponse) o;
    return Objects.equals(this.indexName, indexerResponse.indexName) &&
        Objects.equals(this.storesIndexed, indexerResponse.storesIndexed) &&
        Objects.equals(this.storesFailedToIndex, indexerResponse.storesFailedToIndex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indexName, storesIndexed, storesFailedToIndex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IndexerResponse {\n");
    
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
    sb.append("    storesIndexed: ").append(toIndentedString(storesIndexed)).append("\n");
    sb.append("    storesFailedToIndex: ").append(toIndentedString(storesFailedToIndex)).append("\n");
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

