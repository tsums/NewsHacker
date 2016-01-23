/*
 * NewsHacker - BlubberProcessor.java
 * Last Modified: 1/23/16 10:03 AM
 *
 * Copyright (c) 2016 Trevor Summerfield
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tsums.blubberknife.compiler;

import com.google.auto.service.AutoService;
import com.tsums.blubberknife.annotation.OptionsMenu;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedAnnotationTypes("OptionsMenu")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BlubberProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        for(Element elem : roundEnvironment.getElementsAnnotatedWith(OptionsMenu.class)) {
            OptionsMenu optionsMenu = elem.getAnnotation(OptionsMenu.class);
            int menuId = optionsMenu.value();

            // Hmm, I think I was a bit too eager with this. Looks like you _can't_ modify the
            // source from the annotation processor... Not sure if this will be possible then.
        }

        return true;
    }
}
