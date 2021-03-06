/*
 * This file is part of FastClasspathScanner.
 * 
 * Author: Luke Hutchison
 * 
 * Hosted at: https://github.com/lukehutch/fast-classpath-scanner
 * 
 * --
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Luke Hutchison
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.lukehutch.fastclasspathscanner.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/** Reflection utility methods that can be used by ClassLoaderHandlers. */
public class ReflectionUtils {
    /** Get the named field in the given object or any of its superclasses. */
    public static Object getFieldVal(final Object obj, final String fieldName)
            throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        if (obj != null) {
            for (Class<?> classOrSuperclass = obj.getClass(); classOrSuperclass != null; //
                    classOrSuperclass = classOrSuperclass.getSuperclass()) {
                try {
                    final Field field = classOrSuperclass.getDeclaredField(fieldName);
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    return field.get(obj);
                } catch (final NoSuchFieldException e) {
                    // Try parent
                }
            }
        }
        return null;
    }

    /** Invoke the named method in the given object or its superclasses. */
    public static Object invokeMethod(final Object obj, final String methodName)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj != null) {
            for (Class<?> classOrSuperclass = obj.getClass(); classOrSuperclass != null; //
                    classOrSuperclass = classOrSuperclass.getSuperclass()) {
                try {
                    final Method method = classOrSuperclass.getDeclaredMethod(methodName);
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    return method.invoke(obj);
                } catch (final NoSuchMethodException e) {
                    // Try parent
                }
            }
        }
        return null;
    }

    /** Invoke the named method in the given object or its superclasses. */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?> argType,
            final Object arg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj != null) {
            for (Class<?> classOrSuperclass = obj.getClass(); classOrSuperclass != null; //
                    classOrSuperclass = classOrSuperclass.getSuperclass()) {
                try {
                    final Method method = classOrSuperclass.getDeclaredMethod(methodName, argType);
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    return method.invoke(obj, arg);
                } catch (final NoSuchMethodException e) {
                    // Try parent
                }
            }
        }
        return null;
    }

    /** Invoke the named static method. */
    public static Object invokeStaticMethod(final Class<?> cls, final String methodName)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (cls != null) {
            try {
                final Method method = cls.getDeclaredMethod(methodName);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method.invoke(null);
            } catch (final NoSuchMethodException e) {
                // Try parent
            }
        }
        return null;
    }

    /** Invoke the named static method. */
    public static Object invokeStaticMethod(final Class<?> cls, final String methodName, final Class<?> argType,
            final Object arg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (cls != null) {
            try {
                final Method method = cls.getDeclaredMethod(methodName, argType);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method.invoke(null, arg);
            } catch (final NoSuchMethodException e) {
                // Try parent
            }
        }
        return null;
    }
}
