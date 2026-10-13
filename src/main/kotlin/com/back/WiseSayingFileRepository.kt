package com.back

import java.nio.file.Path

class WiseSayingFileRepository : WiseSayingRepository {
    var lastId=0

    val tableDirPath: Path
        get() {
            return AppConfig.dbDirPath.resolve("wiseSaying")
        }

    override fun save(wiseSaying: WiseSaying): WiseSaying {
        return wiseSaying
            .takeIf { it.isNew() }
            .also {
                wiseSaying.id = genNextId()
                saveOnDisk(wiseSaying)
            } ?: wiseSaying
    }

    fun saveLastId(lastId: Int) {
        mkTableDirsIfNotExists()

        tableDirPath.resolve("lastId.txt")
            .toFile()
            .writeText(lastId.toString())
    }

    fun loadLastId(): Int {
        return kotlin.runCatching {
            tableDirPath.resolve("lastId.txt")
                .toFile()
                .readText()
                .toInt()
        }.getOrElse { 0 }

//        return try {
//            tableDirPath.resolve("lastId.txt")
//                .toFile()
//                .readText()
//                .toInt()
//        } catch (e: Exception) {
//            0
//        }
//
//        return tableDirPath.resolve("lastId.txt")
//                .toFile()
//                .readText()
//                .toIntOrNull() ?: 0
    }

    fun genNextId(): Int {
        return (loadLastId() + 1).also {
            saveLastId(it)
        }
    }

    private fun saveOnDisk(wiseSaying: WiseSaying) {
        mkTableDirsIfNotExists()

        val wiseSayingFile = tableDirPath.resolve("${wiseSaying.id}.json")
        wiseSayingFile.toFile().writeText(wiseSaying.jsonStr)
    }

    private fun mkTableDirsIfNotExists() {

        tableDirPath.toFile().run {
            if (!exists()) {
                mkdirs()
            }
        }
    }

    override fun findListDesc(): List<WiseSaying> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): WiseSaying? {

        //        val rst = tableDirPath
//            .toFile()
//            .listFiles()
//            ?.find { it.name == "${id}.json" }
//
//        if(rst != null) {
//            WiseSaying.fromJsonStr(rst.readText())
//        }

        return tableDirPath
            .toFile()
            .listFiles()
            ?.find { it.name == "${id}.json" }
            ?.let { WiseSaying.fromJsonStr(it.readText()) }
    }

    override fun delete(wiseSaying: WiseSaying): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }
}