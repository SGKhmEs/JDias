package com.sgkhmjaes.jdias.service.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

public interface AutoMapping {

    public static HashMap<Class, HashMap<Class, FastMethod>> METHODS_ENTITY_DTO = new HashMap<>();
    public static HashMap<Class, HashMap<Class, ArrayList<FastMethod>>> METHODS_ENTITY_DTOm = new HashMap<>();

    public static HashMap<Class, HashMap<Class, ArrayList<FastMethod>>> METHODS_DTO_ENTITY = new HashMap<>();
    public static HashMap<Class, HashMap<Class, FastMethod[]>> METHODS_DTO_ENTITYm = new HashMap<>();

    public default void mappingToDTO(Object... args) throws InvocationTargetException {

        Class<? extends AutoMapping> dtoClass = this.getClass();

        if (METHODS_ENTITY_DTO.get(dtoClass) == null && METHODS_ENTITY_DTOm.get(dtoClass) == null) {

            HashMap<Class, ArrayList<FastMethod>> methodsEntityDTOm = new HashMap();
            HashMap<Class, FastMethod> methodsEntityDTO = new HashMap();
            HashMap<String, FastMethod> methodsSetter = new HashMap<>();

            FastClass dtoFastClass = FastClass.create(dtoClass);
            for (Method method : dtoClass.getMethods()) {
                String methodName = method.getName();
                if (methodName.length() > 3 && "set".equals(methodName.substring(0, 3))
                        && !methodName.substring(3, 4).toLowerCase().equals(methodName.substring(3, 4))) {
                    methodsSetter.put(methodName, dtoFastClass.getMethod(method));
                }
            }

            for (Object arg : args) {
                Class<?> aClass = arg.getClass();
                FastClass aFastClass = FastClass.create(aClass);
                FastMethod methodSet = methodsSetter.get("set" + aClass.getSimpleName());

                if (methodSet != null) {
                    methodsEntityDTO.put(aClass, methodSet);
                    try{
                        methodSet.invoke(this, new Object[]{arg});
                    }catch (InvocationTargetException e){
                        String messageException = "AUTOMAPPING EXCEPTION. Method '"+methodSet+"' calling for object '" + this + "' with an argument '" + arg + "'.";
                        System.err.println(messageException);
                        throw new InvocationTargetException (e);
                    }
                    continue;
                }
                ArrayList<FastMethod> fastMethodList = new ArrayList<>();
                for (Method methodGetter : aClass.getMethods()) {
                    String methodName = methodGetter.getName();
                    if (methodName.length() > 3 && "get".equals(methodName.substring(0, 3))) {
                        FastMethod fastMethodSetter = methodsSetter.get(methodName.replaceFirst("get", "set"));
                        if (fastMethodSetter != null) {
                            FastMethod fastMethodGetter = aFastClass.getMethod(methodGetter);
                            Object invoke = fastMethodGetter.invoke(arg, null);
                            fastMethodList.add(fastMethodGetter);
                            fastMethodList.add(fastMethodSetter);
                            if (invoke != null && !invoke.toString().equals("0") && !invoke.toString().equals("false")) {
                                try {
                                    fastMethodSetter.invoke(this, new Object[]{invoke});
                                } catch (InvocationTargetException e) {
                                    String messageException = "AUTOMAPPING EXCEPTION. Method '"+fastMethodSetter+"' calling for object '" + this + "' with an argument '" + invoke + "'.";
                                    System.err.println(messageException);
                                    throw new InvocationTargetException (e);
                                }
                            }
                        }
                    }
                    methodsEntityDTOm.put(aClass, fastMethodList);
                }
            }

            synchronized (METHODS_ENTITY_DTO) {
                METHODS_ENTITY_DTO.put(dtoClass, methodsEntityDTO);
            }
            synchronized (METHODS_ENTITY_DTOm) {
                METHODS_ENTITY_DTOm.put(dtoClass, methodsEntityDTOm);
            }

        } else {
            HashMap<Class, FastMethod> methodsEntityDTO = METHODS_ENTITY_DTO.get(dtoClass);
            HashMap<Class, ArrayList<FastMethod>> methodsEntityDTOm = METHODS_ENTITY_DTOm.get(dtoClass);

            for (Object arg : args) {
                Class<?> aClass = arg.getClass();

                if (methodsEntityDTO != null) {
                    FastMethod getObject = methodsEntityDTO.get(aClass);
                    if (getObject != null) {
                        getObject.invoke(this, new Object[]{arg});
                    }
                }

                if (methodsEntityDTOm != null) {
                    ArrayList<FastMethod> fastMethodsList = methodsEntityDTOm.get(aClass);
                    if (fastMethodsList != null && !fastMethodsList.isEmpty()) {
                        for (int i = 0; i < fastMethodsList.size(); i += 2) {
                            FastMethod getter = fastMethodsList.get(i);
                            FastMethod setter = fastMethodsList.get(i + 1);
                            Object result = getter.invoke(arg, null);
                            if (result != null) {
                                setter.invoke(this, new Object[]{result});
                            }
                        }
                    }

                }

            }

        }
    }

