/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package ca.raihan.tictactoe.resources;

import java.io.File;
import java.io.InputStream;

import ca.raihan.cfg.Config;
import ca.raihan.cfg.ConfigManager;
import ca.raihan.cfg.JSONConfigManager;
import ca.raihan.cfg.XMLConfigManager;

import ca.raihan.util.internal.__UninstantiableImpl;

/**
 *
 * @author Pranjal Raihan
 */
public final class Resources extends __UninstantiableImpl {
    
    private static final ConfigManager xmlConfigManager = 
            new XMLConfigManager();
    
    private static final ConfigManager jsonConfigManager = 
            new JSONConfigManager();
    
    private static final Object MUTEX = new Object();
    
    private static final String DEFAULT_CFG_PATH = "default";
    
    private static Config DEFAULT_CFG;
    
    
    
    
    /**
     * Returns the XML-based {@code Config} for the specified {@code File}
     * 
     * @param resource the {@code File} to read
     * 
     * @return the read {@code Config}
     */
    public static Config getXMLConfig(File resource) {
        synchronized (MUTEX) {
            return xmlConfigManager.read(resource);
        }
    }
    
    /**
     * Returns the JSON-based {@code Config} for the specified {@code File}
     * 
     * @param resource the {@code File} to read
     * 
     * @return the read {@code Config}
     */
    public static Config getJSONConfig(File resource) {
        synchronized (MUTEX) {
            return jsonConfigManager.read(resource);
        }
    }
    
    /**
     * Returns the default {@code Config} stored in the source files
     * 
     * @return the default {@code Config}
     */
    public static Config getDefaultConfig() {
        if (DEFAULT_CFG != null) {
            return DEFAULT_CFG;
        }
        synchronized (MUTEX) {
            InputStream in = Resources.class.getResourceAsStream(
                    mapXML(DEFAULT_CFG_PATH));
            if (in == null) {
                in = Resources.class.getResourceAsStream(
                        mapJSON(DEFAULT_CFG_PATH));
                if (in == null) {
                    throw new IllegalStateException(
                            "No default resources found");
                }
                return jsonConfigManager.read(in);
            }
            return DEFAULT_CFG = xmlConfigManager.read(in);
        }
    }
    
    /**
     * Returns a resource name with the proper JSON file extension.
     * 
     * @param resource the file path
     * 
     * @return a resource name with the proper JSON file extension.
     */
    public static String mapJSON(String resource) {
        return resource + ".json";
    }
    
    /**
     * Returns a resource name with the proper XML file extension.
     * 
     * @param resource the file path
     * 
     * @return a resource name with the proper XMl file extension.
     */
    public static String mapXML(String resource) {
        return resource + ".xml";
    }
    
    
    
    
    private Resources() {
    }
    
}
