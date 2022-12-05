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
		return create(character, ObjectId(gameId))
	}

	private fun create(character: GameCharacter, gameId: ObjectId): Uni<GameCharacter> {
		return persist(toEntity(character, gameId)).map(::fromEntity)
	}

	fun list(gameId: String): Uni<List<GameCharacter>> {
		return list(ObjectId(gameId))
	}

	private fun list(gameId: ObjectId): Uni<List<GameCharacter>> {
		return find("gameId = ?1", gameId).list().map { list -> list.map(::fromEntity) }
	}

	fun findById(id: String, gameId: ObjectId): Uni<CharacterEntity?> {
		return find("id = ?1 and gameId = ?2").firstResult()
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


}
