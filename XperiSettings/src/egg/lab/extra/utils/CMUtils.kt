/*
 * Copyright (C) 2022 The Android Open Source Project
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
 */

package egg.lab.extra.utils

import android.util.Log
import android.content.Context
import android.hardware.display.ColorDisplayManager
import vendor.semc.hardware.display.V2_0.IDisplay;

private const val TAG : String = "CMUtils";

class CMUtils {
    private var mCdm : ColorDisplayManager? = null
    private var mHidl : IDisplay? = null

    constructor(context: Context?) {

        if (this.mCdm != null) {
            return
        }

        if (context == null) {
            Log.e(TAG, "Error: context NULL!")
            return
        }

        this.mCdm = context.getSystemService(ColorDisplayManager::class.java);
        if (this.mCdm == null) {
            Log.e(TAG, "Error: Display manager NULL")
            return
        }

        this.mHidl = IDisplay.getService();
        if (this.mHidl == null) {
            Log.e(TAG, "Error, HIDL not found")
            return
        }
    }

    fun enableCM() : Unit {
        if (this.mHidl != null) {
            this.mHidl?.set_sspp_color_mode(0);
            this.mCdm?.setColorMode(0);
            this.mHidl?.set_color_mode(0);
        }
    }

    fun disableCM() : Unit {
        if (this.mHidl != null) {
            this.mHidl?.set_sspp_color_mode(1);
            this.mCdm?.setColorMode(3);
            this.mHidl?.set_color_mode(1);
        }
    }
}