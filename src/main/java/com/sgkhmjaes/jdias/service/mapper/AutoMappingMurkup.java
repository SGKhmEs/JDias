
package com.sgkhmjaes.jdias.service.mapper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.cglib.reflect.FastMethod;

public class AutoMappingMurkup {
    
    private static final HashMap <Class, HashMap <FastMethod, AutoMappingMurkup>> ENTITY_TO_DTO  =  new HashMap<>();
    private static final HashMap <Class, ArrayList <Class>> METHODS_ENTITY_DTO_CLASS_LIST  =  new HashMap<>();
    
    private final FastMethod fastMethodSource;
    private final FastMethod fastMethodDest;
    private final Class aClass;
    
    public void xxx (Object arg, Object dto) throws InvocationTargetException {
        
        if (this.fastMethodDest == null) fastMethodSource.invoke(dto, new Object[]{arg});
        else fastMethodSource.invoke(dto, new Object[]{fastMethodDest.invoke(arg, null)});
    }
    
    public AutoMappingMurkup(FastMethod fastMethodSource, FastMethod fastMethodDest, Class aClass){
        this.fastMethodSource=fastMethodSource;
        this.fastMethodDest=fastMethodDest;
        this.aClass=aClass;
    }

    public Class getaClass() {
        return aClass;
    }
        
    public static boolean addEntityToDto (Class dtoClass, HashMap <FastMethod, AutoMappingMurkup> map) {
        if (ENTITY_TO_DTO.get (dtoClass) == null)  {
            synchronized (ENTITY_TO_DTO) {
                ENTITY_TO_DTO.put(dtoClass, map);
            }
            return true;
        }
        else return false;
    }
    
    public static HashMap <FastMethod, AutoMappingMurkup> getEntityToDto (Class classDto) {
        return ENTITY_TO_DTO.get(classDto);
    }
    
    public static boolean addClassList (Class dtoClass, ArrayList <Class> classList) {
        if (METHODS_ENTITY_DTO_CLASS_LIST.get (dtoClass) == null)  {
            synchronized (METHODS_ENTITY_DTO_CLASS_LIST) {
                METHODS_ENTITY_DTO_CLASS_LIST.put(dtoClass, classList);
            }
            return true;
        }
        else return false;
    }
    
    public static ArrayList <Class> getClassList (Class classDto) {
        return METHODS_ENTITY_DTO_CLASS_LIST.get(classDto);
    }
    
    /*
    public void setSetterGetterReletions (FastMethod setter, Class aClass) {
        AutoMappingMurkup autoMappingMurkup = new AutoMappingMurkup(setter, null, aClass);
        HashMap <FastMethod, AutoMappingMurkup> h = new HashMap <> ();
        h.put(setter, autoMappingMurkup);
    }
    
    public void setSetterGetterReletions (FastMethod setter, Class aClass, FastMethod getter) {
        AutoMappingMurkup autoMappingMurkup = new AutoMappingMurkup(setter, null, aClass);
        //this.fastMethodSource = setter;
        //this.fastMethodDest=getter;
        //this.aClass=aClass;
        //HashMap <FastMethod, AutoMappingMurkup> autoMappingMurkup = new HashMap <> ();
        //autoMappingMurkup.put(setter, autoMappingMurkup);
    }
    */

    
    
    @Override
    public String toString() {
        return "Fast method source = " + fastMethodSource + ", fast method dest = " + fastMethodDest + ", class = " +  aClass;
    }

    
    
    
}
