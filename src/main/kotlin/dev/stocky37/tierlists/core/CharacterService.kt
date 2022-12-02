package dev.stocky37.tierlists.core

import com.github.slugify.Slugify
import dev.stocky37.tierlists.db.CharacterEntity
import dev.stocky37.tierlists.db.GameEntity
import dev.stocky37.tierlists.model.GameCharacter
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoRepository
import io.smallrye.mutiny.Uni
import org.bson.types.ObjectId
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class CharacterService @Inject constructor(
	private val slugifier: Slugify,
	private val games: GameService
) : ReactivePanacheMongoRepository<CharacterEntity> {

//	fun findById(id: String, gameId: String): CharacterEntity? {
//
//	}

	fun findById(id: String, gameId: ObjectId): Uni<CharacterEntity?> {
		return find("id = ?1 and gameId = ?2").firstResult()
	}

	fun create(character: GameCharacter, gameId: String): Uni<GameCharacter?> {
		return games.findById(gameId)
			.onItem().ifNotNull().transformToUni { game -> persist(toEntity(character, game?.id!!)) }
			.onItem().ifNotNull().transform(::fromEntity)
	}

	fun list(gameId: String): Uni<List<GameCharacter>?> {
		return games.findById(gameId)
			.onItem().ifNotNull().transformToUni { game: GameEntity? -> find("gameId = ?1", game?.id).list() }
			.onItem().ifNotNull().transform { list -> list.map(::fromEntity) }
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

//	fun findBySlug(id: String, gameId: String) {
//
//	}
}
