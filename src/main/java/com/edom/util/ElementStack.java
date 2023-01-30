/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   ElementStack.java

package com.edom.util;

import org.w3c.dom.Element;


/**
 * @author 王正权
 * 973598066@qq.com
 */
public class ElementStack
    
{

    public ElementStack()
    {
        this(50);
    }

    public ElementStack(int defaultCapacity)
    {
        lastElementIndex = -1;
         
        stack = new Element[defaultCapacity];
    }

    

    public void clear()
    {
        lastElementIndex = -1;
    }

    public Element peekElement()
    {
        if(lastElementIndex < 0)
            return null;
        else
            return stack[lastElementIndex];
    }

    public Element popElement()
    {
        if(lastElementIndex < 0)
            return null;
        else
            return stack[lastElementIndex--];
    }

    public void pushElement(Element element)
    {
        int length = stack.length;
        if(++lastElementIndex >= length)
            reallocate(length * 2);
        stack[lastElementIndex] = element;
    }

    protected void reallocate(int size)
    {
        Element oldStack[] = stack;
        stack = new Element[size];
        System.arraycopy(oldStack, 0, stack, 0, oldStack.length);
    }

    public int size()
    {
        return lastElementIndex + 1;
    }

    public Element getElement(int depth)
    {
        Element element;
        try
        {
            element = stack[depth];
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            element = null;
        }
        return element;
    }

   

    public Element getCurrent()
    {
        return peekElement();
    }

    
    protected Element stack[];
    protected int lastElementIndex;
}

 