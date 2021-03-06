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

package net.foxdenstudio.webserviceapi.novacula.server;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge | FDS-WSAPI
 */
public abstract class Mimes {

    /**
     * Method getMimes having as argument the requested file name. *
     */
    public static String getMimes(String fileName) {

        //searching for the position of the last point
        int fileExtensionPosition = fileName.lastIndexOf(".");
        //extracting the extension file form the extension position+1 to the file length
        String fileExtension = fileName.substring(fileExtensionPosition + 1, fileName.length());

        /*
         * According to the file extension, we return the appropriate mime type
         */
        switch (fileExtension) {
            case "htm":
            case "html":
                return "text/html";
            case "py":
                return "text/html";
            case "xsl":
                return "text/xsl";
            case "gif":
                return "image/gif";
            case "jpeg":
            case "jpg":
            case "jpe":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "class":
            case "bin":
            case "dms":
            case "lha":
            case "lzh":
                return "application/octet-stream";
            case "jnlp":
                return "application/x-java-jnlp-file";
            case "doc":
                return "application/msword";
            case "oda":
                return "application/oda";
            case "pdf":
                return "application/pdf";
            case "ai":
            case "eps":
            case "ps":
                return "application/postscript";
            case "smi":
            case "smil":
                return "application/smil";
            case "xls":
                return "application/vnd.ms-excel";
            case "ppt":
                return "application/vnd.ms-powerpoint";
            case "wbxml":
                return "application/vnd.wap.wbxml";
            case "wmlc":
                return "application/vnd.wap.wmlc";
            case "wmlsc":
                return "application/vnd.wap.wmlscriptc";
            case "bcpio":
                return "application/x-bcpio";
            case "vcd":
                return "application/x-cdlink";
            case "pgn":
                return "application/x-chess-pgn";
            case "cpio":
                return "application/x-cpio";
            case "csh":
                return "application/x-csh";
            case "dcr":
            case "dir":
            case "dxr":
                return "application/x-director";
            case "dvi":
                return "application/x-dvi";
            case "spl":
                return "application/x-futuresplash";
            case "gtar":
                return "application/x-gtar";
            case "hdf":
                return "application/x-hdf";
            case "js":
                return "application/x-javascript";
            case "skp":
            case "skd":
            case "skt":
            case "skm":
                return "application/x-koan";
            case "latex":
                return "application/x-latex";
            case "nc":
            case "cdf":
                return "application/x-netcdf";
            case "sh":
                return "application/x-sh";
            case "shar":
                return "application/x-shar";
            case "swf":
                return "application/x-shockwave-flash";
            case "sit":
                return "application/x-stuffit";
            case "sv4cpio":
                return "application/x-sv4cpio";
            case "sv4crc":
                return "application/x-sv4crc";
            case "tar":
                return "application/x-tar";
            case "tcl":
                return "application/x-tcl";
            case "tex":
                return "application/x-tex";
            case "texinfo":
            case "texi":
                return "application/x-texinfo";
            case "t":
            case "tr":
            case "roff":
                return "application/x-troff";
            case "man":
                return "application/x-troff-man";
            case "me":
                return "application/x-troff-me";
            case "ms":
                return "application/x-troff-ms";
            case "ustar":
                return "application/x-ustar";
            case "src":
                return "application/x-wais-source";
            case "zip":
                return "application/zip";
            case "au":
            case "snd":
                return "audio/basic";
            case "mid":
            case "midi":
            case "kar":
                return "audio/midi";
            case "mpga":
            case "mp2":
            case "mp3":
                return "audio/mpeg";
            case "aif":
            case "aiff":
            case "aifc":
                return "audio/x-aiff";
            case "ram":
            case "rm":
                return "audio/x-pn-realaudio";
            case "rpm":
                return "audio/x-pn-realaudio-plugin";
            case "ra":
                return "audio/x-realaudio";
            case "wav":
                return "audio/x-wav";
            case "pdb":
            case "xyz":
                return "chemical/x-pdb";
            case "bmp":
                return "image/bmp";
            case "ief":
                return "image/ief";
            case "tiff":
            case "tif":
                return "image/tiff";
            case "wbmp":
                return "image/vnd.wap.wbmp";
            case "ras":
                return "image/x-cmu-raster";
            case "pnm":
                return "image/x-portable-anymap";
            case "pbm":
                return "image/x-portable-bitmap";
            case "pgm":
                return "image/x-portable-graymap";
            case "ppm":
                return "image/x-portable-pixmap";
            case "rgb":
                return "image/x-rgb";
            case "xbm":
                return "image/x-xbitmap";
            case "xpm":
                return "image/x-xpixmap";
            case "xwd":
                return "image/x-xwindowdump";
            case "igs":
            case "iges":
                return "model/iges";
            case "msh":
            case "mesh":
            case "silo":
                return "model/mesh";
            case "wrl":
            case "vrml":
                return "model/vrml";
            case "css":
                return "text/css";
            case "asc":
            case "txt":
                return "text/plain";
            case "rtx":
                return "text/richtext";
            case "rtf":
                return "text/rtf";
            case "sgm":
            case "sgml":
                return "text/sgml";
            case "tsv":
                return "text/tab-separated-values";
            case "wml":
                return "text/vnd.wap.wml";
            case "wmls":
                return "text/vnd.wap.wmlscript";
            case "etx":
                return "text/x-setext";
            case "xml":
                return "text/xml";
            case "mpeg":
            case "mpg":
            case "mpe":
                return "video/mpeg";
            case "qt":
            case "mov":
                return "video/quicktime";
            case "avi":
                return "video/x-msvideo";
            case "movie":
                return "video/x-sgi-movie";
            case "ice":
                return "x-conference/x-cooltalk";
            default:
                return "text/plain";
        }
    }
}
