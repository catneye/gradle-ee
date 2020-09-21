/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.util;

import com.catneye.annotation.Updatable;
import com.catneye.exception.DifferentClassUpdateException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zaleskovskiy
 */
public class TransformUtil {
    
    public static Object update(Object target, Object source, boolean newpreferred) {
        Class cls = target.getClass();
        if (!cls.getName().equals(source.getClass().getName())) {
            throw new DifferentClassUpdateException("Unable to update " + cls.getName() + " with object of " + source.getClass().getName());
        }
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Updatable.class);
            //if (annotation != null && !((Updatable)annotation).value()) continue;
            field.setAccessible(true);
            Object newval = null;
            Object val = null;
            try {
                val = field.get(target);
                newval = field.get(source);
                if (newpreferred && (newval != null)) {
                    field.set(target, newval);
                }
            } catch (NullPointerException | IllegalAccessException ex) {
                if ((val == null) && (newval != null)) {
                    try {
                        field.set(target, newval);
                    } catch (IllegalArgumentException | IllegalAccessException ex1) {
                        Logger.getLogger(TransformUtil.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }
        return target;
    }

    public static Object clone(Object target, Object source) {
        Class tcls = target.getClass();
        Class scls = source.getClass();

        Field[] tfields = tcls.getDeclaredFields();
        //Logger.getLogger(UtilsBean.class.getName()).log(Level.INFO, "target fields: {0}", tcls.getDeclaredFields());
        //Logger.getLogger(UtilsBean.class.getName()).log(Level.INFO, "source fields: {0}", scls.getDeclaredFields());
        for (Field tfield : tfields) {
            //Annotation annotation = tfield.getAnnotation(Updatable.class);
            //if (annotation != null && !((Updatable)annotation).value()) continue;
            tfield.setAccessible(true);

            try {
                Logger.getLogger(TransformUtil.class.getName()).log(Level.INFO, "target field name: {0}", tfield.getName());
                Field sfield = scls.getDeclaredField(tfield.getName());
                sfield.setAccessible(true);

                Logger.getLogger(TransformUtil.class.getName()).log(Level.INFO, "target class: {0}", tfield.getType().toString());
                Logger.getLogger(TransformUtil.class.getName()).log(Level.INFO, "source class: {0}", sfield.getType().toString());

                Object sval = null;
                Object tval = null;
                try {
                    tval = tfield.get(target);
                    sval = sfield.get(source);
                    //transform known types
                    if (tfield.getType().equals(java.time.LocalDate.class) && sfield.getType().equals(java.util.Date.class)) {
                        sval = DateUtil.asLocalDate((Date) sval);
                    } else if (tfield.getType().equals(java.util.Date.class) && sfield.getType().equals(java.time.LocalDate.class)) {
                        sval = DateUtil.asUtilDate((LocalDate) sval);
                    } else {
                        //sval = sfield.get(source);
                    }
                    if (sval != null) {
                        tfield.set(target, sval);
                    }
                } catch (NullPointerException | IllegalAccessException ex) {
                    if ((tval == null) && (sval != null)) {
                        try {
                            tfield.set(target, sval);
                        } catch (IllegalArgumentException | IllegalAccessException ex1) {
                            Logger.getLogger(TransformUtil.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(TransformUtil.class.getName()).log(Level.INFO, "NoSuchFieldException: {0}", tfield.getName());
            }

        }
        return target;
    }
}
