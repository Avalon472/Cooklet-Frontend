# Cooklet Frontend

Android recipe management app built with Kotlin and Jetpack Compose. Architected around a local-first data model, all state is persisted to device storage on every mutation, so the app is fully usable on launch before any network response arrives after an initial connection.

## Highlighted Features

- **Local-first state persistence** — ViewModels initialize directly from device storage (JSON via Gson), rendering the last known state instantly on launch with no loading delay; every mutation calls `save()` immediately, applied consistently across recipes, grocery list, and user preferences
- **Graceful degradation on fetch failure** — falls back to the locally cached recipe list if the backend is unreachable, with distinct UI states and copy for "fetch failed" vs. "no recipes yet"
- **Rate limit awareness** — search ViewModel explicitly handles `429` responses from the backend's daily cap middleware, surfacing a specific "Request Limit Reached" state rather than a generic error
- **Grocery list aggregation** — adding a recipe to the list deduplicates by ingredient name and unit, incrementing quantity on existing entries rather than creating duplicate line items; ingredients are grouped by aisle and display in the user's preferred unit system (metric or imperial)
- **Persisted user preferences** — theme, unit system, and sort orders for both recipes and ingredients are stored locally and restored on every launch with no explicit save step required from the user
- **Read/write model separation** — a `convertRecipeToPayload` utility maps the API response shape to the write-focused payload shape in one place, keeping null-safety and nested object mapping out of the UI layer

## Tech Stack

- **Kotlin** — primary language
- **Jetpack Compose** — declarative UI
- **ViewModel + StateFlow** — reactive state management
- **Retrofit + Gson** — HTTP client and JSON deserialization
- **Android internal file storage** — lightweight local persistence (no Room/SQLite dependency)

## Getting Started

Open the project in Android Studio. The app targets the Cooklet Backend deployed at:

```
https://cooklet-backend.onrender.com/api/recipe/
```

To point it at a local backend instance, update `BASE_URL` in `endpointControllers.kt`.

Build and run on an emulator or physical device (Android 8.0+ recommended).
