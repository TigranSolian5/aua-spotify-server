package edu.aua.spotifyserver.collections.domain.usecase.validator

import edu.aua.spotifyserver.collections.domain.model.props.CreateCollectionProps

interface CollectionTypeSpecificPropertiesValidator {
    suspend fun prepareValidatedProps(props: CreateCollectionProps)
}
