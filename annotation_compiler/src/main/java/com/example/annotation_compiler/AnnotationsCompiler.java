package com.example.annotation_compiler;

import com.example.annotations.BindView;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

/**
 * 注解处理器，需要先注册
 */
@AutoService(Processor.class)
public class AnnotationsCompiler extends AbstractProcessor {

    /**
     * 初始化三部曲
     * 1、支持java版本
     * 2、当前的APT能用来处理那些注解
     * 3、创建一个生成文件的对象
     */
    //支持java版本
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //当前的APT能用来处理那些注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> types = new HashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }

    //创建一个生成文件的对象
    Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
    }

    /**
     * 对注解的处理在这个方法中完成
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        /**
         * 这里面的代码可以用javapoet代替
         */
        /**
         * 类或者接口   TypeElement
         * 方法  ExecutableElement
         * 属性  VariableElement
         */
        Set<? extends Element> elemennts = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        Writer writer = null;
        Map<String, List<VariableElement>> map = new HashMap<>();
        for (Element element :elemennts) {
            VariableElement variableElement = (VariableElement) element;
            /**
             *获取类名
             * variableElement.getEnclosingElement()获取包裹这个注解的类
             */
            String activityName = variableElement.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> list = map.get(activityName);
            if(list == null){
                list = new ArrayList<>();
                map.put(activityName,list);
            }
            list.add(variableElement);

        }

        if (map.size() > 0){
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()){
                String name = it.next();
                List<VariableElement> list = map.get(name);
                /**
                 * 得到包名
                 */
                TypeElement typeElement = (TypeElement) list.get(0).getEnclosingElement();
                String pageName = processingEnv.getElementUtils().getPackageOf(typeElement).toString();
                /**
                 * 写入文件
                 */
                try {

                    /**
                     * 创建一个包名下面的name + "_ViewBinding"的类
                     */
                    JavaFileObject javaFileObject = mFiler.createSourceFile(pageName+"."+ name + "_ViewBinding");
                    writer = javaFileObject.openWriter();
                    writer.write("package " + pageName + ";\n");
                    writer.write("import " + pageName + ".IBinder;\n");
                    writer.write("public class "+ name + "_ViewBinding implements IBinder<" + pageName + "." + name +">{\n");
                    writer.write("@Override\n");
                    writer.write("public void bind(" + pageName +"."+name +" target) {\n");
                    for (VariableElement element:list) {
                        /**
                         * 得到控件的名字
                         */
                        String variableName = element.getSimpleName().toString();
                        /**
                         * 得到ID
                         */
                        int id = element.getAnnotation(BindView.class).value();
                        /**
                         * 得到类型
                         */
                        TypeMirror typeMirror = element.asType();
                        writer.write("target." + variableName+ " = (" +typeMirror +")target.findViewById(" + id+");\n");

                    }
                    writer.write("}\n}\n");
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (writer != null){
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }
}
