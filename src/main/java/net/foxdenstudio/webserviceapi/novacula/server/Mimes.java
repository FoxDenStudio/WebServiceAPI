package net.foxdenstudio.webserviceapi.novacula.server;

/**
 * Created by Joshua Freedman on 11/29/2015.
 * Project: SpongeForge->FDS-WSAPI
 */
public abstract class Mimes {
    private static int fileExtensionPosition; //contain the file extension position in the file name
    private static String fileExtension; //contain the file extension

    /**
     * Method getMimes having as argument the requested file name. *
     */
    public static String getMimes(String fileName) {

        //seraching for the position of the last point
        fileExtensionPosition = fileName.lastIndexOf(".");
        //extracting the extension file form the extension position+1 to the file length
        fileExtension = fileName.substring(fileExtensionPosition + 1, fileName.length());

        /**
         * According to the file extension ,we return the appropriate mime type
         */
        if (fileExtension.equals("htm") || fileExtension.equals("html"))
            return "text/html";
        else if (fileExtension.equals("py"))
            return "text/html";
        else if (fileExtension.equals("xsl"))
            return "text/xsl";
        else if (fileExtension.equals("gif"))
            return "image/gif";
        else if (fileExtension.equals("jpeg") || fileExtension.equals("jpg") || fileExtension.equals("jpe"))
            return "image/jpeg";
        else if (fileExtension.equals("png"))
            return "image/png";
        else if (fileExtension.equals("class") || fileExtension.equals("bin") || fileExtension.equals("dms") || fileExtension.equals("lha") || fileExtension.equals("lzh"))
            return "application/octet-stream";
        else if (fileExtension.equals("jnlp"))
            return "application/x-java-jnlp-file";
        else if (fileExtension.equals("doc"))
            return "application/msword";
        else if (fileExtension.equals("oda"))
            return "application/oda";
        else if (fileExtension.equals("jnlp"))
            return "application/x-java-jnlp-file";
        else if (fileExtension.equals("pdf"))
            return "application/pdf";
        else if (fileExtension.equals("ai") || fileExtension.equals("eps") || fileExtension.equals("ps"))
            return "application/postscript";
        else if (fileExtension.equals("smi") || fileExtension.equals("smil"))
            return "application/smil";
        else if (fileExtension.equals("xls"))
            return "application/vnd.ms-excel";
        else if (fileExtension.equals("ppt"))
            return "application/vnd.ms-powerpoint";
        else if (fileExtension.equals("wbxml"))
            return "application/vnd.wap.wbxml";
        else if (fileExtension.equals("wmlc"))
            return "application/vnd.wap.wmlc";
        else if (fileExtension.equals("wmlsc"))
            return "application/vnd.wap.wmlscriptc";
        else if (fileExtension.equals("bcpio"))
            return "application/x-bcpio";
        else if (fileExtension.equals("vcd"))
            return "application/x-cdlink";
        else if (fileExtension.equals("pgn"))
            return "application/x-chess-pgn";
        else if (fileExtension.equals("cpio"))
            return "application/x-cpio";
        else if (fileExtension.equals("csh"))
            return "application/x-csh";
        else if (fileExtension.equals("dcr") || fileExtension.equals("dir") || fileExtension.equals("dxr"))
            return "application/x-director";
        else if (fileExtension.equals("dvi"))
            return "application/x-dvi";
        else if (fileExtension.equals("spl"))
            return "application/x-futuresplash";
        else if (fileExtension.equals("gtar"))
            return "application/x-gtar";
        else if (fileExtension.equals("hdf"))
            return "application/x-hdf";
        else if (fileExtension.equals("js"))
            return "application/x-javascript";
        else if (fileExtension.equals("skp") || fileExtension.equals("skd") || fileExtension.equals("skt") || fileExtension.equals("skm"))
            return "application/x-koan";
        else if (fileExtension.equals("latex"))
            return "application/x-latex";
        else if (fileExtension.equals("nc") || fileExtension.equals("cdf"))
            return "application/x-netcdf";
        else if (fileExtension.equals("sh"))
            return "application/x-sh";
        else if (fileExtension.equals("shar"))
            return "application/x-shar";
        else if (fileExtension.equals("swf"))
            return "application/x-shockwave-flash";
        else if (fileExtension.equals("sit"))
            return "application/x-stuffit";
        else if (fileExtension.equals("sv4cpio"))
            return "application/x-sv4cpio";
        else if (fileExtension.equals("sv4crc"))
            return "application/x-sv4crc";
        else if (fileExtension.equals("tar"))
            return "application/x-tar";
        else if (fileExtension.equals("tcl"))
            return "application/x-tcl";
        else if (fileExtension.equals("tex"))
            return "application/x-tex";
        else if (fileExtension.equals("texinfo") || fileExtension.equals("texi"))
            return "application/x-texinfo";
        else if (fileExtension.equals("t") || fileExtension.equals("tr") || fileExtension.equals("roff"))
            return "application/x-troff";
        else if (fileExtension.equals("man"))
            return "application/x-troff-man";
        else if (fileExtension.equals("me"))
            return "application/x-troff-me";
        else if (fileExtension.equals("ms"))
            return "application/x-troff-ms";
        else if (fileExtension.equals("ustar"))
            return "application/x-ustar";
        else if (fileExtension.equals("src"))
            return "application/x-wais-source";
        else if (fileExtension.equals("zip"))
            return "application/zip";
        else if (fileExtension.equals("au") || fileExtension.equals("snd"))
            return "audio/basic";
        else if (fileExtension.equals("mid") || fileExtension.equals("midi") || fileExtension.equals("kar"))
            return "audio/midi";
        else if (fileExtension.equals("mpga") || fileExtension.equals("mp2") || fileExtension.equals("mp3"))
            return "audio/mpeg";
        else if (fileExtension.equals("aif") || fileExtension.equals("aiff") || fileExtension.equals("aifc"))
            return "audio/x-aiff";
        else if (fileExtension.equals("ram") || fileExtension.equals("rm"))
            return "audio/x-pn-realaudio";
        else if (fileExtension.equals("rpm"))
            return "audio/x-pn-realaudio-plugin";
        else if (fileExtension.equals("ra"))
            return "audio/x-realaudio";
        else if (fileExtension.equals("wav"))
            return "audio/x-wav";
        else if (fileExtension.equals("pdb") || fileExtension.equals("xyz"))
            return "chemical/x-pdb";
        else if (fileExtension.equals("bmp"))
            return "image/bmp";
        else if (fileExtension.equals("ief"))
            return "image/ief";
        else if (fileExtension.equals("png"))
            return "image/png";
        else if (fileExtension.equals("tiff") || fileExtension.equals("tif"))
            return "image/tiff";
        else if (fileExtension.equals("wbmp"))
            return "image/vnd.wap.wbmp";
        else if (fileExtension.equals("ras"))
            return "image/x-cmu-raster";
        else if (fileExtension.equals("pnm"))
            return "image/x-portable-anymap";
        else if (fileExtension.equals("pbm"))
            return "image/x-portable-bitmap";
        else if (fileExtension.equals("pgm"))
            return "image/x-portable-graymap";
        else if (fileExtension.equals("ppm"))
            return "image/x-portable-pixmap";
        else if (fileExtension.equals("rgb"))
            return "image/x-rgb";
        else if (fileExtension.equals("xbm"))
            return "image/x-xbitmap";
        else if (fileExtension.equals("xpm"))
            return "image/x-xpixmap";
        else if (fileExtension.equals("xwd"))
            return "image/x-xwindowdump";
        else if (fileExtension.equals("igs") || fileExtension.equals("iges"))
            return "model/iges";
        else if (fileExtension.equals("msh") || fileExtension.equals("mesh") || fileExtension.equals("silo"))
            return "model/mesh";
        else if (fileExtension.equals("wrl") || fileExtension.equals("vrml"))
            return "model/vrml";
        else if (fileExtension.equals("css"))
            return "text/css";
        else if (fileExtension.equals("asc") || fileExtension.equals("txt"))
            return "text/plain";
        else if (fileExtension.equals("rtx"))
            return "text/richtext";
        else if (fileExtension.equals("rtf"))
            return "text/rtf";
        else if (fileExtension.equals("sgm") || fileExtension.equals("sgml"))
            return "text/sgml";
        else if (fileExtension.equals("tsv"))
            return "text/tab-separated-values";
        else if (fileExtension.equals("wml"))
            return "text/vnd.wap.wml";
        else if (fileExtension.equals("wmls"))
            return "text/vnd.wap.wmlscript";
        else if (fileExtension.equals("etx"))
            return "text/x-setext";
        else if (fileExtension.equals("xml"))
            return "text/xml";
        else if (fileExtension.equals("mpeg") || fileExtension.equals("mpg") || fileExtension.equals("mpe"))
            return "video/mpeg";
        else if (fileExtension.equals("qt") || fileExtension.equals("mov"))
            return "video/quicktime";
        else if (fileExtension.equals("avi"))
            return "video/x-msvideo";
        else if (fileExtension.equals("movie"))
            return "video/x-sgi-movie";
        else if (fileExtension.equals("ice"))
            return "x-conference/x-cooltalk";
        else
            return "text/plain";
    }
}
