Interviewer:
In a large Compose-based screen (for example, a chat or feed), you notice recomposition happening too often, causing performance issues.
How would you detect, analyze, and optimize unnecessary recompositions in Jetpack Compose?

Candidate:

It is possible to divide a large and complex screen into smaller portions to display the entire layout.
Divide the UI model into smaller models and update them according to the user’s requirements.
Use LaunchedEffect to identify changes — it helps reduce unnecessary recompositions at runtime.
The remember function also helps minimize recompositions by preserving state across recompositions.

✅ PART 1: Answer Evaluation – Recomposition in Jetpack Compose
🔹 Your Key Points:

“Divide large screen into small portions, divide UI model into smaller model, use LaunchedEffect, remember, etc.”

✅ What You Got Right:

✅ Breaking down large screens into smaller composables is best practice to localize recomposition.

✅ Using remember to cache expensive computations or objects is excellent for performance.

✅ Use of LaunchedEffect shows awareness of managing side effects and recomposition triggers.

⚠️ What Could Be Stronger:

You leaned heavily on LaunchedEffect — but it's not used to prevent recomposition, it's used for launching side effects when a key changes. That part was a bit off.

Here’s a senior-level framing of this answer 👇

✅ Sample Senior-Level Answer (Modeled Response)

“To optimize recomposition in Compose, I follow a few principles:

Break complex UI into small @Composables to isolate recomposition.

Use remember, derivedStateOf, and key() to avoid unnecessary recomposition due to changing inputs.

Make sure data passed to composables is immutable and stable. If a data class isn't marked as @Stable or @Immutable, even unchanged values may trigger recomposition.

Use tools like Compose Recomposition Highlighter, and Layout Inspector to identify recomposition hotspots during debugging.

For lists like feeds or chat, use LazyColumn with items(items, key = { it.id }) to prevent recomposing all items on update.

I also avoid capturing mutable state in lambdas or passing vars directly to @Composable functions.”

That kind of structured response will blow interviewers away — it's insightful, accurate, and shows deep Compose knowledge.