package desarollodeplataformasii.nutriaxDBP

import android.Manifest
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Calendar
import java.util.Locale

// =========================================================================
// CONSTANTES Y ENUMS
// =========================================================================

private const val GEMINI_API_KEY = "clave api"

enum class Screen {
    CALENDAR, VISION
}

// =========================================================================
// MODELOS DE DATOS (DEL CÓDIGO 1)
// =========================================================================

data class DateItem(
    val dayOfMonth: Int,
    val dayOfWeek: String,
    val month: Int,
    val year: Int
)

// =========================================================================
// FUNCIÓN DE AYUDA (DEL CÓDIGO 1)
// =========================================================================

private fun getDayName(day: Int): String {
    return when(day) {
        Calendar.MONDAY -> "L"
        Calendar.TUESDAY -> "M"
        Calendar.WEDNESDAY -> "M"
        Calendar.THURSDAY -> "J"
        Calendar.FRIDAY -> "V"
        Calendar.SATURDAY -> "S"
        Calendar.SUNDAY -> "D"
        else -> ""
    }
}

private fun getMonthName(month: Int): String {
    return when (month) {
        Calendar.JANUARY -> "Enero"
        Calendar.FEBRUARY -> "Feb"
        Calendar.MARCH -> "Mar"
        Calendar.APRIL -> "Abr"
        Calendar.MAY -> "May"
        Calendar.JUNE -> "Jun"
        Calendar.JULY -> "Jul"
        Calendar.AUGUST -> "Ago"
        Calendar.SEPTEMBER -> "Sep"
        Calendar.OCTOBER -> "Oct"
        Calendar.NOVEMBER -> "Nov"
        Calendar.DECEMBER -> "Dic"
        else -> ""
    }
}

private fun generateSampleDates(startCalendar: Calendar): List<DateItem> {
    val dates = mutableListOf<DateItem>()
    val calendar = startCalendar.clone() as Calendar

    // 1. Encuentra el Lunes de la semana de hoy
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

    // 2. Retrocede 14 días (2 semanas completas)
    calendar.add(Calendar.DAY_OF_MONTH, -14)

    // 3. Generar 42 días (6 semanas)
    for (i in 0..41) {
        dates.add(
            DateItem(
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH),
                dayOfWeek = getDayName(calendar.get(Calendar.DAY_OF_WEEK)),
                month = calendar.get(Calendar.MONTH),
                year = calendar.get(Calendar.YEAR)
            )
        )
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    }
    return dates
}

private fun findTodayPosition(dates: List<DateItem>): Int {
    val today = Calendar.getInstance()
    val day = today.get(Calendar.DAY_OF_MONTH)
    val month = today.get(Calendar.MONTH)
    val year = today.get(Calendar.YEAR)

    return dates.indexOfFirst { it.dayOfMonth == day && it.month == month && it.year == year }
}

// =========================================================================
// VISTA CALENDARIO (ADAPTADA A COMPOSE DEL CÓDIGO 1)
// =========================================================================

@Composable
fun DateItemView(dateItem: DateItem, isSelected: Boolean, onDateClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .width(40.dp)
            .clickable(onClick = onDateClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dateItem.dayOfWeek,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dateItem.dayOfMonth.toString(),
                color = if (isSelected) Color.White else Color.Black,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val todayCalendar = remember { Calendar.getInstance() }
    val allDates = remember { generateSampleDates(todayCalendar) }

    var selectedDateIndex by remember { mutableIntStateOf(findTodayPosition(allDates)) }
    val initialPosition = remember { findTodayPosition(allDates) }
    val listState = rememberLazyListState()

    // Encabezado para mostrar la fecha seleccionada
    val selectedDateItem = if (selectedDateIndex != -1) allDates[selectedDateIndex] else allDates.first()
    val headerDateText = remember(selectedDateItem) {
        "${getMonthName(selectedDateItem.month)} ${selectedDateItem.dayOfMonth}, ${selectedDateItem.year}"
    }

    // Centrar la vista en la semana actual al inicio
    LaunchedEffect(Unit) {
        if (initialPosition != -1) {
            val weekStart = initialPosition - (initialPosition % 7)
            listState.scrollToItem(weekStart)
        }
    }

    // Función para abrir el DatePicker de Android
    fun openDatePicker(context: Context) {
        val anio = todayCalendar.get(Calendar.YEAR)
        val mes = todayCalendar.get(Calendar.MONTH)
        val dia = todayCalendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, day)

                // Actualizar la fecha seleccionada en el header
                val newDateItem = DateItem(day, getDayName(selectedCalendar.get(Calendar.DAY_OF_WEEK)), month, year)
                val newIndex = allDates.indexOfFirst { it == newDateItem }
                if (newIndex != -1) {
                    selectedDateIndex = newIndex
                    // Intentar centrar en la fecha seleccionada
                    val weekStart = newIndex - (newIndex % 7)
                    if (weekStart != -1) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(weekStart)
                        }
                    }
                } else {
                    // Si la fecha seleccionada está fuera del rango de 6 semanas
                    // En un caso real, regenerarías las fechas
                }
            },
            anio, mes, dia
        )
        datePickerDialog.show()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Encabezado (equivalente a tv_selected_date y el clic del header)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { openDatePicker(context) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = headerDateText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(8.dp))
