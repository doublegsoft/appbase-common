/*
 * Copyright 2017 doublegsoft.net
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
package net.doublegsoft.appbase.event;

import java.io.Serializable;

/**
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 2.0
 */
public class AsyncEvent implements Serializable {

    private static final long serialVersionUID = 2719595528682578504L;

    private String source;

    private String target;

    private Object payload;

    private long timestamp;

}
