package com.makeathon.handyapp.models

class SettingsItem(val icon: Int, val title: String, val setting: Setting) {

}

enum class Setting {
    PROFILE, ABOUT, HELP
}
