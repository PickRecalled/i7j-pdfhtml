/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.html2pdf.css.apply.impl;

import com.itextpdf.html2pdf.attach.ElementResult;
import com.itextpdf.html2pdf.attach.ProcessorContext;
import com.itextpdf.html2pdf.attach.TagProcessingResult;
import com.itextpdf.html2pdf.css.CssConstants;
import com.itextpdf.html2pdf.css.apply.ICssApplier;
import com.itextpdf.html2pdf.css.resolve.ICssResolver;
import com.itextpdf.html2pdf.html.node.INode;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.property.Property;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PTagCssApplier implements ICssApplier {
    @Override
    public void apply(ProcessorContext context, INode node, TagProcessingResult result) {
        ICssResolver cssResolver = context.getCssResolver();
        Map<String, String> cssProps = cssResolver.resolveStyles(node);
        if (result instanceof ElementResult && ((ElementResult) result).getElement() instanceof IPropertyContainer) {
            if (cssProps.get(CssConstants.FONT_FAMILY) != null) {
                try {
                    ((ElementResult) result).getElement().setProperty(Property.FONT, context.getFontResolver().getFont(cssProps.get(CssConstants.FONT_FAMILY)));
                } catch (IOException exc) {
                    Logger logger = LoggerFactory.getLogger(PTagCssApplier.class);
                    logger.error("Could not load font", exc);
                }
            }
            if (cssProps.get(CssConstants.FONT_SIZE) != null) {
                ((ElementResult) result).getElement().setProperty(Property.FONT_SIZE, Integer.valueOf(cssProps.get(CssConstants.FONT_SIZE)));
            }
        }
    }
}