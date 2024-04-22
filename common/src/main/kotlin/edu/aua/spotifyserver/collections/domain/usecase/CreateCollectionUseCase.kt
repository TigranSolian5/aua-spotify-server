package edu.aua.spotifyserver.collections.domain.usecase

import edu.aua.spotifyserver.collections.domain.model.Collection
import edu.aua.spotifyserver.collections.domain.model.props.CreateCollectionProps
import edu.aua.spotifyserver.collections.domain.usecase.validator.CollectionTypeSpecificPropertiesValidator
import edu.aua.spotifyserver.collections.persistence.CollectionsPersistenceAdapter
import edu.aua.spotifyserver.common.mongodb.generateObjectId
import org.springframework.stereotype.Component

@Component
class CreateCollectionUseCase(
    private val adapter: CollectionsPersistenceAdapter,
    private val validatorsByType: Map<Collection.Type, CollectionTypeSpecificPropertiesValidator>,
) {

    suspend fun create(props: CreateCollectionProps): Collection {
        prepareValidatedProperties(props)

        val collection = Collection(
            id = generateObjectId(),
            type = props.type,
            ownerId = props.ownerId,
            caption = props.caption,
            subCaption = props.subCaption,
            displayImageUrl = props.displayImageUrl,
            trackCount = props.trackCount,
            duration = props.duration,
            genres = props.genres,
            typeSpecificProperties = props.typeSpecificProperties,
        )

        return adapter.save(collection)
    }

    private suspend fun prepareValidatedProperties(props: CreateCollectionProps) {
        val validator = validatorsByType[props.type]
        validator?.prepareValidatedProps(props)
    }
}
