package kmp.core.mobile.utils.extensions


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kmp.core.mobile.base.SIDE_EFFECTS_KEY
import kmp.core.mobile.base.ViewSideEffect
import kmp.core.mobile.presentation.theme.spacings
import kmp.core.mobile.resoources.IFileResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch



private var screenSize: Pair<Int, Int> = 0 to 0

fun getScreenSize(): Pair<Int, Int> {
    return screenSize
}

fun setScreenSize(width: Int, height: Int) {
    screenSize = width to height
}

@OptIn(ExperimentalMaterial3Api::class)
fun BottomSheetScaffoldState.trigger(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        if (bottomSheetState.isVisible) bottomSheetState.hide()
        else bottomSheetState.expand()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun BottomSheetScaffoldState.expand(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        bottomSheetState.expand()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun BottomSheetScaffoldState.collapse(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        bottomSheetState.expand()
    }
}

@Composable
fun Dp.toPx(): Float {
    return LocalDensity.current.run { this@toPx.toPx() }
}

@Composable
fun Int.toDp(): Dp {
    return LocalDensity.current.run { this@toDp.toDp() }
}

@Composable
fun Double.toDp(): Dp {
    return LocalDensity.current.run {
        (this@toDp / density).dp
    }
}

@Composable
fun Float.toDp(): Dp {
    return this.toDouble().toDp()
}

fun Modifier.noRippleClickable(
    enabled: Boolean = true,
    onClick: (() -> Unit)?
): Modifier = composed {
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick?.invoke()
    }
}

fun Modifier.noRippleLongClickable(
    enabled: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
): Modifier = composed {
    if (!enabled) return@composed this

    pointerInput(Unit) {
        detectTapGestures(
            onLongPress = {
                onLongClick?.invoke()
            },
            onTap = {
                onClick?.invoke()
            }
        )
    }
}


@Composable
fun Modifier.strokeBackground(
    shape: Shape,
    bgColor: Color,
    strokeColor: Color? = null,
    strokeWidth: Dp = spacings.noSpacing,
): Modifier {
    return this
        .background(
            shape = shape,
            color = bgColor
        )
        .borderIfNotNull(
            width = strokeWidth,
            color = strokeColor,
            shape = shape
        )
}

fun Modifier.topRoundClip(
    roundSize: Dp,
): Modifier {
    return this
        .clip(
            RoundedCornerShape(
                topStart = roundSize,
                topEnd = roundSize
            )
        )
}

fun Modifier.strokeSideRoundClip(
    strokeSide: StrokeSideRoundClip,
    bgColor: Color,
    roundSpacing: Dp,
): Modifier {

    val shape = when (strokeSide) {
        StrokeSideRoundClip.START ->
            RoundedCornerShape(
                topStart = roundSpacing,
                bottomStart = roundSpacing
            )


        StrokeSideRoundClip.TOP -> RoundedCornerShape(
            topStart = roundSpacing,
            topEnd = roundSpacing
        )

        StrokeSideRoundClip.END -> RoundedCornerShape(
            topEnd = roundSpacing,
            bottomEnd = roundSpacing
        )

        StrokeSideRoundClip.BOTTOM -> RoundedCornerShape(
            bottomStart = roundSpacing,
            bottomEnd = roundSpacing
        )
    }


    return this
        .background(
            shape = shape,
            color = bgColor
        )
        .clip(shape)
}

fun Modifier.swipeable(
    offsetX: Animatable<Float, AnimationVector1D>,
    swipeWidth: Float
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()

    pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                coroutineScope.launch {
                    // If swiped more than 40% of the width, complete the swipe
                    // Otherwise, return to original position
                    val targetValue = if (offsetX.value < -swipeWidth * 0.4f) {
                        -swipeWidth
                    } else {
                        0f
                    }

                    offsetX.animateTo(
                        targetValue = targetValue,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            },
            onDragCancel = {
                coroutineScope.launch {
                    // Return to original position when drag is canceled
                    offsetX.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            },
            onHorizontalDrag = { change, dragAmount ->
                coroutineScope.launch {
                    // Calculate new offset and constrain it between -swipeWidth and 0
                    val newValue = (offsetX.value + dragAmount).coerceIn(-swipeWidth, 0f)
                    offsetX.snapTo(newValue)
                }
            }
        )
    }
}

