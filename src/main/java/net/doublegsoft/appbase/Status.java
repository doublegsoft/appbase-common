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

package net.doublegsoft.appbase;

/**
 *
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public enum Status {

    YES("Y"),

    NO("N"),

    TRUE("T"),

    FALSE("F"),

    ACTIVE("A"),

    INACTIVE("I"),

    ENABLED("E"),

    DISABLED("D");

    public static Status getStatus(String flag) {
        for (Status s : Status.values()) {
            if (s.ch.equals(flag)) {
                return s;
            }
        }
        throw new IllegalArgumentException("not found status with the flag(\"" + flag + "\")");
    }

    private final String ch;

    private Status(String ch) {
        this.ch = ch;
    }

    @Override
    public String toString() {
        return ch;
    }
}
