package uz.group1.maxwayapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun testMethodMaruf() {

    }
}


/*
1)  O'zingizni branchingiz commit
2)  o'zingizni branchingizda update project qilasiz (bu bitta branchda o'zingiz ishlasangiz optional) [merge chiqsa fix qilasiz]
3)  push qilasiz
4) Master/main checkout
4) Master/main  asosiy branchga o'tib [UNDA KOD YOZMAYSIZ] faqat update project qilasiz
5) Ozingizni branch checkout
6) Merge main/master into "ozingizni branch" agar conflick chiqsa hal qilasiz
7)  ozingizni branchga push qilasiz
8) Githubga kirib pull reqeust yaratasiz
9) Trello zadacha statae update qilib pull request link qo';shasiz. pull reqeust haqida guruhda ham ogohlantirib qo'yasiz
* */