package dev.stocky37.tierlists.core

import com.github.slugify.Slugify
import dev.stocky37.tierlists.db.CharacterEntity
import dev.stocky37.tierlists.model.GameCharacter
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepository
import io.smallrye.mutiny.Uni
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class CharacterService @Inject constructor(
	private val slugifier: Slugify
) : ReactivePanacheMongoRepository<CharacterEntity> {

	fun create(character: GameCharacter, gameId: String): Uni<GameCharacter> {
		return persist(toEntity(character, ObjectId(gameId))).map(::fromEntity)
	}

	fun list(gameId: String): Uni<List<GameCharacter>> {
		return find("gameId = ?1", ObjectId(gameId)).list().map { list -> list.map(::fromEntity) }
	}

	fun get(id: String, gameId: String): Uni<GameCharacter?> {
		return findById(id, gameId).map(::fromNullableEntity)
	}

	fun findById(id: String, gameId: String): Uni<CharacterEntity?> {
		return if (ObjectId.isValid(id)) {
			findById(ObjectId(id), ObjectId(gameId))
		} else {
			findBySlug(id, ObjectId(gameId))
		}
	}

	fun delete(id: String, gameId: String): Uni<Void> {
		return findById(id, gameId).onItem()
			.ifNotNull()
			.transformToUni { e -> delete(e!!) }
			.replaceWithVoid()
	}

	fun findById(id: ObjectId, gameId: ObjectId): Uni<CharacterEntity?> {
		return find("id = ?1 and gameId = ?2", id, gameId).firstResult()
	}

	fun findBySlug(slug: String, gameId: ObjectId): Uni<CharacterEntity?> {
		return find("slug = ?1 and gameId = ?2", slug, gameId).firstResult()
	}

	fun toEntity(character: GameCharacter, gameId: ObjectId): CharacterEntity {
		return CharacterEntity(
			id = if (character.id == null) null else ObjectId(character.id),
			gameId = gameId,
			name = character.name,
			slug = character.slug ?: slugifier.slugify(character.name),
			imageSm = character.imageSm,
			imageLg = character.imageLg
		)
	}

	fun fromEntity(entity: CharacterEntity): GameCharacter {
		return GameCharacter(
			id = entity.id?.toString(),
			name = entity.name,
			slug = entity.slug,
			imageSm = entity.imageSm,
			imageLg = entity.imageLg
		)
	}

	fun fromNullableEntity(entity: CharacterEntity?): GameCharacter? {
		return if (entity == null) {
			null
		} else {
			fromEntity(entity)
		}
	}


}
