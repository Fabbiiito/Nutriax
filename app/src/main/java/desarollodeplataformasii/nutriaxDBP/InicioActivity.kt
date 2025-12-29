package desarollodeplataformasii.nutriaxDBP

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.constraintlayout.compose.ConstraintLayout

import androidx.constraintlayout.compose.Dimension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape



class InicioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black // el fondo de toda la pantalla
            ) {
                //inicio() // tu función con todo el contenido
                //
                //RegisterFlow()
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
//todo esto es la parte de incio oi waaaa
/*
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
*/
//todo es para el la aprte d enew user pero con varia paginas waaaa
/*
@Composable

fun BaseLayout(
    top: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
    bottom: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {

        if (top != null) {
            Box(modifier = Modifier.padding(top = 24.dp)) {
                top()
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }

        if (bottom != null) {
            Box(modifier = Modifier.padding(bottom = 24.dp)) {
                bottom()
            }
        }
    }
}

/* =========================
   FLOW
   ========================= */

@Composable
fun RegisterFlow() {
    var step by rememberSaveable { mutableStateOf(0) }

    when (step) {

        0 -> BaseLayout(
            top = { Title("Bienvenido") },
            content = { PageOne() },
            bottom = { NextButton { step = 1 } }
        )

        1 -> BaseLayout(
            top = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BackButton { step-- }
                    Spacer(Modifier.width(8.dp))
                    Title("Cuéntanos de ti")
                }
            },
            content = {
                PageTwo(
                    onSelectGoal = { step = 2 }
                )
            }
        )

        2 -> BaseLayout(
            top = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BackButton { step-- }
                    Spacer(Modifier.width(8.dp))
                    Title("Final")
                }
            },
            content = { PageThree() }
        )
    }
}

/* =========================
   PAGES
   ========================= */

@Composable
fun PageOne() {
    Text(
        text = "¿Cuál es tu edad?",
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PageTwo(
    onSelectGoal: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Selecciona tu objetivo", color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSelectGoal) {
            Text("Bajar grasa")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onSelectGoal) {
            Text("Ganar músculo")
        }
    }
}

@Composable
fun PageThree() {
    Text(
        text = "Registro completo 🎉",
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

/* =========================
   COMPONENTS
   ========================= */

@Composable
fun NextButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF9800)
        )
    ) {
        Text("Continuar", color = Color.Black)
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    Text(
        text = "←",
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold
    )
}

/* =========================
   PREVIEW
   ========================= */

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        RegisterFlow()
    }
}
*/


@Preview(showBackground = true)
@Composable
fun SkeletonConstraintLayout2() {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(vertical = 40.dp)

    ) {
        //val startGuide = createGuidelineFromStart(0.5f)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f), // altura del row en f
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            // CAJA 1
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth(0.15f)
                    .background(Color.White)
                    .clickable { }
            )

            // CAJA 2
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .background(Color.Red)
            )
        }

        val imagenlogo = createRef()
        val topimagen = createGuidelineFromTop(0.1f)
        Box(
            modifier = Modifier
                .background(Color.White)
                .constrainAs(imagenlogo) {
                    top.linkTo(topimagen)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                    width = Dimension.percent(0.5f)
                    height = Dimension.percent(0.15f)
                }
        )
        val textimagen = createRef()
        val toptextimagen = createGuidelineFromTop(0.27f)
        val startGuide = createGuidelineFromStart(0.15f)
        val endGuide = createGuidelineFromEnd(0.15f)

        Box(
            modifier = Modifier
                .background(Color. White)
                .constrainAs(textimagen) {
                    top.linkTo(toptextimagen)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)

                    height = Dimension.value(60.dp)
                    width = Dimension.fillToConstraints
                }
        )

        val descripcion = createRef()
        val topdescripcion = createGuidelineFromTop(0.37f)
        Box(
            modifier = Modifier
                .background(Color.Red)
                .constrainAs(descripcion) {
                    top.linkTo(topdescripcion)
                    start.linkTo(startGuide)
                    end.linkTo(endGuide)

                    height = Dimension.value(90.dp)
                    width = Dimension.fillToConstraints
                }
        )
        val cajaboton = createRef()
        val topbotones = createGuidelineFromTop(0.5f)
        val statboton =createGuidelineFromStart(0.05f)
        val endboton =createGuidelineFromEnd(0.05f)
        val bottonboton =createGuidelineFromBottom(0.15f)
        Box(
            modifier = Modifier
                .background(Color.Red)
                .constrainAs(cajaboton) {
                    top.linkTo(topbotones)
                    bottom.linkTo(bottonboton)
                    start.linkTo(statboton)
                    end.linkTo(endboton)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )
        val continuar =createRef()
        val topcontinuar = createGuidelineFromTop(0.9f)
        val Bottoncontinuar = createGuidelineFromBottom(0f)
        Box(
            modifier = Modifier
                .background(Color.White)
                .constrainAs(continuar) {
                    top.linkTo(topcontinuar)
                    bottom.linkTo(Bottoncontinuar)
                    start.linkTo(statboton)
                    end.linkTo(endboton)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )


    }

}




