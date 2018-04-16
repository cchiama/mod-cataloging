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
@JsonPropertyOrder({ "functionCodes" })
public class FunctionCodeCollection {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("functionCodes")
    @Valid
    @NotNull
    private List<FunctionCode> functionCodes = new ArrayList<FunctionCode>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The functionCodes
     */
    @JsonProperty("functionCodes")
    public List<FunctionCode> getFunctionCodes() {
        return functionCodes;
    }

    /**
     * 
     * (Required)
     * 
     * @param functionCodes
     *     The functionCodes
     */
    @JsonProperty("functionCodes")
    public void setFunctionCodes(List<FunctionCode> functionCodes) {
        this.functionCodes = functionCodes;
    }

    public FunctionCodeCollection withFunctionCodes(List<FunctionCode> functionCodes) {
        this.functionCodes = functionCodes;
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

    public FunctionCodeCollection withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}
