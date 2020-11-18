package ru.articat.crypta.Interfaces;
import java.util.ArrayList;

import ru.articat.crypta.Models.ThemeModel;
import ru.articat.crypta.Models.ThemeHeader;

public interface ThemeInterface {
		// you can define any parameter as per your requirement
		void themeDataCallback(ArrayList<ThemeModel> tema, ThemeHeader themeHeader, int pages);

}