    public default void mappingFromDTO(Object... args) throws InvocationTargetException {

        Class<? extends AutoMapping> dtoClass = this.getClass();
        FastClass dtoFastClass = FastClass.create(dtoClass);

        if (METHODS_DTO_ENTITY.get(dtoClass) == null && METHODS_DTO_ENTITYm.get(dtoClass) == null) {

            HashMap<String, FastMethod> methodsGetter = new HashMap<>();
            HashMap<Class, ArrayList<FastMethod>> methodsDTOEntity = new HashMap();
            HashMap<Class, FastMethod[]> methodsDTOEntityM = new HashMap();

            for (Method method : dtoClass.getMethods()) {
                String methodName = method.getName();
                if (methodName.length() > 3 && "get".equals(methodName.substring(0, 3)) && !methodName.equals("getClass")
                        && !methodName.substring(3, 4).toLowerCase().equals(methodName.substring(3, 4))) {
                    methodsGetter.put(methodName, dtoFastClass.getMethod(method));
                }
            }
            
            for (Object arg : args) {

                Class<?> aClass = arg.getClass();
                FastClass destFastClass = FastClass.create(aClass);
                FastMethod getterDTO = methodsGetter.get("get" + aClass.getSimpleName());

                if (getterDTO != null) {
                    Object getterDtoResult;
                    try {
                        getterDtoResult = getterDTO.invoke(this, null);
                    } catch (InvocationTargetException e) {
                        String messageException = "AUTOMAPPING EXCEPTION. Method '"+getterDTO+"' calling for object '" + this + "'.";
                        System.err.println(messageException);
                        throw new InvocationTargetException (e);
                    }

                    ArrayList<FastMethod> fastMethodList = new ArrayList<>();
                    fastMethodList.add(getterDTO);
                    Method[] allMethods = aClass.getMethods();
                    for (Method setterMethod : aClass.getMethods()) {
                        String methodName = setterMethod.getName();
                        if (methodName.length() > 3 && "set".equals(methodName.substring(0, 3))
                                && !methodName.substring(3, 4).toLowerCase().equals(methodName.substring(3, 4))) {
                            String get = methodName.replaceFirst("set", "get");
                            FastMethod getterDtoInsideObject = null;
                            for (Method m : allMethods) {
                                if (!m.getName().equals(get)) {
                                    continue;
                                }
                                getterDtoInsideObject = destFastClass.getMethod(m);
                                break;
                            }
                            FastMethod fastSetterMethod = destFastClass.getMethod(setterMethod);
                            try {
                                fastSetterMethod.invoke(arg, new Object[]{getterDtoInsideObject.invoke(getterDtoResult, null)});
                            } catch (InvocationTargetException e) {
                                String messageException = "AUTOMAPPING EXCEPTION. Method '"+getterDtoInsideObject+"' calling for object '" + getterDtoResult +
                                        "', and method '"+fastSetterMethod+"' calling for result invoke last operation.";
                                System.err.println(messageException);
                                throw new InvocationTargetException (e);
                            }
                            fastMethodList.add(getterDtoInsideObject);
                            fastMethodList.add(fastSetterMethod);
                        }
                    }
                    methodsDTOEntity.put(aClass, fastMethodList);
                } else {
                    for (Method setterMethod : aClass.getMethods()) {
                        String methodName = setterMethod.getName();
                        FastMethod getterDto = methodsGetter.get(methodName.replaceFirst("set", "get"));
                        if (getterDto != null && methodName.length() > 3 && "set".equals(methodName.substring(0, 3))
                                && !methodName.substring(3, 4).toLowerCase().equals(methodName.substring(3, 4))) {
                            try {
                                destFastClass.getMethod(setterMethod).invoke(arg, new Object[]{getterDto.invoke(this, null)});
                            } catch (InvocationTargetException e) {
                                String messageException = "AUTOMAPPING EXCEPTION. Method '"+getterDto+"' calling for object '" + this +
                                        "', and method '"+destFastClass.getMethod(setterMethod)+"' calling for result invoke last operation.";
                                System.err.println(messageException);
                                throw new InvocationTargetException (e);
                            }
                            FastMethod fastSetter = destFastClass.getMethod(setterMethod);
                            methodsDTOEntityM.put(aClass, new FastMethod[]{getterDto, fastSetter});
                        }
                    }
                }

            }
            synchronized (METHODS_DTO_ENTITYm) {
                METHODS_DTO_ENTITYm.put(dtoClass, methodsDTOEntityM);
            }
            synchronized (METHODS_DTO_ENTITY) {
                METHODS_DTO_ENTITY.put(dtoClass, methodsDTOEntity);
            }

        } else {
            HashMap<Class, ArrayList<FastMethod>> methodsDTOEntity = METHODS_DTO_ENTITY.get(dtoClass);
            HashMap<Class, FastMethod[]> methodsDTOEntityM = METHODS_DTO_ENTITYm.get(dtoClass);

            for (Object arg : args) {
                Class<?> aClass = arg.getClass();

                if (methodsDTOEntity != null) {
                    ArrayList<FastMethod> fastMethodList = methodsDTOEntity.get(aClass);
                    if (fastMethodList != null) {
                        Object invoke = fastMethodList.get(0).invoke(this, null);
                        for (int i = 1; i < fastMethodList.size(); i += 2) {
                            Object result = fastMethodList.get(i).invoke(invoke, null);
                            if (result != null) {
                                fastMethodList.get(i + 1).invoke(arg, new Object[]{result});// 0 and false not detect
                            }                                //if (result != null && !result.toString().equals("0") && !result.toString().equals("false")) fastMethodList.get(i + 1).invoke(arg, new Object[]{result});
                        }
                    }
                }

                if (methodsDTOEntityM != null) {
                    FastMethod[] fastMethodArray = methodsDTOEntityM.get(aClass);
                    if (fastMethodArray != null) {
                        Object result = fastMethodArray[0].invoke(this, null);
                        if (result != null) {
                            fastMethodArray[1].invoke(arg, new Object[]{result}); // 0 and false not detect
                        }                            //if (result != null && !result.toString().equals("0") && !result.toString().equals("false")) fastMethodArray[1].invoke(arg, new Object[]{result});
                    }
                }
            }

        }

    }

    /*
    public default  void addRuleMapingToDTO (){}
    
    public default  void addRuleMapingFromDTO (){}
     */
}
