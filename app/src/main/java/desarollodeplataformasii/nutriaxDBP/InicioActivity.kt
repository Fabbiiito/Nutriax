package desarollodeplataformasii.nutriaxDBP

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import desarollodeplataformasii.nutriaxDBP.ui.theme.NutriaxDBPTheme
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout

import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.Dimension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Button
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.material3.Surface


class InicioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black // el fondo de toda la pantalla
            ) {
                inicio() // tu función con todo el contenido
            }
        }
    }
}
/*
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

 */
@Preview
@Composable
fun inicio (modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                vertical = 40.dp
            )
    )
    {

        MainImagen()
        textoinicio()
        newuser()
        login()

    }
}
@Composable
fun MainImagen()
{
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val cajaRoja = createRef()
        val topGuide = createGuidelineFromTop(0.05f)
        val endGuide = createGuidelineFromEnd(0f)

        Box(
            modifier = Modifier
                //.background(Color.Red)
                .constrainAs(cajaRoja) {
                    top.linkTo(topGuide)
                    end.linkTo(endGuide)

                    width = Dimension.percent(0.6f)
                    height = Dimension.percent(0.5f)
                }
        )
        {
            Image(
                painter = painterResource(id = R.drawable.inicio),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight,
                alignment = Alignment.CenterStart
            )

        }

    }

}
@Composable
fun textoinicio() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val caja = createRef()
        val topGuide = createGuidelineFromTop(0.57f)
        val statGuide = createGuidelineFromStart(0.05f)
        Box(
            modifier = Modifier
                //.background(Color.Red)
                .constrainAs(caja) {
                    top.linkTo(topGuide)
                    start.linkTo(statGuide)
                }
        ) {
            Column(

            ) {
                Text(
                    text = "COME MEJOR,",
                    color = Color.White,
                    fontSize = 35.sp, // tamaño grande
                    fontWeight = FontWeight.Bold // negrita
                )
                Text(
                    text = "OBTEN RESULTADOS",
                    color = Color.White,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
fun newuser()
{
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val newuser = createRef()
        val topGuide = createGuidelineFromTop(0.75f)
        val statGuide = createGuidelineFromStart(0f)
        Box(
            modifier = Modifier
                //.background(Color.Red)
                .constrainAs(newuser) {
                    top.linkTo(topGuide)
                    start.linkTo(statGuide)
                }
        ) {
            Button(
                onClick = { /* Acción al presionar */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)), // naranja
                shape = RoundedCornerShape(16.dp), // esquinas redondeadas
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // margen a los lados
            ) {
                Text(
                    text = "COMENZAR",
                    color = Color.Black ,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }

        }
    }
}
@Composable
fun login() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val newuser = createRef()
        val topGuide = createGuidelineFromTop(0.83f)
        val statGuide = createGuidelineFromStart(0.2f)

        Row(
                modifier = Modifier.padding(top = 16.dp)
                    .constrainAs(newuser) {
                    top.linkTo(topGuide)
                    start.linkTo(statGuide)
                } // margen superior
                ) {
            Text(
                text = "¿Ya tienes una cuenta?",
                color = Color.Gray
            )
            Text(
                text = " Iniciar sesión",
                color = Color(0xFFFF9800), // naranja
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        // acción al presionar iniciar sesión
                    },
                textDecoration = TextDecoration.Underline // opcional para que parezca link
            )
        }
    }

}

/*
fun Ejemplo2ConstraintGuide(){
    ConstraintLayout (modifier = Modifier.fillMaxSize()){

        val cajaRoja : ConstrainedLayoutReference = createRef()
        val topGuide : ConstraintLayoutBaseScope.HorizontalAnchor = createGuidelineFromTop( 0.1f)
        val startGuide : ConstraintLayoutBaseScope.VerticalAnchor = createGuidelineFromStart( 0.25f)
        Box(modifier = Modifier.size(125.dp).background(Color.Red).constrainAs(cajaRoja){
            top.linkTo(topGuide)
            start.linkTo(startGuide)
        })
    }
}


 */