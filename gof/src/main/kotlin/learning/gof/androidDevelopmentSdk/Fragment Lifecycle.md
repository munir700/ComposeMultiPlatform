Explain the Fragment lifecycle and how it differs from Activity lifecycle.
Answer:
Fragments have additional lifecycle methods beyond the Activity lifecycle:
Fragment-Specific Callbacks (in order):

onAttach(): Fragment is attached to the host Activity
onCreate(): Fragment is being created
onCreateView(): Creates and returns the fragment's UI layout
onViewCreated(): Called after onCreateView() when the view hierarchy is created
onStart(): Fragment becomes visible
onResume(): Fragment becomes interactive
onPause(): Fragment is pausing
onStop(): Fragment is stopped
onDestroyView(): Fragment's view is destroyed, but the fragment exists
onDestroy(): Fragment is destroyed
onDetach(): Fragment is detached from the host Activity

Key Differences from Activity:

Fragments don't exist independently; they must be hosted in an Activity
onCreateView() is unique to Fragments
Fragments can be reused across multiple Activities
Fragment lifecycle is tied to its host Activity's lifecycle

What is the purpose of onCreateView() and onViewCreated()?
Answer:

onCreateView(): Inflates the fragment's layout XML and returns the root View. This is where you set up the UI. Cannot access views directly here until they're inflated.
onViewCreated(): Called immediately after onCreateView() returns. Use this method to access views that were just created and perform additional setup like setting listeners, initializing data.

Example Pattern:
