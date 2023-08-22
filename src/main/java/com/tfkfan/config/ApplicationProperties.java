package com.tfkfan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Drone Factory.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    // jhipster-needle-application-properties-property
    // jhipster-needle-application-properties-property-getter
    // jhipster-needle-application-properties-property-class
    private Long defaultModelWeightLimit;
    private Long batteryLogInterval;

    public Long getDefaultModelWeightLimit() {
        return defaultModelWeightLimit;
    }

    public void setDefaultModelWeightLimit(Long defaultModelWeightLimit) {
        this.defaultModelWeightLimit = defaultModelWeightLimit;
    }

    public Long getBatteryLogInterval() {
        return batteryLogInterval;
    }

    public void setBatteryLogInterval(Long batteryLogInterval) {
        this.batteryLogInterval = batteryLogInterval;
    }
}
