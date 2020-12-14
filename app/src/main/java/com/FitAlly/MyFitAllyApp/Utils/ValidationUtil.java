/*
 * Copyright 2017 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.FitAlly.MyFitAllyApp.Utils;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;


//validation class for the types of images selected

public class ValidationUtil {
    private static final String[] IMAGE_TYPE = new String[]{"jpg", "png", "jpeg", "bmp", "jp2", "psd", "tif", "gif"}; //an array of image types


    public static boolean isImage(Uri uri, Context context) {
        String mimeType = context.getContentResolver().getType(uri);

        if (mimeType != null) {
            return mimeType.contains("image");
        } else {
            String filenameArray[] = uri.getPath().split("\\.");//seperate by name and file type
            String extension = filenameArray[filenameArray.length - 1];

            if (extension != null) {
                for (String type : IMAGE_TYPE) {
                    if (type.toLowerCase().equals(extension.toLowerCase())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    public static boolean checkImageMinSize(Rect rect) {
        return rect.height() >= Constants.Profile.MIN_AVATAR_SIZE && rect.width() >= Constants.Profile.MIN_AVATAR_SIZE; //check that it meets the requirements
    }
}


