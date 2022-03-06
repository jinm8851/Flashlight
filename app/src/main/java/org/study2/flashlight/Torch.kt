package org.study2.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager

//카메라 키고끄기위해 context 객체를 생성자로 받음
class Torch(context: Context) {
    private var cameraId: String? = null

    //    카메라메니져 객체생성 Object형을 반환하기 때문에 as 를 사용하여 CameraManager로 형변환
    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init {
//        카메라를 키고끌때 cameraId필요 카메라 id는 기기에 내장된 카메라마다 고유한 ID가 부여됩니다.
        cameraId = getCameraId()
    }

    fun flashOn() {
        cameraId?.let {
            cameraManager.setTorchMode(it, true)
        }
    }

    fun flashOff() {
        cameraId?.let {
            cameraManager.setTorchMode(it, false)
        }
    }

    private fun getCameraId(): String? {
//        카메라메니져는 기긱가 가지고 있는 모든 카메라에 대한 정보를 제공
        val cameraIds = cameraManager.cameraIdList
        for (id in cameraIds) {
//        아이디 별로 세부정보를 가지는 객체를 얻습니다.
            val info = cameraManager.getCameraCharacteristics(id)
//            플레쉬 가는여부와
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
//            렌즈 방향 여부
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING)
            if (flashAvailable != null
                && flashAvailable
                && lensFacing != null
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK
            ) {
//                    플레쉬가 사용 가능하고 카메라가 기기의 뒷면을 향하고 있는 카메라의 ID를 찾았다면 이 값을 반환
                return id
            }
        }
//        해당하는 값을 찾지 못했으면 null을 반환
        return null
    }
}