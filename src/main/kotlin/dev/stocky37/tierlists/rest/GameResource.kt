package dev.stocky37.tierlists.rest

import dev.stocky37.tierlists.core.GameService
import dev.stocky37.tierlists.model.Game
import dev.stocky37.tierlists.rest.api.GameApi
import io.smallrye.mutiny.Uni
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Path


@ApplicationScoped
class GameResource @Inject constructor(
	private val svc: GameService,
	private val characters: CharacterResource
) : GameApi {

	override fun get(id: String): Uni<Game?> = svc.get(id)

	override fun delete(id: String): Uni<Void> = svc.delete(id)

	@Path("characters")
	fun characters() = characters
}
