package dev.stocky37.tierlists.rest.util

import javax.ws.rs.NameBinding

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@NameBinding
annotation class NullToNotFound
