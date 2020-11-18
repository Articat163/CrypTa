package ru.articat.crypta.Util;

public class MyLog {

    /**
     *  Метод работает (переход из LogCat в код), но потребляем слишком много памяти. Вылетает по OutOfMemory иногда
     */


//    public static void logi(final String tag, final String msg) {
//        final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
//
//        String fileName = stackTrace.getFileName();
//        if (fileName == null) fileName="";  // It is necessary if you want to use proguard obfuscation.
//
//        final String info = stackTrace.getMethodName() + " (" + fileName + ":"
//                + stackTrace.getLineNumber() + ")";
//        Log.i(tag, info + ": " + msg);
//    }

//    public static void logd((final String tag, final String msg) {
//        final StackTraceElement stackTrace = new Exception().getStackTrace()[1];
//
//        String fileName = stackTrace.getFileName();
//        if (fileName == null) fileName="";  // It is necessary if you want to use proguard obfuscation.
//
//        final String info = stackTrace.getMethodName() + " (" + fileName + ":"
//                + stackTrace.getLineNumber() + ")";
//        Log.d(tag, info + ": " + msg);
//    }


}
