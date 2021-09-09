package io.getstream.chat.virtualevent.util

/**
 * Mock user companies according to the design.
 */
private val COMPANY_LIST: Set<String> = setOf(
    "Reilly LLC",
    "Miller, O'Conner and Nicolas",
    "Grimes, Bashirian and Nolan",
    "Borer, Hamill and Mante",
    "Steuber Inc",
    "Baumbach, Weissnat and Herzog",
    "Upton, Pfeffer and Hodkiewicz",
    "Mraz Group",
    "Harvey Inc",
    "Kihn LLC",
    "Designer. Speaker, Lifelong Learner.",
    "Feest - Lakin",
    "Kihn - Parisian",
    "Nolan Inc",
    "Keebler LLC",
    "Huels - Daniel"
)

/**
 * Returns a random company name.
 */
fun randomCompany(): String = COMPANY_LIST.random()
