package com.shzz.common.tool.bean.transform.asm;

import com.shzz.common.tool.bean.BeanFieldInfo;
import com.shzz.common.tool.bean.transform.BeanTransform;
import com.shzz.common.tool.bean.transform.ExtensionObjectTransform;
import com.shzz.common.tool.bean.transform.SystemProperties;
import com.shzz.common.tool.bean.transform.Transform;
import com.shzz.common.tool.bean.transform.asm.context.Context;
import com.shzz.common.tool.bean.transform.asm.strategy.*;
import com.shzz.common.tool.bean.transform.asm.context.TransformTypeContext;
import com.shzz.common.tool.code.BeanTransformException;
import com.shzz.common.tool.bean.transform.asm.strategy.StrategyMode;
import org.objectweb.asm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.shzz.common.tool.bean.transform.asm.BeanTransformsMethodAdapter.*;
import static com.shzz.common.tool.bean.transform.asm.strategy.CollectionTypeStrategy.ELEMENT_TRANSFORM_MEDIAN;
import static org.objectweb.asm.Opcodes.*;

/**
 * @Classname TransformUtilGenerate
 * @Description TODO
 * @Date 2021/11/7 20:44
 * @Created by wen wang
 */
public class TransformUtilGenerate {

    private static final Logger LOG = LoggerFactory.getLogger("TransformUtilGenerate");

    public static final String SUPER_CLASS_INTERNAL_NAME = Type.getInternalName(BeanTransFormsHandler.class);
    public static final String OBJECT_CLASS_INTERNAL_NAME = Type.getInternalName(Object.class);
    public static final String EXTENSION_TRANSFORM_CLASS_INTERNAL_NAME = Type.getInternalName(ExtensionObjectTransform.class);
    public static final String BEAN_TRANSFORM_IMPL_CLASS_NAME_PREFIX = "com.shzz.common.tool.bean.transform.asm.BeanTransForms";
    public static final String EXTEND_TRANSFORM_IMPL_CLASS_NAME_PREFIX = "com.shzz.common.tool.bean.transform.asm.ExtendTransform";
    public static final String TRANSFORM_METHOD_NAME = "beanTransforms";
    public static final String EXTEND_TRANSFORM_METHOD_NAME = "extensionObjectTransform";
    public static final String EXTEND_IMPL_FIELD_NAME_SUFFIX = "_convert";
    public static final String TARGET_FIELD_CLASS_FIELD_SUFFIX = "_targetType";
    public static final String SOURCE_FIELD_CLASS_FIELD_SUFFIX = "_sourceType";
    public static final String EXTEND_TRANSFORM_METHOD_DESC = Type.getMethodDescriptor(
            Type.getType(Object.class),
            Type.getType(Object.class),
            Type.getType(boolean.class)
    );

    public static final String TRANSFORM_METHOD_DESC = Type.getMethodDescriptor(
            Type.getType(Object.class),
            Type.getType(Class.class),
            Type.getType(Object.class),
            Type.getType(Class.class)
    );


    //    private final Map<String, Map<String, ExtensionObjectTransform>> extensionObjectTransformMap = new ConcurrentHashMap<>();
//    private final Map<String, Map<String, BeanTransFormsHandler>> beanTransFormsHandlerMap = new ConcurrentHashMap<>();
//    private final Map<String, Map<String, Class>> fieldClassMap = new ConcurrentHashMap<>();
    public static final Map<StrategyMode, Class<? extends ComplexTypeStrategy>> strategy = new ConcurrentHashMap<>();

    private TransformUtilGenerate() {

    }

    public static <S, T> BeanTransform generate(Class<S> sourceBeanClass, Class<T> targetClass, List<ExtensionObjectTransform> extendsTransformList) throws Exception {
        return generate(sourceBeanClass, targetClass, false, true, extendsTransformList, null);
    }


