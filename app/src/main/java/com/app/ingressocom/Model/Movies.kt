package com.app.ingressocom.Model

data class ApiResponse(
    val items: List<Movie>
)

data class Movie(
    val b2BEventId: String?,
    val cities: List<String>,
    val id: String,
    val title: String,
    val originalTitle: String,
    val type: String,
    val movieIdUrl: String,
    val ancineId: String,
    val countryOrigin: String,
    val priority: Int,
    val contentRating: String,
    val duration: String,
    val rating: Double,
    val synopsis: String,
    val cast: String,
    val director: String,
    val distributor: String,
    val inPreSale: Boolean,
    val isReexhibition: Boolean,
    val urlKey: String,
    val isPlaying: Boolean,
    val countIsPlaying: Int,
    val premiereDate: PremiereDate?, // Altere aqui
    val creationDate: String,
    val city: String,
    val siteURL: String,
    val nationalSiteURL: String,
    val images: List<Image>,
    val genres: List<String>,
    val ratingDescriptors: List<String>,
    val accessibilityHubs: List<Any>,
    val completeTags: List<Any>,
    val tags: List<Any>,
    val trailers: List<Trailer>,
    val partnershipType: String?
)

data class Image(
    val url: String,
    val type: String
)

data class Trailer(
    val type: String,
    val url: String,
    val embeddedUrl: String
)

data class PremiereDate(
    val localDate: String,
    val isToday: Boolean,
    val dayOfWeek: String,
    val dayAndMonth: String,
    val hour: String,
    val year: String
)