// Código nuevo (Placeholder simple)
            Text("🔍", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        // RecyclerView Horizontal (convertido a LazyRow)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = listState,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            itemsIndexed(allDates) { index, dateItem ->
                DateItemView(
                    dateItem = dateItem,
                    isSelected = index == selectedDateIndex,
                    onDateClick = {
                        selectedDateIndex = index
                    }
                )
            }
        }

        // Contenido de la pantalla de Calendario (aún por implementar en la original, solo un placeholder)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Contenido de la agenda/comidas del día: ${selectedDateItem.dayOfMonth}", fontSize = 18.sp)
        }
    }
}

// =========================================================================
// FUNCIÓN DE AYUDA (DEL CÓDIGO 2)
// =========================================================================

/**
 * Función que crea una URI de archivo temporal para la cámara.
 */
fun createImageUri(context: Context): Uri {
    val tempDir = File(context.cacheDir, "images")
    tempDir.mkdirs()
    val file = File(tempDir, "temp_image.jpg")

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )
}

/**
 * Función que convierte la URI a un Bitmap.
 */
fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use {
            BitmapFactory.decodeStream(it)
        }
    } catch (e: Exception) {
        Log.e("VisionScreen", "Error loading bitmap from URI: ${e.message}")
        null
    }
}

// =========================================================================
// VIEW MODEL (DEL CÓDIGO 2)
// =========================================================================

// Se define un ViewModel simple para mantener el estado (necesario en el código original)
class VisionViewModel : ViewModel() {
    // Aquí iría la lógica de negocio si fuera más compleja, por ahora se deja vacío.
}

// =========================================================================
// VISTA ANÁLISIS NUTRICIONAL (DEL CÓDIGO 2)
// =========================================================================

