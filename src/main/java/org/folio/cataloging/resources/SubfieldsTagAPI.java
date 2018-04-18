package org.folio.cataloging.resources;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.folio.cataloging.ModCataloging;
import org.folio.cataloging.log.MessageCatalog;
import org.folio.cataloging.resources.domain.SubfieldsTag;
import org.folio.cataloging.shared.Validation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * Subfield codes for Tag entity RESTful APIs.
 *
 * @author natasciab
 * @since 1.0
 */
@RestController
@Api(value = "modcat-api", description = "Subfield resource API")
@RequestMapping(value = ModCataloging.BASE_URI, produces = "application/json")
public class SubfieldsTagAPI extends BaseResource {

    @ApiOperation(value = "Returns the subfield tag associated with the input data.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Method successfully returned the requested tag."),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 414, message = "Request-URI Too Long"),
            @ApiResponse(code = 500, message = "System internal failure occurred.")
    })
    @GetMapping("/subfield-tag")
    public void getSubfieldsTag(  final String marcCategory,
                                            final String code1,
                                            final String code2,
                                            final String code3,
                                            final String lang,
                                            final Map<String, String> okapiHeaders,
                                            final Handler<AsyncResult<Response>> asyncResultHandler,
                                            final Context vertxContext) throws Exception {
        doGet((storageService, configuration, future) -> {
                try {
                    final int category = Integer.parseInt(marcCategory);

                    final Validation validation = storageService.getSubfieldsByCorrelations(
                            category,
                            Integer.parseInt(code1),
                            Integer.parseInt(code2),
                            Integer.parseInt(code3));

                    final SubfieldsTag subfieldsTag = new SubfieldsTag();
                    subfieldsTag.setCategory(category);
                    subfieldsTag.setDefaultSubfield(String.valueOf(validation.getMarcTagDefaultSubfieldCode()));
                    subfieldsTag.setSubfields(stream(validation.getMarcValidSubfieldStringCode().split("")).collect(toList()));
                    subfieldsTag.setRepeatable(stream(validation.getRepeatableSubfieldStringCode().split("")).collect(toList()));
                    subfieldsTag.setTag(validation.getKey().getMarcTag());
                    return subfieldsTag;
                } catch (final Exception exception) {
                    logger.error(MessageCatalog._00010_DATA_ACCESS_FAILURE, exception);
                    return null;
                }
            }, asyncResultHandler, okapiHeaders, vertxContext);
    }
}
