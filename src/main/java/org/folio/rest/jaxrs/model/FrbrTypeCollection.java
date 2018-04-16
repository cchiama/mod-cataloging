package org.folio.rest.jaxrs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "frbrTypes" })
public class FrbrTypeCollection {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("frbrTypes")
    @Valid
    @NotNull
    private List<FrbrType> frbrTypes = new ArrayList<FrbrType>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The frbrTypes
     */
    @JsonProperty("frbrTypes")
    public List<FrbrType> getFrbrTypes() {
        return frbrTypes;
    }

    /**
     * 
     * (Required)
     * 
     * @param frbrTypes
     *     The frbrTypes
     */
    @JsonProperty("frbrTypes")
    public void setFrbrTypes(List<FrbrType> frbrTypes) {
        this.frbrTypes = frbrTypes;
    }

    public FrbrTypeCollection withFrbrTypes(List<FrbrType> frbrTypes) {
        this.frbrTypes = frbrTypes;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public FrbrTypeCollection withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