@Composable
fun VisionScreen(speak: (String) -> Unit, viewModel: VisionViewModel = viewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Estados locales
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var resultText by remember { mutableStateOf("Analiza Nutricional con Gemini") }
    var isLoading by remember { mutableStateOf(false) }

    // Estado para la URI temporal de la cámara
    val cameraUri = remember { mutableStateOf<Uri?>(null) }

    // Función central para actualizar estados después de seleccionar/tomar una imagen
    fun handleNewImageUri(uri: Uri?) {
        if (uri != null) {
            imageUri = uri
            imageBitmap = uriToBitmap(context, uri)
            resultText = "Imagen seleccionada. Presiona Analizar Comida."
        }
    }

    // 1. Lanzador de la Galería
    val pickMedia = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        handleNewImageUri(uri)
    }

    // 2. Lanzador de la Cámara
    val takePicture = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            handleNewImageUri(cameraUri.value)
        }
    }

    // 3. Lanzador de Permisos (para Android 13+)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            cameraUri.value = createImageUri(context)
            cameraUri.value?.let { takePicture.launch(it) }
        } else {
            resultText = "Permiso de cámara denegado."
        }
    }

    // Función de análisis de imagen
    fun analyzeImage() = coroutineScope.launch {
        val bitmap = imageBitmap
        if (bitmap == null) {
            resultText = "Por favor, toma o selecciona una foto primero."
            return@launch
        }

        isLoading = true
        resultText = "Analizando... Esto puede tardar unos segundos."

        try {
            val model = GenerativeModel(
                modelName = "gemini-2.5-flash",
                apiKey = GEMINI_API_KEY
            )

            val prompt = """
                Analiza detalladamente esta imagen de comida. 
                Identifica el alimento principal y estima la cantidad en gramos o unidades. 
                Responde ÚNICAMENTE en este formato JSON, sin texto adicional antes o después:
                {
                    "alimento_identificado": "nombre_del_alimento",
                    "estimacion_gramos": "numero_estimado",
                    "macros_estimados": {
                        "calorias_kcal": "numero_entero",
                        "proteinas_g": "numero_decimal",
                        "carbohidratos_g": "numero_decimal",
                        "grasas_g": "numero_decimal"
                    }
                }
                Si no puedes identificar el alimento, usa "Desconocido".
            """.trimIndent()

            val inputContent = content {
                image(bitmap)
                text(prompt)
            }

            val response = withContext(Dispatchers.IO) {
                model.generateContent(inputContent)
            }

            val jsonResponse = response.text ?: "Respuesta de la IA vacía."
            resultText = "Resultado de la IA (JSON):\n$jsonResponse"

            val speechText = if (jsonResponse.length > 50)
                "Análisis completado. Resultados listos para ser guardados."
            else
                "Error en la respuesta de la IA."

            speak(speechText)

        } catch (e: Exception) {
            resultText = "Error de la API o al procesar: ${e.message}"
            Log.e("GeminiVision", "Error: ${e.message}", e)
            speak("Error al analizar la imagen.")
        } finally {
            isLoading = false
        }
    }

    // **********************************
    // * INTERFAZ DE USUARIO (COMPOSE) *
    // **********************************
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Análisis Nutricional con Gemini", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Previsualización de la Imagen
        imageBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Imagen de comida seleccionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        } ?: Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(8.dp)
                .background(Color.LightGray)
        ) {
            Text("No hay imagen seleccionada", Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de Acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Botón de Galería
            Button(
                onClick = {
                    pickMedia.launch(
                        androidx.activity.result.PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                enabled = !isLoading
            ) {
                Text("Seleccionar Foto")
            }

            // Botón de Cámara
            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    } else {
                        cameraUri.value = createImageUri(context)
                        cameraUri.value?.let { takePicture.launch(it) }
                    }
                },
                enabled = !isLoading
            ) {
                Text("Tomar Foto")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Análisis
        Button(
            onClick = { analyzeImage() },
            enabled = !isLoading && imageBitmap != null
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Analizando...")
            } else {
                Text("Analizar Comida con Gemini")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Resultado del Análisis
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = resultText,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// =========================================================================
// ESTRUCTURA PRINCIPAL (ACTIVIDAD)
// =========================================================================

class MainActivity : ComponentActivity() {

    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización del motor Text-to-Speech (DEL CÓDIGO 2)
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale("es", "ES"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                }
            } else {
                Log.e("TTS", "Initialization Failed!")
            }
        }

        setContent {
            MaterialTheme {
                MainAppScreen(::speak)
            }
        }
    }

    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }

    private fun speak(text: String) {
        if (::tts.isInitialized) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID)
        }
    }
}

// =========================================================================
// BARRA DE NAVEGACIÓN Y PANTALLA PRINCIPAL (FUSIÓN)
// =========================================================================

@Composable
fun MainAppScreen(speak: (String) -> Unit) {
    var selectedScreen by remember { mutableStateOf(Screen.CALENDAR) }

    Scaffold(
        topBar = { AppTopBar(selectedScreen) },
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen,
                onScreenSelected = { selectedScreen = it }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                Screen.CALENDAR -> CalendarScreen() // Contenido del Código 1
                Screen.VISION -> VisionScreen(speak)   // Contenido del Código 2
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(currentScreen: Screen) {
    TopAppBar(
        title = {
            Text(
                text = when (currentScreen) {
                    Screen.CALENDAR -> "📅 Mi Agenda Nutricional"
                    Screen.VISION -> "📷 Análisis de Comida"
                },
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

// AÑADE ESTA FUNCIÓN (Reemplaza NavItem)
@Composable
fun BottomNavTextButton(
    label: String,
    screen: Screen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer

    Button(
        onClick = onClick,
        modifier = Modifier.height(48.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else Color.Transparent,
            contentColor = color
        ),
        elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
        )
    }
}

// MODIFICA ESTA FUNCIÓN
@Composable
fun BottomNavigationBar(selectedScreen: Screen, onScreenSelected: (Screen) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavTextButton(
            label = "Calendario",
            screen = Screen.CALENDAR,
            isSelected = selectedScreen == Screen.CALENDAR,
            onClick = { onScreenSelected(Screen.CALENDAR) }
        )
        BottomNavTextButton(
            label = "Comida",
            screen = Screen.VISION,
            isSelected = selectedScreen == Screen.VISION,
            onClick = { onScreenSelected(Screen.VISION) }
        )
    }
}

@Composable
fun NavItem(
    icon: ImageVector,
    label: String,
    screen: Screen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = color,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
