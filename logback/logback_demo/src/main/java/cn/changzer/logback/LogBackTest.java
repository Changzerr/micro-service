package cn.changzer.logback;

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
        Logger logger = LoggerFactory.getLogger("cn.itcast.logback.HelloWorld");
        logger.error("error ...");
        logger.warn("warn ...");
        logger.info("info ...");
        logger.debug("debug ...");
        //因为默认的输出级别为debug，所以这一条日志不会输出
        logger.trace("trace ...");
    }
}
