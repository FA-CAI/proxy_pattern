package com.shangxue.proxy_pattern.dynamicProxy.handwritingjdkproxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**

 * @description:模拟jdk动态代理，用来生成源代码

  摘自com.shangxue.proxy_pattern.dynamicProxy.jdkproxy.Test
        //jdk动态代理的原理————
                //1.拿到被代理对象的引用，并且获取到它的所有的接口（通过反射获取）
                //2. JDK Proxy类重新生成一个新的类，同时新的类要实现被代理类所有实现的接口(不能漏被代理类的东西呀。所以1里要也获取它实现的接口~)
                //3.动态生成java代码，把新加的业务逻辑方法由一定的逻辑代码去调用（在代码中体现）  【运行时才干的这些事，也就是动态。通过反射。】
                //4.编译新生成的java代码（多诡异多牛逼，还真能这么干啊），变成.class
                //5.在重新加载到JVM中运行
        //以上这个过程就叫做字节码重组。也就是手写jdk代理需要：自己用（我写）代码来写（增强类/代理类）代码，用代码来编译代码，用代码来重新加载代码，用代码来运行代码。

 */
public class ZKProxy {
    public static final String ln = "\r\n";

    public static Object newProxyInstance(ZKClassLoader zkClassLoader, Class<?>[] interfaces, ZKMeipo zkMeipo) {
        try{
            //1、动态生成源代码 .java文件。用String类型接收
            String src = generateSrc(interfaces);  //动态生成源代码。为了代码整洁(有种可以分模块，然后其他人可以单独复制模块["微服务"的思想也是这，哈哈哈，思想都是通的，是贯穿编程代码的微观宏观的]，可复用的感觉)，抽取出来了
          //TODO D:\BaiduNetdiskDownload\咕泡学院二期\02 源码分析专题\01 Spring中常见设计模式\录播视频\03.深度分析代理模式 下 00:21min:21s

            //2、Java文件输出磁盘
            String filePath = ZKProxy.class.getResource("").getPath();  //输出到ZKProxy所在文件夹。但我取了中文，所以这边暂时输出到桌面吧。待把项目放到全英文，现在中文路径乱码好烦
           // String filePath ="C:\\Users\\Administrator\\Desktop\\";  //不能弄成桌面，会影响等会加载就要写复杂了...绝对路径好烦啊
            File f = new File(filePath + "$Proxy0.java");  //写的是增强的（代理类的）源代码.java
            FileWriter fw = new FileWriter(f);
            fw.write(src);
            fw.flush();
            fw.close();

            //3、把生成的源代码.java文件编译成字节码.class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manage = compiler.getStandardFileManager(null,null,null);
            Iterable iterable = manage.getJavaFileObjects(f);
                         //编译任务
            JavaCompiler.CompilationTask task = compiler.getTask(null,manage,null,null,null,iterable);
            task.call();
            manage.close();

            //4、编译生成的.class文件加载到JVM中来  类加载器加载
            Class proxyClass =  zkClassLoader.findClass("$Proxy0"); //我上面路径写了桌面，那这里不能相对路径了
          //  Class proxyClass =  zkClassLoader.findClass("C:\\Users\\Administrator\\Desktop\\$Proxy0");
            Constructor c = proxyClass.getConstructor(ZKInvocationHandler.class);

            f.delete(); //（在运行时）把生成的代理类的源代码默默地删掉（那为什么不一开始就不要输出到硬盘啊，摔(╯‵□′)╯︵┴─┴ ），完美地还原jdk动态代理的效果。

            //5、返回字节码重组以后的新的代理对象
            return c.newInstance(zkMeipo);


        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        //当做是在用记事本写代码
        sb.append("package com.shangxue.proxy_pattern.dynamicProxy.handwritingjdkproxy;" + ln);
        sb.append("import com.shangxue.proxy_pattern.Person;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        //有点在写垃圾springjdbc的感觉了。
        //实际接口应该有多个，是要for循环的，但是这边简便起见
        sb.append("public class $Proxy0 implements " + interfaces[0].getName() + "{" + ln);
                 //
                 sb.append("ZKInvocationHandler h;" + ln);
                 //构造方法
                 sb.append("public $Proxy0(ZKInvocationHandler h) { " + ln);
                 sb.append("this.h = h;");
        sb.append("}" + ln);
        //实现接口的方法（一个接口里可能有很多方法，所以用for循环） jdk代理类的反编译后源代码就是这么干的
        for (Method m : interfaces[0].getMethods()){

            //？？？这是在干嘛，讲师没做这个
                 //参数类型的数组
                 Class<?>[] params = m.getParameterTypes();
                 //存储 参数名 的sb
                 StringBuffer paramNames = new StringBuffer();
                 //存储 参数值 的sb
                 StringBuffer paramValues = new StringBuffer();
                 //存储 参数Class对象？ 的sb
                 StringBuffer paramClasses = new StringBuffer();
                 for (int i = 0; i < params.length; i++) {
                     Class clazz = params[i];
                     String type = clazz.getName();
                     String paramName = toLowerFirstCase(clazz.getSimpleName());
                     paramNames.append(type + " " +  paramName);
                     paramValues.append(paramName);
                     paramClasses.append(clazz.getName() + ".class");
                     if(i > 0 && i < params.length-1){
                         paramNames.append(",");
                         paramClasses.append(",");
                         paramValues.append(",");
                     }
                 }

                 sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(" + paramNames.toString() + ") {" + ln);
                 sb.append("try{" + ln);
                 sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{" + paramClasses.toString() + "});" + ln);
                 sb.append((hasReturnValue(m.getReturnType()) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + paramValues + "})",m.getReturnType()) + ";" + ln);
                 sb.append("}catch(Error _ex) { }");
                 sb.append("catch(Throwable e){" + ln);
                 sb.append("throw new UndeclaredThrowableException(e);" + ln);
                 sb.append("}");
                 sb.append(getReturnEmptyCode(m.getReturnType()));
                 sb.append("}");
        }
        sb.append("}" + ln);
        return sb.toString();
    }

    private static String getCaseCode(String code, Class<?> returnType) {
        if(mappings.containsKey(returnType)){
            return "((" + mappings.get(returnType).getName() +  ")" + code + ")." + returnType.getSimpleName() + "Value()";
        }
        return code;
    }

    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnType) {
        if(mappings.containsKey(returnType)){
            return "return 0;";
        }else if(returnType == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static boolean hasReturnValue(Class<?> returnType) {
        return returnType != void.class;
    }

    private static String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