inline fun Modifier.suspendNoRippleClickable(
    enabled: Boolean = true,
    crossinline onClick: suspend CoroutineScope.() -> Unit
): Modifier = composed {
    val coroutineScope = rememberCoroutineScope()
    clickable(
        indication = null,
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        coroutineScope.launch { onClick.invoke(this) }
    }
}

fun Modifier.clipIfNotNull(shape: Shape?): Modifier {
    return if (shape != null) this.clip(shape) else this
}

fun Modifier.shadowIfNotNull(elevation: Dp, shape: Shape?): Modifier {
    return if (shape != null) this.shadow(elevation, shape) else this
}

fun tintIfNotNull(color: Color?): ColorFilter? {
    return if (color != null) ColorFilter.tint(color) else null
}

fun Modifier.borderIfNotNull(border: BorderStroke?, shape: Shape? = null): Modifier {
    return if (border != null) this.border(border, shape ?: RectangleShape) else this
}

fun Modifier.borderIfNotNull(color: Color?, width: Dp, shape: Shape): Modifier {
    return if (color != null) this.border(
        color = color,
        width = width,
        shape = shape
    )
    else this
}

fun Modifier.backgroundIfNotNull(color: Color?): Modifier {
    return if (color != null) this.background(color) else this
}

@Composable
fun animateAlignmentAsState(
    targetBiasValue: Float,
    label: String = "Alignment State Animation"
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetValue = targetBiasValue, label = label)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToNextPage() {
    try {
        animateScrollToPage(currentPage + 1)
    } catch (_: Throwable) {
    }
}

@OptIn(ExperimentalFoundationApi::class)
suspend fun PagerState.scrollToPreviousPage() {
    try {
        animateScrollToPage(currentPage - 1)
    } catch (_: Throwable) {
    }
}

fun Dp.half() = this.div(2)

fun Dp.negative() = this.times(-1)

@Composable
fun animateTextStyleAsState(
    targetValue: TextStyle,
    animationSpec: AnimationSpec<Float> = spring(),
    finishedListener: ((TextStyle) -> Unit)? = null
): State<TextStyle> {

    val animation = remember { Animatable(0f) }
    var previousTextStyle by remember { mutableStateOf(targetValue) }
    var nextTextStyle by remember { mutableStateOf(targetValue) }

    val textStyleState = remember(animation.value) {
        derivedStateOf {
            lerp(previousTextStyle, nextTextStyle, animation.value)
        }
    }

    LaunchedEffect(targetValue, animationSpec) {
        previousTextStyle = textStyleState.value
        nextTextStyle = targetValue
        animation.snapTo(0f)
        animation.animateTo(1f, animationSpec)
        finishedListener?.invoke(textStyleState.value)
    }

    return textStyleState
}

fun <T> LazyListScope.itemIfNotNull(data: T?, content: @Composable (T) -> Unit) {
    if (data != null) item {
        content(data)
    }
}

fun LazyListScope.itemIf(condition: Boolean, content: @Composable () -> Unit) {
    if (condition) item {
        content()
    }
}

@Composable
fun rememberCurrentOffset(state: LazyListState): State<Int> {
    val position = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.value = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.value += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.value -= (lastItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            currentOffset.value += itemOffset.value
        }
    }

    return currentOffset
}

@Composable
fun <T> rememberRef(): MutableState<T?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember() {
        object : MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}

@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

@Composable
fun Modifier.listItemStroke(
    backgroundColor: Color,
    strokeColor: Color,
    roundSpacing: Dp,
    strokeWidth: Dp,
    index: Int,
    lastIndex: Int
): Modifier {
    val shape = when (index) {
        0 -> if (0 == lastIndex) {
            RoundedCornerShape(roundSpacing)
        } else {
            RoundedCornerShape(
                topStart = roundSpacing,
                topEnd = roundSpacing
            )
        }

        lastIndex -> RoundedCornerShape(
            bottomStart = roundSpacing,
            bottomEnd = roundSpacing
        )

        else -> RoundedCornerShape(spacings.noSpacing)
    }

    // build modifier
    return remember {
        this
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                border = BorderStroke(
                    width = strokeWidth,
                    color = strokeColor
                ),
                shape = shape
            )
    }
}

