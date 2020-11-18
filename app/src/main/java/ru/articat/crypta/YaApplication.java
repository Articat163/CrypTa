package ru.articat.crypta;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;

public class YaApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// Инициализация AppMetrica SDK
		YandexMetrica.activate(getApplicationContext(), "b19f72c9-b1ee-4693-91de-16f8877208e1");
		// Отслеживание активности пользователей
		YandexMetrica.enableActivityAutoTracking(this);

		// trust all SSL -> HTTPS connection
//		SSLCertificateHandler.nuke();
	}
}
