package com.example.blog;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;

public class MainMenu {
    protected static PopupMenu createMainMenu(Context context, ImageButton imageButton, TextView textView) {
        final PopupMenu dropDownMenu = new PopupMenu(context, imageButton);
        final Menu menu = dropDownMenu.getMenu();
        dropDownMenu.getMenuInflater().inflate(R.menu.main_menu, menu);
        return dropDownMenu;
    }
}
