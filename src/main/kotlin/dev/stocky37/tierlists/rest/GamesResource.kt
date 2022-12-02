package dev.stocky37.tierlists.rest

import dev.stocky37.tierlists.core.GameService
import dev.stocky37.tierlists.model.Game
import dev.stocky37.tierlists.rest.api.GameApi
import dev.stocky37.tierlists.rest.api.GamesApi
import io.smallrye.mutiny.Uni
import javax.inject.Inject

class GamesResource @Inject constructor(
	private val gameResource: GameApi,
	private val svc: GameService
) : GamesApi {

	override fun list(): Uni<List<Game>> = svc.list()

	override fun create(resource: Game): Uni<Game> = svc.create(resource)

	override fun get(): GameApi = gameResource
}
