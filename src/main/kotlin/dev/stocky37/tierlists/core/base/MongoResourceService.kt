package dev.stocky37.tierlists.core.base

import io.quarkus.mongodb.panache.kotlin.PanacheMongoEntityBase
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepository
import io.smallrye.mutiny.Uni
import org.bson.types.ObjectId


abstract class MongoResourceService<Resource : Any, Entity : PanacheMongoEntityBase>
	: ResourceService<Resource>, ReactivePanacheMongoRepository<Entity> {

	override fun get(id: String): Uni<Resource?> {
		return findById(id).map(::fromNullableEntity)
	}

	override fun list(): Uni<List<Resource>> {
		return findAll().list().map { list -> list.map(::fromEntity) }
	}

	override fun create(resource: Resource): Uni<Resource> {
		return persist(toEntity(resource)).map(::fromEntity)
	}

	override fun delete(id: String): Uni<Void> {
		return findById(id).onItem()
			.ifNotNull()
			.transformToUni { e -> delete(e!!) }
			.replaceWithVoid()
	}

	open fun findById(id: String): Uni<Entity?> {
		return findById(ObjectId(id))
	}

	fun fromNullableEntity(entity: Entity?): Resource? {
		return if (entity == null) {
			null
		} else {
			fromEntity(entity)
		}
	}

	protected abstract fun toEntity(resource: Resource): Entity

	protected abstract fun fromEntity(entity: Entity): Resource


}
