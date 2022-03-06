package org.study2.flashlight

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
//앱 위젯용 파일응 AppWidgetProvider()라는 일종의 브로드캐스트 리시버 클래스를 상속받습니다.
class TorchAppWidget : AppWidgetProvider() {
//    onUpdate() 는 위젯이 업데이트 되어야 할 때 호출됩니다.
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
//        위젯이 여러개 배치되었다면 모든 위젯을 업데이트 합니다.
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
// 위젯이 처음 생성될 때 호출됩니다.
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }
//  위젯이 여러개일 경우 마지막 위젯이 제거될 떄 호출됩니다.
    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
//위젯을 업데이트할 때 수행되는 코드입니다.
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
//    위젯은 액티비티에서 레이아웃을 다루는 것과는 조금 다릅니다. 위젯에 배치하는 뷰는 따로있습니다.
//    그것들은 RemoteViews 객체로 가져올수 있습니다.
    val views = RemoteViews(context.packageName, R.layout.torch_app_widget)
//    setTextViewText() 메서드는 RemoteViews 객체용으로 준비된 텍스트 값을 변경하는 메서드입니다.
    views.setTextViewText(R.id.appwidget_text, widgetText)
//     여기서 위젯을 클릭했을 때의 처리를 추가해야 합니다
//    실행할 Intent를 작성
    val intent = Intent(context,TorchService::class.java)
  /* TorchService 서비스를 실행하는데 PendingIntent.getService() 메서드를 사용합니다.
  * 전달하는 인자는 컨텍스트,리퀘스트코드,서비스인텐트, 플래그 4개입니다
  * 리퀘스트코드는 사용하지않기 때문에 0을 전달합니다.
  * 플레그는 앱 위젯의 모양이 변하지 않아도 되면 FLAG_IMMUTABLE 을 설정합니다.*/
    val pendingIntent = PendingIntent.getService(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
//    클릭이벤트를 연결하려면 setOnClickPendingIntent() 메서드를 사용
//    여기에는 클릭이 발생할 뷰의 ID와 PendingIntent 객체가 필요합니다.
    views.setOnClickPendingIntent(R.id.appwidget_layout,pendingIntent)



    // Instruct the widget manager to update the widget
//    레이아웃을 모두 수정했다면 appWidgetManager 를 사용해 위젯을 없데이트 합니다.
    appWidgetManager.updateAppWidget(appWidgetId, views)
}