1. mutableStateOf(initialState) → UI State Holder

Used for UI state (S) in a Compose-friendly way.

It makes the state observable — whenever _viewState.value changes, Jetpack Compose recomposes automatically.

Example: if your screen has Loading → Content → Error, this mutableStateOf is perfect to track the current screen state.

👉 Think of it as: “What does the UI look like right now?”

2. MutableSharedFlow<E> → Events Stream

Used for one-time events (E), typically user actions or intents.

Examples: Button clicks, text input, "retry" actions.

Unlike State, events are not meant to be replayed automatically when the screen is recreated.

SharedFlow is a hot stream → multiple collectors can listen to it.

👉 Think of it as: “What the user just did or triggered.”

3. Channel<SF> → Side Effects

Used for single-shot effects (SF), like showing a Toast, Snackbar, Navigation, Dialog, etc.

These should not persist across recompositions or config changes (otherwise you'd see duplicate popups/snackbars).

Channel guarantees delivery once, and receiveAsFlow() converts it into a Flow for the UI layer to collect.

👉 Think of it as: “Do this one-time action, then forget about it.”

🚦 Why All Three Together? (MVI Pattern)

This setup is a common MVI (Model–View–Intent) pattern:

State (mutableStateOf) → Persistent, observable screen state.

Event (MutableSharedFlow) → User-driven inputs/intents.

Effect (Channel) → One-time UI commands (toast, navigation, etc.).

This separation keeps your UI predictable:

State drives UI.

Events trigger business logic.

Effects handle one-off UI actions.

✅ So basically:

mutableStateOf → Screen data

MutableSharedFlow → User actions

Channel → One-time UI effects