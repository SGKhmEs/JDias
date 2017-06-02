
package com.sgkhmjaes.jdias.service.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.modelmapper.ModelMapper;

public interface AutoMapping {
    
    public default void autoMapping(Object... args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        //try{
        
        Object[] mappingClassArray = new Object [args.length];
        HashMap<String, Method> methodsGetterMap = new HashMap<>();
        HashMap<String, Method> methodsSetterMap = new HashMap<>();
        Class<? extends AutoMapping> aClass = this.getClass();
        Method[] methods = aClass.getMethods();

        for (int i=0; i<args.length; i++) mappingClassArray[i] = new ModelMapper().map(args[i], aClass);
        
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.length() > 3 && !"getClass".equals(methodName)&& 
                    !methodName.substring(3, 4).toLowerCase().equals(methodName.substring(3, 4))) {
                if ("get".equals(methodName.substring(0, 3))) methodsGetterMap.put(methodName, method);
                if ("set".equals(methodName.substring(0, 3))) methodsSetterMap.put(methodName, method);
        }}

        for (String methodName : methodsGetterMap.keySet()) {
            for (Object o : mappingClassArray) {
                Object invoke = o.getClass().getMethod(methodName, null).invoke(o);
                if (invoke != null && !invoke.toString().equals("0")) {
                    Method setter = methodsSetterMap.get(methodName.replaceFirst("get", "set"));
                    setter.invoke(this, invoke);
        }}}
        
        /*
        }catch (NoSuchMethodException, SecurityException, InstantiationException, 
        IllegalAccessException, IllegalArgumentException, InvocationTargetException e){
            throw new AutoMappingException(e);
        }
        */
    }
    
}
