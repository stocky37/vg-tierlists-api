package dev.stocky37.tierlists.db

import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntityBase
import org.bson.codecs.pojo.annotations.BsonCreator
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@MongoEntity(clientName = "app", database = "tierlists", collection = "characters")
data class CharacterEntity @BsonCreator constructor(
	@BsonId var id: ObjectId?,
	@BsonProperty("name") val name: String,
	@BsonProperty("slug") val slug: String,

	@field:BsonProperty("image_sm")
	@param:BsonProperty("image_sm")
	val imageSm: String,

	@field:BsonProperty("image_lg")
	@param:BsonProperty("image_lg")
	val imageLg: String,

	@field:BsonProperty("game_id")
	@param:BsonProperty("game_id")
	val gameId: ObjectId?,
) : PanacheMongoEntityBase()
