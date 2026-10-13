package com.back

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class WiseSayingFileRepositoryTest {
    private val wiseSayingRepository = SingletonScope.wiseSayingFileRepository

    companion object {

        @JvmStatic
        @BeforeAll
        fun setMode() {
            AppConfig.setModeToTest()
        }
    }

    @BeforeEach
    fun setUp() {
        wiseSayingRepository.clear()
    }

    @Test
    fun `save`() {
        val wiseSaying = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))

        val isExist = wiseSayingRepository
            .tableDirPath
            .toFile()
            .listFiles()
            ?.find { it.name == "${wiseSaying.id}.json" }

        assertThat(isExist)
    }

    @Test
    fun `findById`() {
        val wiseSaying = wiseSayingRepository
            .save(WiseSaying(content="나의 죽음을 적들에게 알리지 말라.", author="충무공 이순신"))

        val foundWiseSaying = wiseSayingRepository.findById(wiseSaying.id)

        assertThat(foundWiseSaying).isEqualTo(wiseSaying)
    }

    @Test
    fun `saveLastId, loadLastId`() {
        wiseSayingRepository.saveLastId(10)
        assertThat(wiseSayingRepository.loadLastId()).isEqualTo(10)
    }

    @Test
    fun `delete`() {
        val wiseSaying = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))

        wiseSayingRepository.delete(wiseSaying)

        assertThat(wiseSayingRepository.findById(wiseSaying.id)).isNull()
    }

    @Test
    fun `findAll`() {
        val wiseSaying1 = wiseSayingRepository
            .save(WiseSaying("나의 죽음을 적들에게 알리지 말라.", "충무공 이순신"))

        val wiseSaying2 = wiseSayingRepository
            .save(WiseSaying("나를 파괴할 수 있는 사람이 없다.", "바토르"))

        val foundWiseSayings = wiseSayingRepository.findListDesc()

        assertThat(foundWiseSayings).containsExactly(wiseSaying1, wiseSaying2)
    }

    @Test
    fun `목록(검색)`() {
        val result = TestRunner.run(
            """
            등록
            현재를 사랑하라.
            작자미상
            등록
            과거에 집착하지 마라.
            작자미상
            목록?keywordType=content&keyword=과거
        """
        )

        assertThat(result)
            .contains("----------------------")
            .contains("검색타입 : content")
            .contains("검색어 : 과거")

        assertThat(result)
            .doesNotContain("1 / 작자미상 / 현재를 사랑하라.")
            .contains("2 / 작자미상 / 과거에 집착하지 마라.")
    }
}