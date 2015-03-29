/* This file was generated by SableCC (http://www.sablecc.org/). */

package net.sourceforge.texlipse.texparser.node;

import net.sourceforge.texlipse.texparser.analysis.*;

@SuppressWarnings("nls")
public final class TCparagraph extends Token
{
    public TCparagraph()
    {
        super.setText("\\paragraph");
    }

    public TCparagraph(int line, int pos)
    {
        super.setText("\\paragraph");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TCparagraph(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTCparagraph(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TCparagraph text.");
    }
}
