package com.oscon2018.tutorials.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Store locator query request
 */
@ApiModel(description = "Store locator query request")
@Validated

public class QueryRequest  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("Lat")
  private BigDecimal lat = null;

  @JsonProperty("Lon")
  private BigDecimal lon = null;

  @JsonProperty("Distance")
  private Integer distance = null;

  @JsonProperty("SapId")
  private Integer sapId = null;

  public QueryRequest lat(BigDecimal lat) {
    this.lat = lat;
    return this;
  }

  /**
   * Latitude
   * @return lat
  **/
  @ApiModelProperty(value = "Latitude")

  @Valid

  public BigDecimal getLat() {
    return lat;
  }

  public void setLat(BigDecimal lat) {
    this.lat = lat;
  }

  public QueryRequest lon(BigDecimal lon) {
    this.lon = lon;
    return this;
  }

  /**
   * Longitude
   * @return lon
  **/
  @ApiModelProperty(value = "Longitude")

  @Valid

  public BigDecimal getLon() {
    return lon;
  }

  public void setLon(BigDecimal lon) {
    this.lon = lon;
  }

  public QueryRequest distance(Integer distance) {
    this.distance = distance;
    return this;
  }

  /**
   * Geo distance
   * @return distance
  **/
  @ApiModelProperty(value = "Geo distance")


  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public QueryRequest sapId(Integer sapId) {
    this.sapId = sapId;
    return this;
  }

  /**
   * Sap identifier
   * @return sapId
  **/
  @ApiModelProperty(value = "Sap identifier")


  public Integer getSapId() {
    return sapId;
  }

  public void setSapId(Integer sapId) {
    this.sapId = sapId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryRequest queryRequest = (QueryRequest) o;
    return Objects.equals(this.lat, queryRequest.lat) &&
        Objects.equals(this.lon, queryRequest.lon) &&
        Objects.equals(this.distance, queryRequest.distance) &&
        Objects.equals(this.sapId, queryRequest.sapId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, lon, distance, sapId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryRequest {\n");
    
    sb.append("    lat: ").append(toIndentedString(lat)).append("\n");
    sb.append("    lon: ").append(toIndentedString(lon)).append("\n");
    sb.append("    distance: ").append(toIndentedString(distance)).append("\n");
    sb.append("    sapId: ").append(toIndentedString(sapId)).append("\n");
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

