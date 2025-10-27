package learning.gof.algorithmicSkills

// TODO How would you optimize a large list rendering in Compose or RecyclerView?
//A:
//Use LazyColumn / RecyclerView with diffing (LazyListState or ListAdapter).
//Paginate results to avoid loading everything.
//Use rememberLazyListState() for scroll state.
//Reuse item composables using keys.
//Avoid unnecessary recompositions (prefer derivedStateOf, remember, etc.).

class OptimizeLargeListRendering {
}