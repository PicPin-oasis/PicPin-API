package com.picpin.api.photo.usecases

import com.picpin.api.photo.domains.root.Photo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate

class GetMyPhotoSectionsAssemblerTest {

    @Test
    fun assemble() {
        // Arrange
        val expectedFirstElementDate = LocalDate.of(2023, 10, 16)
        val expectedSecondElementDate = LocalDate.of(2023, 10, 29)
        val expectedThirdElementDate = LocalDate.of(2023, 11, 22)
        val photos = listOf(
            Photo.fixture(id = 1, takenPhotoDate = expectedFirstElementDate),
            Photo.fixture(id = 2, takenPhotoDate = expectedSecondElementDate),
            Photo.fixture(id = 3, takenPhotoDate = expectedThirdElementDate)
        )

        // Act
        val response = GetMyPhotoSectionsAssembler.assemble(photos)
        val postSections = response.postSections

        // Assert
        assertAll(
            { assertEquals(postSections.first().takenPhotoDate, expectedThirdElementDate) },
            { assertEquals(postSections.first().photoCards.first().id, 3) },
            { assertEquals(postSections[1].takenPhotoDate, expectedSecondElementDate) },
            { assertEquals(postSections[1].photoCards.first().id, 2) },
            { assertEquals(postSections.last().takenPhotoDate, expectedFirstElementDate) },
            { assertEquals(postSections.last().photoCards.first().id, 1) }
        )
    }
}
