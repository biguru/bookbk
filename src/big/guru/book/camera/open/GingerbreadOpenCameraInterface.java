/*
 * Copyright (C) 2012 ZXing authors
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

package big.guru.book.camera.open;

import android.annotation.TargetApi;
import android.hardware.Camera;


/**
 * Implementation for Android API 9 (Gingerbread) and later. This opens up the possibility of accessing
 * front cameras, and rotated cameras.
 */
@TargetApi(9)
public final class GingerbreadOpenCameraInterface implements OpenCameraInterface {

  public Camera open() {
    
    
    
    Camera camera = Camera.open();
    

    return camera;
  }

}
