package com.back

class App {
    fun run() {
        var lastId=0

        println("== 명언 앱 ==")

        while(true){
            print("명언) ")
            val input = readln().trim()

            when(input) {
                "종료" -> break

                "등록" -> {
                    print("명언: ")
                    val content = readln().trim()

                    print("작가: ")
                    val author = readln().trim()

                    val id=++lastId
                    println("${id}번 명언이 등록되었습니다")
                }
            }
        }
    }
}