package dev.stocky37.tierlists.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder(alphabetic = true)
data class GameCharacter @JsonCreator constructor(
	val name: String,
	val imageSm: String,
	val imageLg: String,

	// ignore fields on input
	@field:JsonProperty(access = JsonProperty.Access.READ_ONLY) val id: String?,
	@field:JsonProperty(access = JsonProperty.Access.READ_ONLY) val slug: String?,
)
