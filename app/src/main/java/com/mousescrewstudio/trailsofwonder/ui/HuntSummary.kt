package com.mousescrewstudio.trailsofwonder.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.mousescrewstudio.trailsofwonder.ui.database.Hunt



@Composable
fun HuntSummary(
    huntID: String
) {

    var chasseTest = Hunt(huntID, huntID, 1, 1, 1, listOf<String>("1"), listOf<String>("Commentaire1", "Commentaire2"))

    var i = 0

    GetChasse (
        huntID = huntID,
        onSuccess = { hunts ->
            println(hunts)
            chasseTest = hunts
            i = 1
        }
    )


    if( i == 1 ) Afficher(hunt = chasseTest)

}

@Composable
fun GetChasse(huntID : String, onSuccess: (Hunt) -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    val huntCollection = firestore.collection("huntsList")

    huntCollection.get()
        .addOnSuccessListener {
            it.toObjects(Hunt::class.java).forEach {
                if (it.huntName == huntID) {
                    println("Bon nom")
                    onSuccess(it)
                } else {
                    println("Bad nom")
                }
            }
        }
        .addOnFailureListener { exception ->
            println("Erreur Chasse")
        }
}

@Composable
fun Afficher(hunt: Hunt) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Veuillez vous préparer pour la chasse ${hunt.huntName}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = hunt.location,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = ("${hunt.durationHours}:${hunt.durationMinutes}"),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text("Créer une équipe")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(BorderStroke(2.dp, androidx.compose.ui.graphics.Color.Green)))

        {
            Column(modifier = Modifier
                .padding(16.dp)
            )  {
                Text(
                    text = "Commentaires",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .wrapContentHeight()
                )

                Spacer(modifier = Modifier.height(16.dp))
                hunt.comment.forEach {
                    Text(text = it, textAlign = TextAlign.Center,)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
}


@Preview
@Composable
fun PreviewSummary() {
    HuntSummary("Coucou")
}