package com.project.vedere;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.project.vedere.utils.YamlParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class YamlParserTest {
    private Context instrumentationCtx;
    private YamlParser yamlParser;
    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getInstrumentation().getContext();
        yamlParser = new YamlParser();
    }

    @Test
    public void test() throws IOException {
        Yaml yaml = new Yaml();

        Map<String, Object> obj = yamlParser.parseYaml(instrumentationCtx, "Keys.yaml");
        Log.d("Yaml", obj.toString());
    }

}

