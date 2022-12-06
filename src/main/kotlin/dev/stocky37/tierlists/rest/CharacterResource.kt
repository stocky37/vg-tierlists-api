package dev.stocky37.tierlists.rest

import dev.stocky37.tierlists.core.CharacterService
import dev.stocky37.tierlists.core.GameService
import dev.stocky37.tierlists.model.Game
import dev.stocky37.tierlists.model.GameCharacter
import dev.stocky37.tierlists.rest.util.NullToNotFound
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.groups.UniOnNotNull
import org.jboss.resteasy.reactive.RestPath
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@ApplicationScoped
class CharacterResource @Inject constructor(
	private val games: GameService,
	private val characters: CharacterService
) {
	@GET
	@NullToNotFound
	@Produces(MediaType.APPLICATION_JSON)
	fun list(@RestPath gameId: String): Uni<List<GameCharacter>?> {
		return getGame(gameId).transformToUni { game -> characters.list(game?.id!!) }
	}

	@POST
	@NullToNotFound
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	fun create(resource: GameCharacter, @RestPath gameId: String): Uni<GameCharacter?> {
		return getGame(gameId).transformToUni { game -> characters.create(resource, game?.id!!) }
	}

	@GET
	@Path("{id}")
	@NullToNotFound
	@Produces(MediaType.APPLICATION_JSON)
	fun get(@RestPath id: String, @RestPath gameId: String): Uni<GameCharacter?> {
		return getGame(gameId).transformToUni { game -> characters.get(id, game?.id!!) }
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	fun delete(@RestPath id: String, @RestPath gameId: String): Uni<Void> {
		return getGame(gameId).transformToUni { game -> characters.delete(id, game?.id!!) }
	}

	private fun getGame(gameId: String): UniOnNotNull<Game?> {
		return games.get(gameId).onItem().ifNotNull()
	}

}
