
package com.sgkhmjaes.jdias.service.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

public interface AutoMapping {
    
    public default void autoMapping(Object... args) throws AutoMappingException {
        
        try{
        
        Object[] mappingClassArray = new Object [args.length];
        HashMap<String, Method> methodsGetterMap = new HashMap<>();
        HashMap<String, Method> methodsSetterMap = new HashMap<>();
        Class<? extends AutoMapping> dtoClass = this.getClass();
        String simpleName = dtoClass.getSimpleName();
        Method m;
        try{
            m = dtoClass.getMethod("set"+simpleName, dtoClass);
        }catch(NoSuchMethodException e){
            m = null;
        }
        
        for (Method method : dtoClass.getMethods()) {
            String methodName = method.getName();
            if (methodName.length() > 3 && !"getClass".equals(methodName)&& 
                    !methodName.substring(3, 4).toLowerCase().equals(methodName.substring(3, 4))) {
                if ("set".equals(methodName.substring(0, 3))) methodsSetterMap.put(methodName, method);
                if ("get".equals(methodName.substring(0, 3))) methodsGetterMap.put(methodName, method);
        }}
        
        ModelMapper modelMapper = new ModelMapper();
        Condition skipPostDTO = new Condition() {
            @Override
            public boolean applies(MappingContext context) {
                String fieldName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1, simpleName.length());
                return !context.getMapping().getLastDestinationProperty().getName().equals(fieldName);
            }
        };
        modelMapper.getConfiguration().setPropertyCondition(skipPostDTO);
        
        for (int i=0; i<args.length; i++) {
            if (m!=null && args[i].getClass() == dtoClass) {
                mappingClassArray[i] = dtoClass.newInstance();
                m.invoke(mappingClassArray[i], args[i]);
            }
            else mappingClassArray[i] = modelMapper.map(args[i], dtoClass);
        }
 
        for (String methodName : methodsGetterMap.keySet()) {
            for (Object o : mappingClassArray) {
                Object invoke = o.getClass().getMethod(methodName).invoke(o);
                if (invoke != null && !invoke.toString().equals("0") 
                        && !invoke.toString().equals("false")
                    ) methodsSetterMap.get(methodName.replaceFirst("get", "set")).invoke(this, invoke);
        }}
        
    }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | 
            NoSuchMethodException | InstantiationException e){
         throw new AutoMappingException(e);
        }
        
    }
    
}
