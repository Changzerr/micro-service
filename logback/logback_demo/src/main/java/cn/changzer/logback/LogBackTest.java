package cn.changzer.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lingqu
 * @date 2022/10/5
 * @apiNote
 */
public class LogBackTest {

    @Test
    public void test1(){
        Logger logger = LoggerFactory.getLogger("cn.changzer.logback.HelloWorld");
        //默认debug级别，从root logger 继承而来
        logger.trace("trace ...");
        logger.debug("debug ...");
        logger.info("info ...");
        logger.warn("warn ...");
        logger.error("error ...");
    }

    @Test
    public void test2(){
        Logger logger = LoggerFactory.getLogger("cn.changzer.logback.HelloWorld");
        //默认debug级别，从root logger 继承而来
        logger.trace("trace ...");
        logger.debug("debug ...");
        logger.info("info ...");
        logger.warn("warn ...");
        logger.error("error ...");

        LoggerContext iLoggerFactory = (LoggerContext)LoggerFactory.getILoggerFactory();
        StatusPrinter.print(iLoggerFactory);
    }

    /*
     * 日志输出级别：ERROR > WARN > INFO > DEBUG > TRACE
     * */

    //测试默认的日志输出级别
    @Test
    public void test3(){
        Logger logger = LoggerFactory.getLogger("cn.changzer.logback.HelloWorld");
        logger.error("error ...");
        logger.warn("warn ...");
        logger.info("info ...");
        logger.debug("debug ...");
        //因为默认的输出级别为debug，所以这一条日志不会输出
        logger.trace("trace ...");
    }

    //设置日志输出级别
    @Test
    public void test4(){
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cn.changzer.logback.HelloWorld");
        logger.setLevel(Level.WARN);
        logger.error("error ...");
        logger.warn("warn ...");
        logger.info("info ...");
        logger.debug("debug ...");
        logger.trace("trace ...");
    }

    //测试Logger的继承
    @Test
    public void test5(){
        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cn.changzer");
        logger.setLevel(Level.INFO);
        logger.error("error ...");
        logger.warn("warn ...");
        logger.info("info ...");
        logger.debug("debug ...");
        logger.trace("trace ...");

        // "cn.itcast.logback" 会继承 "cn.itcast" 的有效级别
        Logger barLogger = LoggerFactory.getLogger("cn.changzer.logback");
        // 这条日志会打印，因为 INFO >= INFO
        barLogger.info("子级信息");
        // 这条日志不会打印，因为 DEBUG < INFO
        barLogger.debug("子级调试信息");
    }

    //Logger获取，根据同一个名称获得的logger都是同一个实例
    @Test
    public void test6(){
        Logger logger1 = LoggerFactory.getLogger("cn.changzer");
        Logger logger2 = LoggerFactory.getLogger("cn.changzer");
        System.out.println(logger1 == logger2);
    }

    //参数化日志
    @Test
    public void test7(){
        Logger logger = LoggerFactory.getLogger("cn.changzer");
        logger.debug("hello {}","world");
    }
}
