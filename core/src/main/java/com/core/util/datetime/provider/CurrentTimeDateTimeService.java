package com.core.util.datetime.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * This class returns the current time.
 *
 * @author Petri Kainulainen
 */
@Component("dateTimeService")
public class CurrentTimeDateTimeService implements DateTimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentTimeDateTimeService.class);

    @Override
    public ZonedDateTime getCurrentDateAndTime() {
        ZonedDateTime currentDateAndTime = ZonedDateTime.now();

        LOGGER.debug("Returning current date and time: {}", currentDateAndTime);

        return currentDateAndTime;
    }
}
