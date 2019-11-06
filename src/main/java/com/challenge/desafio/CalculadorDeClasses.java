package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Objects;

public class CalculadorDeClasses implements Calculavel {
    @Override
    public BigDecimal somar(Object obj) {
        return extractValues(obj, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object obj) {
        return extractValues(obj, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object obj) {
        return somar(obj).subtract(subtrair(obj));
    }

    private BigDecimal extractValues(Object obj, Class anotationClass){
        if (Objects.isNull(obj)) throw new NullPointerException("Obj null");
        Double somatorio = Double.valueOf(0L);
        try {
            Class classe = obj.getClass();
            for (Field f : classe.getDeclaredFields()) {
                if(f.isAnnotationPresent(anotationClass)) {
                    if (f.getType().equals(BigDecimal.class)) {
                        f.setAccessible(true);
                        BigDecimal o = (BigDecimal) f.get(obj);
                        System.out.println(o);
                        somatorio += o.doubleValue();
                    }
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException  e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return somatorio > 0 ? BigDecimal.valueOf(somatorio).setScale(0): BigDecimal.ZERO;
    }
}
