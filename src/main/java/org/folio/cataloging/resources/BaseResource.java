package org.folio.cataloging.resources;

import org.folio.cataloging.business.common.DataAccessException;
import org.folio.cataloging.integration.Configuration;
import org.folio.cataloging.log.Log;
import org.folio.cataloging.log.MessageCatalog;
import org.folio.cataloging.resources.domain.ErrorCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "http://localhost:3000")
public abstract class BaseResource {
    protected Log logger = new Log(getClass());

    @Autowired
    protected Configuration configurator;

    @Autowired
    protected RestTemplate CLIENT;

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Communication failure with one or more internal subsystems")
    @ExceptionHandler(SubsystemCommunicationException.class)
    public void ioFailure(final Exception exception) {
        logger.error(MessageCatalog._00013_IO_FAILURE, exception);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "System internal failure has occurred.")
    @ExceptionHandler(DataAccessException.class)
    public void dataAccessFailure(final DataAccessException exception) {
        logger.error(MessageCatalog._00011_NWS_FAILURE, exception);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "System internal failure has occurred.")
    @ExceptionHandler(SystemInternalFailureException.class)
    public ResponseEntity<Object> systemInternalFailure(final Exception exception, final ErrorCollection errors) {
        logger.error(MessageCatalog._00011_NWS_FAILURE, exception);
        return new ResponseEntity<Object>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Cannot create the requested entity.")
    @ExceptionHandler(UnableToCreateOrUpdateEntityException.class)
    public void unableToUpsertEntity(final UnableToCreateOrUpdateEntityException exception) {
        logger.error(MessageCatalog._00018_CANNOT_CREATE, exception);
    }
}
