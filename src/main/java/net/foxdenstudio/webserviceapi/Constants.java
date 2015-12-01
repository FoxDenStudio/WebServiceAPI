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
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 *
 */

package net.foxdenstudio.webserviceapi;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDSFDS-WSAPI
 */
public class Constants {

    /**
     * The port that the web server will run on.  Will eventually be changeable in the config.
     */
    public static final int SERVER_PORT = 3080;

    /**
     * The webmaster email address. Will appear in generated errors and status reports.
     */
    public static final String ADMIN_EMAIL = "npjoshf@gmail.com";

    /**
     * The default page if the user doesn't specify one.
     */
    public static final String DEFAULT_INDEX = "home";

    /**
     * The root directory for NovaServer related things... Currently the root for web files.
     */
    public static final String BASE_PATH = "pages";

}
