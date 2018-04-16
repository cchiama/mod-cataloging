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
@JsonPropertyOrder({ "recordStatusTypes" })
public class RecordStatusTypeCollection {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("recordStatusTypes")
    @Valid
    @NotNull
    private List<RecordStatusType> recordStatusTypes = new ArrayList<RecordStatusType>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The recordStatusTypes
     */
    @JsonProperty("recordStatusTypes")
    public List<RecordStatusType> getRecordStatusTypes() {
        return recordStatusTypes;
    }

    /**
     * 
     * (Required)
     * 
     * @param recordStatusTypes
     *     The recordStatusTypes
     */
    @JsonProperty("recordStatusTypes")
    public void setRecordStatusTypes(List<RecordStatusType> recordStatusTypes) {
        this.recordStatusTypes = recordStatusTypes;
    }

    public RecordStatusTypeCollection withRecordStatusTypes(List<RecordStatusType> recordStatusTypes) {
        this.recordStatusTypes = recordStatusTypes;
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

    public RecordStatusTypeCollection withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
