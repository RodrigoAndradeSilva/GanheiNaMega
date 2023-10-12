package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Aqui você decide o que o app vai fazer...
        setContentView(R.layout.activity_main)


        // buscar os objetos e ter a referência deles
        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        // banco de dados de preferências
        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        //Ou usar um valor de string padrão!
        // ou retornar null e não mostrar nada!

        // if -> let
        /*
        if (result != null) {
            txtResult.text = "Última aposta: $result"
        }
        */
        result?.let {
            txtResult.text = "Última aposta: $it"
        }






        // escutar eventos de touch

        // opção 1: XML

        // opção 2: Criar variável que seja do tipo (objeto anonimo) View.OnClickListener (interface)
        // btnGenerate.setOnClickListener(buttonClickListener)

        // opção 3: mais simples possível - bloco de código que será disparado pelo onClickListener
        btnGenerate.setOnClickListener {
            // aqui podemos colocar nossa lógica de programação. Porque será disparado depois do
            // evento de touch do usuário

            val text = editText.text.toString()

            numberGenerator(text, txtResult)
        }
    }

    private fun numberGenerator(text: String, txtResult: TextView) {
        // aqui é a falha numero 1
        if (text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val qtd = text.toInt() // converte string para inteiro

        // aqui é a falha numero 2
        if (qtd < 6 || qtd > 15) {
            // deu falha
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        // Aqui é o sucesso
        val numbers = mutableSetOf<Int>()
        val random = Random()

        while (true) {
            val number = random.nextInt(60) // 0...59
            numbers.add(number + 1)

            if (numbers.size == qtd) {
                break
            }
        }

        // 1 - 2 - 3 - 4 - 5 - 6
        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply() //assincrona

        /*  // alternativa 2
        prefs.edit().apply() {
            putString("result", txtResult.text.toString())
            apply()
        }
        */



        // commit -> Salvar de forma sincrona (bloquear a interface)
            //          informar se teve sucesso ou não
        // apply -> Salvar de forma assincrona (não vai bloquear a interface gráfica do usuário)
            //          não informa se teve sucesso ou não

    }


    // opção 2: Criar variável que seja do tipo (objeto anonimo) View.OnClickListener (interface)
//    val buttonClickListener = View.OnClickListener {
//        Log.i("Teste", "botao clicado!!!")
//    }

//    val buttonClickListener = object : View.OnClickListener {
//        //Quem chama o onClick é o próprio SDK do android que dispara após o evento de touch
//        override fun onClick(v: View?) {
//            Log.i("Teste", "botao clicado!!!")
//        }
//    }

//    // opção 1: XML
//    fun buttonClicked(view: View) {
//        Log.i("Teste", "botao clicado!!!")
//
//    }
}