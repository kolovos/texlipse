/*
 * $Id: TexScanner.java,v 1.6 2009/05/20 19:28:17 borisvl Exp $
 *
 * Copyright (c) 2004-2005 by the TeXlapse Team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package net.sourceforge.texlipse.editor.scanner;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.texlipse.editor.ColorManager;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

/**
 * TexScanner is used as a default scanner at the moment.
 * It works for "__tex_default" content type areas.
 * It uses defined rules to detect sequences and it returns the
 * specified token that satisfies a rule. The token defines how the
 * characters are presented.
 * 
 * @see net.sourceforge.texlipse.editor.partitioner.FastLaTeXPartitionScanner
 * @author Antti Pirinen
 */
public class TexScanner extends RuleBasedScanner {
    
    /**
     * A default constructor.
     * @param manager
     */
    public TexScanner(ColorManager manager) {
        // A token that defines how to color numbers
        IToken numberToken = new Token(new TextAttribute(manager
                .getColor(ColorManager.TEX_NUMBER),
                null,
                manager.getStyle(ColorManager.TEX_NUMBER_STYLE)));

        // A token that defines how to color command words (\command_word)
        IToken commandToken = new Token(new TextAttribute(manager
                .getColor(ColorManager.COMMAND),
                null,
                manager.getStyle(ColorManager.COMMAND_STYLE)));

        IToken braketToken = new Token(new TextAttribute(manager
                .getColor(ColorManager.CURLY_BRACKETS),
                null,
                manager.getStyle(ColorManager.CURLY_BRACKETS_STYLE)));

        IToken squareToken = new Token(new TextAttribute(manager
                .getColor(ColorManager.SQUARE_BRACKETS),
                null,
                manager.getStyle(ColorManager.SQUARE_BRACKETS_STYLE)));

        IToken commentToken = new Token(new TextAttribute(manager
                .getColor(ColorManager.COMMENT),
                null,
                manager.getStyle(ColorManager.COMMENT_STYLE)));

        // A token that defines how to color special characters (\_, \&, \~ ...)
        IToken specialCharToken = new Token(new TextAttribute(manager
                .getColor(ColorManager.TEX_SPECIAL),
                null,
                manager.getStyle(ColorManager.TEX_SPECIAL_STYLE)));
        
        List<IRule> rules = new ArrayList<IRule>();
        rules.add(new TexSpecialCharRule(specialCharToken));
        rules.add(new WordRule(new TexWord(), commandToken));
        rules.add(new NumberRule(numberToken));
        rules.add(new MultiLineRule("{", "}", braketToken, '\\'));
        rules.add(new MultiLineRule("[", "]", squareToken, '\\'));
        rules.add(new EndOfLineRule("%", commentToken, '\\'));
        rules.add(new WhitespaceRule(new WhitespaceDetector()));
        
        IRule[] result = new IRule[rules.size()];
        rules.toArray(result);
        setRules(result);
    }
}
