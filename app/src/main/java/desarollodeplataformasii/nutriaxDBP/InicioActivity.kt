package desarollodeplataformasii.nutriaxDBP

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import desarollodeplataformasii.nutriaxDBP.ui.theme.NutriaxDBPTheme
import androidx.compose.ui.unit.dp

class InicioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Llamamos a tu función de diseño
            InicioWasaaa()
        }
    }
}

@Composable
fun InicioWasaaa() {
    // Aquí es donde empieza tu magia de fondo negro y diseño duro
    androidx.compose.material3.Surface(
        modifier = Modifier.fillMaxSize(),
        color = androidx.compose.ui.graphics.Color.Black // Tu fondo negro
    ) {
        Greeting(name = "inicio wasaaaa")
    }
}

@Composable
fun Greeting(name: String) {
    androidx.compose.material3.Text(
        text = "esta en $name!",
        color = androidx.compose.ui.graphics.Color.White, // Letras blancas para que se vean en el negro
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InicioWasaaa()
}