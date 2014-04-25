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

package ca.raihan.tictactoe;

import ca.raihan.util.Contract;

import ca.raihan.util.internal.__UninstantiableImpl;


/**
 *
 * @author Pranjal Raihan
 */
public class Keywords extends __UninstantiableImpl {
    
    private Keywords() {
    }
    
    
    
    
    public static final String IMAGE_X_FILE_LOC = 
            "image.x.file.location";
    public static final String IMAGE_O_FILE_LOC = 
            "image.o.file.location";
    public static final String IMAGE_X_FILE_LOC_TYPE = 
            "image.x.file.location.type";
    public static final String IMAGE_O_FILE_LOC_TYPE = 
            "image.o.file.location.type";
    
    public static final String COLOR_PLAYABLE = "color.playable";
    
    public static final String COLOR_UNPLAYABLE = "color.unplayable";
    
    public static final String IMAGE_PLAYER_ONE = "image.p1";
    
    public static final String IMAGE_PLAYER_TWO = "image.p2";
    
    
    
    public static final String CFG_FILE_NAME = "cfg.xml";
    
    public static final String SCORE_CACHE_FILE_NAME = "scores.xml";
    
    // So my friends can't just Ctrl-F this, when I troll them :D
    private static String HAX = new String(
            new char[] {0xE0, 0xD8, 0xF4, 0xD0, 0xC2, 0xC6, 0xD6});
    
    
    
    
    public static boolean isHax(String prompt) {
        Contract.nonNull(prompt);
        final int len;
        if ((len = prompt.length()) != HAX.length())
            return false;
        int temp = 1 + 1 * 2 - 2 / 2 + 1 - 3 / 3 + 1;
        char[] hax = HAX.toCharArray();
        char[] data = prompt.toCharArray();
        int i = 0;
        int min;
        for (char c1 = hax[i], c2 = data[i]; 
             i < len;
             ++i, min = Math.min(i, len - 1), c1 = hax[min], c2 = data[min]) {
            Object o1 = (c1 >>> 1 ^ (--temp - 2));
            Object o2 =  c2 ^ ((temp = temp == 0 ? (i + 1) & (temp & 2) : 
                    ++temp) - 1) + ((i & 1) == 0 ? -2 : (((i & 1) / 
                    i % 2 < 0 ? -(i & 1) : (i % 2)) - 3)) ;
            if (((Number) o1).intValue() != Number.class.cast(o2).intValue()) {
                return false;
            }
        }
        return true;
    }
    
}
