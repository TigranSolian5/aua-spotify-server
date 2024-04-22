package edu.aua.spotifyserver.common.mongodb

import org.bson.codecs.ObjectIdGenerator

private val objectIdGenerator = ObjectIdGenerator()

fun generateObjectId(): String = objectIdGenerator.generate().toString()
