package org.study2.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder

class TorchService : Service() {
    /* TorchService 서비스가 Torch 클래스를 사용해야 합니다.Torch 클래스의 인스턴스를 얻는 방법에는
    onCreate() 콜백메서드를 사용하는 방법과 by layz {}를 사용하는 방법이 있습니다. onCreate()콜백메서드를
    사용하면 코드가 더 길어지기때문에 by lazy{}를 사용한 초기화 지연 방법을 샤용했습니다.
    이 방법을 사용하면 torch 객체를 처음 상용할 때 초기화됩니다.*/
    private val torch: Torch by lazy {
        Torch(this)
    }

//    프레쉬가 켜졌는지 꺼졌는지 알기위해 변수추가
    private var isRunning = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
/* 외부에서 startService() 메서드로 TorchService를 호출하면 onStartCommand() 메서드가 호출 됩니다
*  보통 인텐트에 action값을 설정하여 호출하는데 "on" 과 "off" 문자열을 액션으로 받았을때
*  when 문을 사용하여 가각 플래시를 켜고 끄는 동작을 하도록 코드를 작성했습니다.*/
        when(intent?.action) {
//            앱에서 실행할 경우
            "on" -> {
                torch.flashOn()
                isRunning = true
            }
            "off" -> {
                torch.flashOff()
                isRunning = false
            }
//            서비스에서 실행할경우
//            위젯에서는 서비스가 시작될 때는 액션값이 설정되지 않기 때문에 else 문이 실행됩니다.
//            여기서 isRunning값에 따라서 플래시를 켜거나 끄는 동작이 결정됩니다
            else -> {
                isRunning = !isRunning
                if (isRunning) {
                    torch.flashOn()
                } else {
                    torch.flashOff()
                }
            }
        }
/*  서비스는 메모리 부족 등의 이유로 시스템에 의해서 강제로 종료될 수 있습니다.
    onStartCommand() 메서드는 다음중 하나를 반환합니다. 이값에 따라 시스템이 강제로 종료한 후에
*   시스템 자원이 회복되어 다시 서비스를 시작할 수 있을때 어덯게 할지를 결정 합니다.
    START_STICKY : null 인텐트로 다시 시작합니다. 명령을 실행하지는 않지만 무기한으로 실행중이며 작없을 기다리고 있는 미디어 플레이어와 비슷한 경우에 적합합니다.
    START_NOT_STICKY : 다시 시작하지 않음
    START_REDELIVER_INTENT : 마지막 인텐트로 다시 시작함.능동적으로 수행중인 파일 다운로드와 같은 서비스에 적합합니다.
    일반적인 경우에는 super클래스의 onStartCommand() 메서드를 호출하면 내부적으로 START_STICKY를 반환합니다.
    여기서는 차이점을 느끼기 어려움*/
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

}