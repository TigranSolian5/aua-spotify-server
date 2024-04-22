package edu.aua.spotifyserver.configs.collections

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.domain.usecase.validator.CollectionTypeSpecificPropertiesValidator
import edu.aua.spotifyserver.collections.domain.usecase.validator.PlaylistPropertiesValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CollectionsConfig {

    @Bean
    fun validatorsByCollectionTypeMap(
        playlistPropertiesValidator: PlaylistPropertiesValidator,
    ): Map<Collection.Type, CollectionTypeSpecificPropertiesValidator> {
        return mapOf(
            Collection.Type.playlist to playlistPropertiesValidator
        )
    }
}
