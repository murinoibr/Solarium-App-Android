package com.example

import com.example.data.repository.HouseRepository
import com.example.ui.viewmodel.HouseViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class HouseDataTest {

    @Test
    fun verifyAllTenSectionsExist() {
        val sections = HouseRepository.sections
        assertEquals(10, sections.size)
        // Check section IDs 1 through 10 in order
        for (i in 1..10) {
            val section = sections.find { it.id == i }
            assertNotNull("Section $i must exist", section)
            assertTrue("Section $i must have items", section!!.items.isNotEmpty())
        }
    }

    @Test
    fun verifyWifiAndAddressDetails() {
        assertEquals("ValeriaMR", HouseRepository.WIFI_SSID)
        assertEquals("pazeamor", HouseRepository.WIFI_PASSWORD)
        assertTrue(HouseRepository.HOUSE_ADDRESS.contains("Castelo Branco"))
    }

    @Test
    fun verifyInstantAnswers() {
        val wifiAnswer = HouseRepository.getInstantAnswer("qual a senha do wifi?")
        assertNotNull(wifiAnswer)
        assertTrue(wifiAnswer!!.contains("pazeamor"))

        val tvAnswer = HouseRepository.getInstantAnswer("como ligar a tv?")
        assertNotNull(tvAnswer)
        assertTrue(tvAnswer!!.contains("HDMI"))
    }

    @Test
    fun verifyViewModelTabNavigation() {
        val vm = HouseViewModel()
        assertEquals(0, vm.uiState.value.currentTab)
        vm.selectTab(1)
        assertEquals(1, vm.uiState.value.currentTab)
        vm.selectTab(2)
        assertEquals(2, vm.uiState.value.currentTab)
        vm.selectTab(3)
        assertEquals(3, vm.uiState.value.currentTab)
        vm.selectTab(4)
        assertEquals(4, vm.uiState.value.currentTab)
    }

    @Test
    fun verifyKeyFloorPlanPointsExist() {
        val points = HouseRepository.floorPlanPoints
        val entrada = points.find { it.id == "entrada_principal" }
        assertNotNull("Ponto de entrada deve existir", entrada)
        assertTrue(entrada!!.description.contains("puxe"))

        val garagem = points.find { it.id == "garagem" }
        assertNotNull("Ponto de garagem deve existir", garagem)
        assertTrue(garagem!!.description.contains("controle"))

        val patio = points.find { it.id == "patio" }
        assertNotNull("Ponto de pátio deve existir", patio)
        assertTrue(patio!!.description.contains("espreguiçadeiras"))

        val cozinha = points.find { it.id == "cozinha" }
        assertNotNull("Ponto de cozinha deve existir", cozinha)
        assertTrue(cozinha!!.description.contains("Air Fryer") || cozinha.tipOrWarning?.contains("Air Fryer") == true)
    }

    @Test
    fun verifyViewModelSectionSelection() {
        val vm = HouseViewModel()
        vm.openSectionById(3)
        assertNotNull(vm.uiState.value.selectedSection)
        assertEquals(3, vm.uiState.value.selectedSection?.id)
        vm.closeSection()
        assertEquals(null, vm.uiState.value.selectedSection)
    }

    @Test
    fun verifyRoomsAndPhotosOrganizedByRoom() {
        val rooms = HouseRepository.houseRooms
        assertTrue("Deve haver cômodos configurados", rooms.isNotEmpty())
        
        var totalPhotos = 0
        rooms.forEach { room ->
            assertTrue("Cômodo ${room.id} deve ter fotos", room.allPhotos.isNotEmpty())
            assertTrue("Cômodo ${room.id} deve ter título", room.title.isNotBlank())
            assertTrue("Cômodo ${room.id} deve ter descrição", room.description.isNotBlank())
            
            room.allPhotos.forEach { photo ->
                totalPhotos++
                assertTrue("Foto deve ter filename", photo.filename.isNotBlank())
                assertTrue("Foto deve ter legenda", photo.caption.isNotBlank())
                assertTrue("Asset URL deve apontar para house_photos", photo.assetUrl.startsWith("file:///android_asset/house_photos/"))
            }
        }
        assertTrue("Deve conter pelo menos 31 fotos distribuídas pelos cômodos, encontrado: $totalPhotos", totalPhotos >= 31)
    }
}