fun Modifier.verticalScrollIf(scrollState: ScrollState, condition: () -> Boolean): Modifier {
    return if (condition()) this.verticalScroll(scrollState) else this
}

@Composable
fun Modifier.verticalScrollIf(
    condition: Boolean,
    state: ScrollState? = rememberScrollState()
): Modifier {
    return if (condition && state != null) this.verticalScroll(state) else this
}

@Composable
fun rememberScrollStateIf(condition: Boolean): ScrollState? {
    return if (condition) rememberScrollState() else null
}

@Composable
fun ScrollState.onScrolling(callback: () -> Unit) {
    LaunchedEffect(this) {
        snapshotFlow { isScrollInProgress }
            .collect {
                if (it) callback.invoke()
            }
    }
}
/*
@Composable
fun rememberPullRefreshStateIf(
    condition: Boolean,
    refreshing: Boolean,
    onRefresh: () -> Unit,
): PullRefreshState? {
    return if (condition) return rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    ) else {
        null
    }
}



@Composable
fun Modifier.pullRefreshIf(
    condition: Boolean,
    state: PullRefreshState?
): Modifier {
    return if (condition && state != null) this.pullRefresh(state) else this
}

@Composable
fun rememberLottieComposition(res: IFileResource): LottieCompositionResult {
    // Load json if required
    var json by remember(res) { mutableStateOf<String?>(null) }
    if (json == null) json = res.readAsState().value

    // Then return the composition
    return io.github.alexzhirkevich.compottie.rememberLottieComposition(
        LottieCompositionSpec.JsonString(
            json.orEmpty()
        )
    )
}*/

@Composable
fun Modifier.modifyIf(
    condition: Boolean,
    modify: @Composable Modifier.() -> Modifier,
): Modifier {
    return if (condition) modify() else this
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
fun FontFamily(vararg fonts: Font?): FontFamily? {
    val filteredFonts = fonts.filterNotNull()
    return if (filteredFonts.isEmpty()) null else FontFamily(filteredFonts)
}

@Composable
fun <T : ViewSideEffect> Flow<T>.consume(
    key: String = SIDE_EFFECTS_KEY,
    action: (effect: T) -> Unit
) {
    LaunchedEffect(key) { onEach(action).collect() }
}

@Composable
fun Modifier.flipIfRtl(): Modifier {
    val scaleX = if (LocalLayoutDirection.current == LayoutDirection.Ltr) 1f else -1f
    return scale(scaleX = scaleX, scaleY = 1f)
}

fun Modifier.topShadow(elevation: Dp) = drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val shadowHeight = -elevation.toPx()
        val gradientShader = LinearGradientShader(
            from = Offset(0f, shadowHeight),
            to = Offset.Zero,
            colors = listOf(Color.Transparent, DefaultShadowColor.copy(alpha = 0.08f)),
            tileMode = TileMode.Clamp,
        )
        paint.shader = gradientShader
        canvas.drawRect(
            left = size.width,
            top = shadowHeight,
            right = 0f,
            bottom = 0f,
            paint = paint,
        )
    }
}

fun Modifier.extendOutsideParentVertically(
    verticalExtension: Dp
) = this.layout { measurable, constraints ->
    val topExtensionPx = verticalExtension.roundToPx()
    val bottomExtensionPx = verticalExtension.roundToPx()

    val relaxedConstraints = constraints
        .copy(minWidth = 0, minHeight = 0)
        .offset(vertical = topExtensionPx + bottomExtensionPx)

    val placeable = measurable.measure(relaxedConstraints)
    layout(placeable.width, constraints.maxHeight) {
        placeable.placeRelative(0, -topExtensionPx)
    }
}

@Composable
expect fun openUrlInBrowser(url: String)

@Composable
expect fun getNotchHeight(): Dp

@Composable
expect fun getBottomHandleHeight(): Dp

@Composable
expect fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap?

@Composable
expect fun isGesturesNavBarEnabled(): Boolean

@Composable
expect fun setStatusBarColor(isDark: Boolean)

@Composable
expect fun setNavigationBarColor(isDark: Boolean)

@Composable
expect fun dismissKeyboard()