    public static <S, T> BeanTransform generate(Class<S> sourceBeanClass, Class<T> targetClass, boolean isDeepCopy) throws Exception {
        return generate(sourceBeanClass, targetClass, isDeepCopy, true, null, null);
    }

    public static <S, T> BeanTransform generate(Class<S> sourceBeanClass, Class<T> targetClass, boolean isDeepCopy, List<ExtensionObjectTransform> extendsTransformList) throws Exception {
        return generate(sourceBeanClass, targetClass, isDeepCopy, true, extendsTransformList, null);
    }

    public static <S, T> BeanTransform generate(Class<S> sourceBeanClass, Class<T> targetClass, boolean isDeepCopy, boolean permitBaseTypeInterconvert, List<ExtensionObjectTransform> extendsTransformList) throws Exception {
        return generate(sourceBeanClass, targetClass, isDeepCopy, permitBaseTypeInterconvert, extendsTransformList, null);
    }

    public static <S, T> BeanTransform generate(Class<S> sourceBeanClass, Class<T> targetClass, boolean isDeepCopy, boolean permitBaseTypeInterconvert, List<ExtensionObjectTransform> extendsTransformList, java.lang.reflect.Type[] actualGenericType) throws Exception {
        /**
         * @description:
         * @param sourceBeanClass
         * @param targetClass
         * @param isDeepCopy
         * @param permitBaseTypeInterconvert
         * @param extendsTransformList
         * @param actualGenericType
         * @return: com.shzz.common.tool.bean.transform.asm.BeanTransFormsHandler
         * @auther: wen wang
         * @date: 2021/12/8 13:59
         */

        UniversalClassTypeStrategy universalClassTypeStrategy = new UniversalClassTypeStrategy();
        return universalClassTypeStrategy.generate(sourceBeanClass, targetClass, isDeepCopy, permitBaseTypeInterconvert, extendsTransformList, actualGenericType);

    }

    public static String classSimpleNameReconstruct(Class rawType) {

        if (rawType.isArray()) {
            return "ArrayComponent";
        } else {
            return rawType.getSimpleName();
        }

    }

    public static Class loadASMGenerateClass(byte[] bytes, String generateClassname) throws Exception {
        //将二进制流写到本地磁盘上
        FileOutputStream fos = null;
        int lastIndex = generateClassname.lastIndexOf(".");
        String packageTopath=generateClassname.substring(0,lastIndex).replace(".",File.separator);
        String className = generateClassname.substring(lastIndex + 1);
        //String fullPath=TransformUtilGenerate.class.getResource("/")+File.separator+packageTopath+File.separator+className + ".class";
        String fullPath=System.getProperty("user.dir")+File.separator+"generate"+File.separator+packageTopath+className + ".class";

        CustomeClassLoader customeClassLoader = new CustomeClassLoader();

        Class geneImplClass = null;
        try {


             // 创建类元信息

            CustomeClassLoader.putClassByte(generateClassname, bytes);
            geneImplClass = customeClassLoader.loadClass(generateClassname);
            File classFile = new File(fullPath);
            // 写到本地文件
            if (SystemProperties.getClassOutputFlag()) {
               if(!classFile.getParentFile().exists()){

                   classFile.getParentFile().mkdirs();
               }
               // classFile.createNewFile();
                fos = new FileOutputStream(classFile);
                fos.write(bytes);
                fos.close();
                LOG.info("ASM generate class file directory is: "+classFile.getParentFile().getPath());
            }

        } catch (IOException e) {
            LOG.error(e.toString());
        }finally {
            if(Objects.nonNull(fos)){
                fos.close();
            }
        }
        return geneImplClass;
    }

    public static boolean checkGenerateClassname(String generateClassname) throws Exception {

        if (Objects.isNull(generateClassname)) {
            //todo  错误枚举后续完善
            throw new BeanTransformException("0x0fff", "请传入正确类名", "创建类名称为空");
        }
        //todo 异常检测
        return true;
    }

}
