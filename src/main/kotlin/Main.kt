package com.back

fun main() {
    println("== 명언 앱 ==")

    while (true) {
        print("명언) ")
        val input = readln().trim()

        if(input == "종료") {
            break
        }
    }
}