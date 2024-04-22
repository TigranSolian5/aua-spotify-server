package edu.aua.spotifyserver.configs

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["edu.aua.spotifyserver.configs"])
class AppConfig
