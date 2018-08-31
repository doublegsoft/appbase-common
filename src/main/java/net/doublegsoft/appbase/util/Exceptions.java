/*
 * Copyright 2016 doublegsoft.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.doublegsoft.appbase.util;

/**
 * It's an utility to check objects and raises a runtime exception.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class Exceptions {

  public static void throwIfBlank(String str, String caption) {
    if (str == null) {
      throw new NullPointerException("the given " + caption + " is null");
    }
    String s = str.trim();
    if (s.length() == 0) {
      throw new IllegalArgumentException("the given " + caption + " is empty");
    }
  }

  public static void throwIfNull(Object obj, String caption) {
    if (obj == null) {
      throw new NullPointerException("the given " + caption + " is null");
    }
    throwIfBlank((String) obj, caption);
  }

  private Exceptions() {

  }

}
