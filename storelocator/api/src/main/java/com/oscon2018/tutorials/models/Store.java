package com.oscon2018.tutorials.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.oscon2018.tutorials.models.Location;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * store document
 */
@ApiModel(description = "store document")
@Validated

public class Store  implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("StoreCode")
  private String storeCode = null;

  @JsonProperty("BusinessName")
  private String businessName = null;

  @JsonProperty("Address1")
  private String address1 = null;

  @JsonProperty("Address2")
  private String address2 = null;

  @JsonProperty("City")
  private String city = null;

  @JsonProperty("State")
  private String state = null;

  @JsonProperty("PostalCode")
  private String postalCode = null;

  @JsonProperty("Country")
  private String country = null;

  @JsonProperty("PrimaryPhone")
  private String primaryPhone = null;

  @JsonProperty("Website")
  private String website = null;

  @JsonProperty("Description")
  private String description = null;

  @JsonProperty("PaymentTypes")
  @Valid
  private List<String> paymentTypes = null;

  @JsonProperty("PrimaryCategory")
  private String primaryCategory = null;

  @JsonProperty("Photo")
  private String photo = null;

  @JsonProperty("Hours")
  private String hours = null;

  @JsonProperty("Location")
  private Location location = null;

  @JsonProperty("SapId")
  private String sapId = null;

  public Store storeCode(String storeCode) {
    this.storeCode = storeCode;
    return this;
  }

  /**
   * Store code
   * @return storeCode
  **/
  @ApiModelProperty(required = true, value = "Store code")
  @NotNull


  public String getStoreCode() {
    return storeCode;
  }

  public void setStoreCode(String storeCode) {
    this.storeCode = storeCode;
  }

  public Store businessName(String businessName) {
    this.businessName = businessName;
    return this;
  }

  /**
   * Business name
   * @return businessName
  **/
  @ApiModelProperty(required = true, value = "Business name")
  @NotNull


  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public Store address1(String address1) {
    this.address1 = address1;
    return this;
  }

  /**
   * Address line 1
   * @return address1
  **/
  @ApiModelProperty(required = true, value = "Address line 1")
  @NotNull


  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public Store address2(String address2) {
    this.address2 = address2;
    return this;
  }

  /**
   * Address line 2
   * @return address2
  **/
  @ApiModelProperty(value = "Address line 2")


  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public Store city(String city) {
    this.city = city;
    return this;
  }

  /**
   * City
   * @return city
  **/
  @ApiModelProperty(required = true, value = "City")
  @NotNull


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Store state(String state) {
    this.state = state;
    return this;
  }

  /**
   * state
   * @return state
  **/
  @ApiModelProperty(required = true, value = "state")
  @NotNull


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public Store postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Postal code
   * @return postalCode
  **/
  @ApiModelProperty(required = true, value = "Postal code")
  @NotNull


  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public Store country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Country
   * @return country
  **/
  @ApiModelProperty(required = true, value = "Country")
  @NotNull


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Store primaryPhone(String primaryPhone) {
    this.primaryPhone = primaryPhone;
    return this;
  }

  /**
   * Primary phone number for the store
   * @return primaryPhone
  **/
  @ApiModelProperty(required = true, value = "Primary phone number for the store")
  @NotNull


  public String getPrimaryPhone() {
    return primaryPhone;
  }

  public void setPrimaryPhone(String primaryPhone) {
    this.primaryPhone = primaryPhone;
  }

  public Store website(String website) {
    this.website = website;
    return this;
  }

  /**
   * Store website URL
   * @return website
  **/
  @ApiModelProperty(value = "Store website URL")


  public String getWebsite() {
    return website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public Store description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Store description
   * @return description
  **/
  @ApiModelProperty(value = "Store description")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Store paymentTypes(List<String> paymentTypes) {
    this.paymentTypes = paymentTypes;
    return this;
  }

  public Store addPaymentTypesItem(String paymentTypesItem) {
    if (this.paymentTypes == null) {
      this.paymentTypes = new ArrayList<String>();
    }
    this.paymentTypes.add(paymentTypesItem);
    return this;
  }

  /**
   * Get paymentTypes
   * @return paymentTypes
  **/
  @ApiModelProperty(value = "")


  public List<String> getPaymentTypes() {
    return paymentTypes;
  }

  public void setPaymentTypes(List<String> paymentTypes) {
    this.paymentTypes = paymentTypes;
  }

  public Store primaryCategory(String primaryCategory) {
    this.primaryCategory = primaryCategory;
    return this;
  }

  /**
   * Primary category
   * @return primaryCategory
  **/
  @ApiModelProperty(value = "Primary category")


  public String getPrimaryCategory() {
    return primaryCategory;
  }

  public void setPrimaryCategory(String primaryCategory) {
    this.primaryCategory = primaryCategory;
  }

  public Store photo(String photo) {
    this.photo = photo;
    return this;
  }

  /**
   * Store photo URL
   * @return photo
  **/
  @ApiModelProperty(value = "Store photo URL")


  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Store hours(String hours) {
    this.hours = hours;
    return this;
  }

  /**
   * Store hours of operation
   * @return hours
  **/
  @ApiModelProperty(value = "Store hours of operation")


  public String getHours() {
    return hours;
  }

  public void setHours(String hours) {
    this.hours = hours;
  }

  public Store location(Location location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Store sapId(String sapId) {
    this.sapId = sapId;
    return this;
  }

  /**
   * SAP Identifier
   * @return sapId
  **/
  @ApiModelProperty(value = "SAP Identifier")


  public String getSapId() {
    return sapId;
  }

  public void setSapId(String sapId) {
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
    Store store = (Store) o;
    return Objects.equals(this.storeCode, store.storeCode) &&
        Objects.equals(this.businessName, store.businessName) &&
        Objects.equals(this.address1, store.address1) &&
        Objects.equals(this.address2, store.address2) &&
        Objects.equals(this.city, store.city) &&
        Objects.equals(this.state, store.state) &&
        Objects.equals(this.postalCode, store.postalCode) &&
        Objects.equals(this.country, store.country) &&
        Objects.equals(this.primaryPhone, store.primaryPhone) &&
        Objects.equals(this.website, store.website) &&
        Objects.equals(this.description, store.description) &&
        Objects.equals(this.paymentTypes, store.paymentTypes) &&
        Objects.equals(this.primaryCategory, store.primaryCategory) &&
        Objects.equals(this.photo, store.photo) &&
        Objects.equals(this.hours, store.hours) &&
        Objects.equals(this.location, store.location) &&
        Objects.equals(this.sapId, store.sapId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storeCode, businessName, address1, address2, city, state, postalCode, country, primaryPhone, website, description, paymentTypes, primaryCategory, photo, hours, location, sapId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Store {\n");
    
    sb.append("    storeCode: ").append(toIndentedString(storeCode)).append("\n");
    sb.append("    businessName: ").append(toIndentedString(businessName)).append("\n");
    sb.append("    address1: ").append(toIndentedString(address1)).append("\n");
    sb.append("    address2: ").append(toIndentedString(address2)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("    primaryPhone: ").append(toIndentedString(primaryPhone)).append("\n");
    sb.append("    website: ").append(toIndentedString(website)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    paymentTypes: ").append(toIndentedString(paymentTypes)).append("\n");
    sb.append("    primaryCategory: ").append(toIndentedString(primaryCategory)).append("\n");
    sb.append("    photo: ").append(toIndentedString(photo)).append("\n");
    sb.append("    hours: ").append(toIndentedString(hours)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
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

