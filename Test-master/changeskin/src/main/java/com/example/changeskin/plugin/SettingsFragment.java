package com.example.changeskin.plugin;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import androidx.annotation.Nullable;

import com.example.changeskin.R;

import skin.support.SkinCompatManager;

public class SettingsFragment extends PreferenceFragment {
    public static final String BUILD_IN_NIGHT_MODE_KEY = "BuildInNightMode";
    public static final String ASSETS_NIGHT_MODE_KEY = "AssetsNightMode";
    public static final String SDCARD_NIGHT_MODE_KEY = "SDCardNightMode";
    /**
     * 应用内换肤
     */
    private SwitchPreference mBuildInNightModePreference;
    /**
     * 插件式换肤
     */
    private SwitchPreference mAssetsNightModePreference;
    /**
     * 指定sdcard路径
     */
    private SwitchPreference mSDCardNightModePreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preferences);
        mBuildInNightModePreference = (SwitchPreference) findPreference(BUILD_IN_NIGHT_MODE_KEY);
        mAssetsNightModePreference = (SwitchPreference) findPreference(ASSETS_NIGHT_MODE_KEY);
        mSDCardNightModePreference = (SwitchPreference) findPreference(SDCARD_NIGHT_MODE_KEY);

        mBuildInNightModePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mAssetsNightModePreference.setChecked(false);
                mSDCardNightModePreference.setChecked(false);
                boolean isChecked = (boolean) newValue;
                if (isChecked){
                    SkinCompatManager.getInstance().loadSkin("night",null,SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                }else {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                }
                return true;
            }
        });
        mAssetsNightModePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mBuildInNightModePreference.setChecked(false);
                mSDCardNightModePreference.setChecked(false);
                boolean boolValue = (boolean) newValue;
                if (boolValue) {
                    SkinCompatManager.getInstance().loadSkin("night.skin", null, SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
                } else {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                }
                return true;
            }
        });
        mSDCardNightModePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                mBuildInNightModePreference.setChecked(false);
                mAssetsNightModePreference.setChecked(false);
                boolean boolValue = (boolean) newValue;
                if (boolValue) {
                    SkinCompatManager.getInstance().loadSkin("night.skin", null, CustomSDCardLoader.SKIN_LOADER_STRATEGY_SDCARD);
                } else {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                }
                return true;
            }
        });
    }
}
