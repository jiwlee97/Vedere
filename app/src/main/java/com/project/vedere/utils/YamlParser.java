package com.project.vedere.utils;

import android.content.Context;
import android.content.res.AssetManager;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * yaml을 파싱하기위한 유틸 클래스
 */
public class YamlParser {
    private Yaml yaml = new Yaml();

    private YamlParser() {

    }

    private static class YamlParserHolder {
        public static final YamlParser INSTANCE = new YamlParser();
    }

    public static YamlParser getInstance() {
        return YamlParserHolder.INSTANCE;
    }
    /**
     * yaml 파일을 파싱
     * @param context 안드로이드 컨텍스트
     * @param filePath 파싱하고자하는 파일의 이름
     * @return yaml파일의 내용을 Map 객체에 반환
     * @throws IOException 파일 입출력
     */
    public Map<String, Object> parseYaml(Context context, String filePath) throws IOException {
        AssetManager am = context.getAssets();
        InputStream inputStream = am.open(filePath);
        return yaml.load(inputStream);
    }

}
