package dev.stocky37.tierlists.rest

import dev.stocky37.tierlists.core.CharacterService
import dev.stocky37.tierlists.model.GameCharacter
import io.smallrye.mutiny.Uni
import org.jboss.resteasy.reactive.RestPath
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@ApplicationScoped
class CharacterResource @Inject constructor(
	private val svc: CharacterService
) {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	fun list(@RestPath gameId: String): Uni<List<GameCharacter>?> = svc.list(gameId)

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	fun create(resource: GameCharacter, @RestPath gameId: String): Uni<GameCharacter?> = svc.create(resource, gameId)

}
