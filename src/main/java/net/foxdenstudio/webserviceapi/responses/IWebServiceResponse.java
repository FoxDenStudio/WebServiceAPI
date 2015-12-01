/*
 *  This file is part of a FoxDenStudio Project, licensed under the MIT License (MIT).
 *
 *  Copyright (c) 2015. FoxDenStudio - http://foxdenstudio.net/ and contributors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 */

package net.foxdenstudio.webserviceapi.responses;

import net.foxdenstudio.webserviceapi.novacula.server.Mimes;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDS-WSAPI
 */
public interface IWebServiceResponse {

    /**
     * @return A byte array that will be written out to the clients stream.
     */
    byte[] getByteData();

    /**
     * @return A string like plain/text that is the content type.  If the plugin is a dependency, you can use the Mimes.getMime(".{fileExt}")
     */
    default String mimeType() {
        return Mimes.getMimes(".html");
    }
}
