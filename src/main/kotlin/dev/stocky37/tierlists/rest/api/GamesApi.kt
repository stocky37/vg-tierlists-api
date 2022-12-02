package dev.stocky37.tierlists.rest.api

import dev.stocky37.tierlists.model.Game
import io.smallrye.mutiny.Uni
import org.eclipse.microprofile.openapi.annotations.tags.Tag
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Tag(name = "Games")
@Path("/games")
interface GamesApi {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	fun list(): Uni<List<Game>>

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	fun create(resource: Game): Uni<Game>

	@Path("{gameId}")
	fun get(): GameApi
}
