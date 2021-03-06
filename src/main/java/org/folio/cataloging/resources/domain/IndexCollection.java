package org.folio.cataloging.resources.domain;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "indexes" })
public class IndexCollection {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("indexes")
    @Valid
    @NotNull
    private List<Index> indexes = new ArrayList<Index>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The indexes
     */
    @JsonProperty("indexes")
    public List<Index> getIndexes() {
        return indexes;
    }

    /**
     * 
     * (Required)
     * 
     * @param indexes
     *     The indexes
     */
    @JsonProperty("indexes")
    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public IndexCollection withIndexes(List<Index> indexes) {
        this.indexes = indexes;
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

    public IndexCollection withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
