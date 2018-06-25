package org.saiku.web.svg;

/**
 * @author Tomasz Nurkiewicz
 * @since 1/15/13, 10:29 AM
 */

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.batik.bridge.*;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.io.IOUtils;
import org.apache.fop.svg.PDFTranscoder;
import org.joda.time.LocalDateTime;
import org.w3c.dom.svg.SVGDocument;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;

public abstract class Converter
{

    abstract public void convert(InputStream in, OutputStream out, Integer size) throws IOException, TranscoderException;

    private final String contentType;
    private final String extension;

    Converter(String contentType, String extension)
    {
        this.contentType = contentType;
        this.extension = extension;
    }

    public String getContentType()
    {
        return contentType;
    }

    public String getExtension()
    {
        return extension;
    }

    public static Converter byType(String type)
    {
        if (type.equals("SVG")) { return new SvgConverter(); }
        if (type.equals("PNG")) { return new PngConverter(); }
        if (type.equals("TIFF")) { return new TiffConverter(); }
        if (type.equals("JPG")) { return new JpgConverter(); }
        if (type.equals("PDF")) { return new PdfConverter(); }
        return null;
    }
}

class SvgConverter extends Converter
{

    public SvgConverter()
    {
        super("image/svg+xml", "svg");
    }

    public void convert(InputStream in, OutputStream out, Integer size) throws IOException
    {
        IOUtils.copy(in, out);
    }

}

abstract class BatikConverter extends Converter
{

    BatikConverter(String extension)
    {
        super("image/" + extension, extension);
    }

    BatikConverter(String contentType, String extension)
    {
        super(contentType, extension);
    }

    public void convert(InputStream in, OutputStream out, Integer size) throws TranscoderException
    {
        Transcoder t = createTranscoder();
        if (size != null)
        {
            final float sizeBound = Math.max(Math.min(size, 2000.0f), 32.0f);
            t.addTranscodingHint(SVGAbstractTranscoder.KEY_WIDTH, sizeBound);
        }
        t.transcode(new TranscoderInput(in), new TranscoderOutput(out));
    }

    protected abstract Transcoder createTranscoder();

}

class PngConverter extends BatikConverter
{

    public PngConverter()
    {
        super("png");
    }

    protected Transcoder createTranscoder() { return new PNGTranscoder();}

}

class JpgConverter extends BatikConverter
{

    public JpgConverter()
    {
        super("image/jpeg", "jpg");
    }

    protected Transcoder createTranscoder()
    {
        Transcoder t = new JPEGTranscoder();
        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 0.95f);
        return t;
    }

}

class TiffConverter extends BatikConverter
{

    public TiffConverter()
    {
        super("tiff");
    }

    protected Transcoder createTranscoder() {return new TIFFTranscoder();}

}

class PdfConverter extends Converter {

    /** The SVG document factory. */
    protected SAXSVGDocumentFactory factory;
    /** The SVG bridge context. */
    protected BridgeContext ctx;
    /** The GVT builder */
    protected GVTBuilder builder;

    /** Creates an SvgToPdf object. */
    public PdfConverter() {
        super("application/pdf", "pdf");
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        factory = new SAXSVGDocumentFactory(parser);

        UserAgent userAgent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(userAgent);
        ctx = new BridgeContext(userAgent, loader);
        ctx.setDynamicState(BridgeContext.DYNAMIC);

        builder = new GVTBuilder();
    }

    @Override
    public void convert(InputStream in, OutputStream out, Integer size) throws IOException, TranscoderException {
        Document document = new Document(new Rectangle(1500, 1500));
        // step 2
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, out);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new TranscoderException(e);
        }
        // step 3
        document.open();
        // step 4
        PdfContentByte cb = writer.getDirectContent();
        PdfTemplate map = cb.createTemplate(6000, 6000);

        File svgFile = new File("tempSvgFile" + LocalDateTime.now() + ".tmp");

        java.nio.file.Files.copy(
                in,
                svgFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        IOUtils.closeQuietly(in);

        drawSvg(map, svgFile);
        cb.addTemplate(map, 0, 0);
        // step 5
        document.close();
    }

    /**
     * Draws an SVG file to a PdfTemplate.
     * @param map      the template to which the SVG has to be drawn.
     * @param svgFile the SVG content.
     * @throws IOException
     */
    public void drawSvg(PdfTemplate map, File svgFile) throws IOException {
        Graphics2D g2d = new PdfGraphics2D(map, 1500, 1500);
        SVGDocument city = factory.createSVGDocument(svgFile.toURI().toString());
        GraphicsNode mapGraphics = builder.build(ctx, city);
        mapGraphics.paint(g2d);
        g2d.dispose();
    }

}

class PdfConverterOld extends BatikConverter
{

    public PdfConverterOld()
    {
        super("application/pdf", "pdf");
    }

    protected Transcoder createTranscoder() {return new PDFTranscoder();}

}

