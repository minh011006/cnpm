package com.example.doancoso3.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class StaticGrammar(
    val title: String,
    val formula: String,
    val example: String
)


@Composable
fun GrammarScreen() {

    val grammarList = listOf(

        StaticGrammar(
            title = "1. Thì Hiện tại đơn (Present Simple)",
            formula = "(+) S + V(s/es) + O\n(-) S + do/does + not + V(nguyên thể)\n(?) Do/Does + S + V(nguyên thể)?",
            example = "I play football every weekend.\nShe doesn't like apples."
        ),
        StaticGrammar(
            title = "2. Thì Hiện tại tiếp diễn (Present Continuous)",
            formula = "(+) S + am/is/are + V-ing\n(-) S + am/is/are + not + V-ing\n(?) Am/Is/Are + S + V-ing?",
            example = "I am studying Kotlin right now.\nThey aren't playing outside."
        ),
        StaticGrammar(
            title = "3. Thì Hiện tại hoàn thành (Present Perfect)",
            formula = "(+) S + have/has + V3/ed + O\n(-) S + have/has + not + V3/ed\n(?) Have/Has + S + V3/ed?",
            example = "I have lived here for 5 years.\nShe has just finished her homework."
        ),
        StaticGrammar(
            title = "4. Thì Quá khứ đơn (Past Simple)",
            formula = "(+) S + V2/ed + O\n(-) S + did + not + V(nguyên thể)\n(?) Did + S + V(nguyên thể)?",
            example = "We visited Paris last year.\nHe didn't go to school yesterday."
        ),
        StaticGrammar(
            title = "5. Thì Quá khứ tiếp diễn (Past Continuous)",
            formula = "(+) S + was/were + V-ing\n(-) S + was/were + not + V-ing\n(?) Was/Were + S + V-ing?",
            example = "I was watching TV at 8 PM yesterday.\nThey were playing football when it rained."
        ),
        StaticGrammar(
            title = "6. Thì Tương lai đơn (Future Simple)",
            formula = "(+) S + will + V(nguyên thể)\n(-) S + will + not (won't) + V(nguyên thể)\n(?) Will + S + V(nguyên thể)?",
            example = "I will help you with your homework.\nIt won't rain tomorrow."
        ),


        StaticGrammar(
            title = "7. Câu điều kiện Loại 1 (Có thật ở hiện tại/tương lai)",
            formula = "If + S + V(hiện tại đơn), S + will/can/may + V(nguyên thể)",
            example = "If it rains, we will stay at home.\nIf you study hard, you will pass the exam."
        ),
        StaticGrammar(
            title = "8. Câu điều kiện Loại 2 (Không có thật ở hiện tại)",
            formula = "If + S + V2/ed (To be: were), S + would/could + V(nguyên thể)",
            example = "If I were you, I would not do that.\nIf I had a million dollars, I would buy a yacht."
        ),
        StaticGrammar(
            title = "9. Câu điều kiện Loại 3 (Không có thật ở quá khứ)",
            formula = "If + S + had + V3/ed, S + would/could + have + V3/ed",
            example = "If I had known the truth, I would have told you.\nIf she had studied harder, she wouldn't have failed."
        ),


        StaticGrammar(
            title = "10. Câu Bị động (Passive Voice)",
            formula = "Chủ động: S + V + O\nBị động: S(mới) + be + V3/ed + (by O_cũ)\n*Lưu ý: 'be' chia theo thì của động từ chính.",
            example = "Active: The dog bit the boy.\nPassive: The boy was bitten by the dog."
        ),
        StaticGrammar(
            title = "11. Mệnh đề quan hệ (Relative Clauses)",
            formula = "Who / Whom: Thay thế cho người\nWhich: Thay thế cho vật\nThat: Thay thế cho cả người và vật\nWhose: Chỉ sự sở hữu",
            example = "The man who is standing there is my father.\nThe book which I bought yesterday is interesting."
        ),
        StaticGrammar(
            title = "12. Câu so sánh hơn và hơn nhất (Comparisons)",
            formula = "- So sánh hơn (Tính từ ngắn): S + be + adj+er + than + N/Pronoun\n- So sánh hơn (Tính từ dài): S + be + more + adj + than + N/Pronoun\n- So sánh nhất: S + be + the + adj+est / most + adj",
            example = "She is taller than me. (Ngắn)\nThis book is more interesting than that one. (Dài)\nHe is the smartest student in the class. (Nhất)"
        )
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Sổ tay Ngữ pháp",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(grammarList) { grammar ->
                StaticGrammarCard(grammar)
            }
        }
    }
}


@Composable
fun StaticGrammarCard(grammar: StaticGrammar) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = grammar.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE65100))
            Spacer(modifier = Modifier.height(12.dp))


            Surface(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFF1976D2), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Công thức", fontWeight = FontWeight.Bold, color = Color(0xFF1976D2))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = grammar.formula, fontSize = 16.sp, lineHeight = 24.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))


            Surface(
                color = Color(0xFFFFF9C4),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFF57F17), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Ví dụ:", fontWeight = FontWeight.Bold, color = Color(0xFFF57F17))
                        Text(text = grammar.example, fontSize = 15.sp, fontStyle = FontStyle.Italic)
                    }
                }
            }
        }
    }
}