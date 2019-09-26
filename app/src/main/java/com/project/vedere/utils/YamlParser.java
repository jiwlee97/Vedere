package com.project.vedere.utils;

import android.content.Context;
import android.content.res.AssetManager;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;

public class YamlParser {
    private static Yaml yaml = new Yaml();

    public Map<String, Object> parseYaml(Context context, String filePath) throws IOException {
        AssetManager am = context.getAssets();
        InputStream inputStream = am.open(filePath);
        return yaml.load(inputStream);
    }

}
