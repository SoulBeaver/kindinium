/*
Copyright 2014 Christian Broomfield

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.sbg.vindinium.kindinium

import java.io.InputStream
import java.util.Scanner

/**
 * Transforms a stream into a string.
 * @see http://stackoverflow.com/a/5445161 for more details on how it works
 */
fun streamToString(stream: InputStream): String {
    val scanner = Scanner(stream).useDelimiter("\\A")
    return if (scanner.hasNext()) scanner.next() else ""
}
