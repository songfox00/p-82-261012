package com.back

class WiseSayingController {

    var lastId = 0
    val wiseSayings = mutableListOf<WiseSaying>()

    fun write() {
        print("명언: ")
        val content = readln().trim()
        print("작가: ")
        val author = readln().trim()

        val id=++lastId

        WiseSaying(id,content,author).also{
            wiseSayings.add(it)
        }

        println("${id}번 명언이 등록되었습니다")
    }

    fun list()  {
        println("번호 / 명언 / 작가")
        println("----------------------")
        wiseSayings.reversed().forEach{
            println("${it.id} / ${it.content} / ${it.author}")
        }
    }

    fun delete(rq: Rq) {
        val id = rq.getParamAsInt("id", 0)

        if(id==0){
            println("id를 정확히 입력해주세요.")
            return
        }

        val wiseSaying=wiseSayings.firstOrNull{
            it.id == id
        }

        if (wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        wiseSayings.remove(wiseSaying)
        println("${id}번 명언이 삭제되었습니다.")
    }

    fun modify(rq: Rq) {

        val id = rq.getParamAsInt("id", 0)

        if (id == 0) {
            println("id를 정확히 입력해주세요.")
            return
        }

        val wiseSaying = wiseSayings.firstOrNull {
            it.id == id
        }

        if (wiseSaying == null) {
            println("${id}번 명언은 존재하지 않습니다.")
            return
        }

        println("명언(기존) : ${wiseSaying.content}): ")
        print("명언 : ")
        val newContent = readln().trim()
        println("작가(기존: ${wiseSaying.author}): ")
        print("작가 : ")
        val newAuthor = readln().trim()

        wiseSaying.modify(newContent, newAuthor)
        println("${id}번 명언이 수정되었습니다.")
    }
}