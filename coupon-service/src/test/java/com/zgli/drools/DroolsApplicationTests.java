package com.zgli.drools;

import com.zgli.drools.model.Customer;
import com.zgli.drools.model.Order;
import com.zgli.drools.model.OrderItem;
import com.zgli.drools.service.CustomerService;
import org.apache.commons.collections4.ListUtils;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DroolsApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    CustomerService service;

    @Test
    public void testDiscount() {
        Customer customer1 = new Customer("Frank");
        customer1.setAge(4);

        Customer customer2 = new Customer("John");
        customer2.setAge(1);

        service.insertCustomer(customer1);
        service.insertCustomer(customer2);

        assertEquals(25, customer1.getDiscount());
        assertEquals(15, customer2.getDiscount());
    }

    /**
     * 测试点：决策表 先翻译成drl 后执行
     */
    @Test
    public void excel1() {
//        //把excel翻译成drl文件
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        Resource resource = ResourceFactory.newClassPathResource("rules/t.xlsx", "UTF-8");
        String drl = compiler.compile(resource, "Sheet1");
        System.out.println(drl);
        Long start = System.currentTimeMillis();        //执行决策表
        try {
            // load up the knowledge base
            //
            BigDecimal b = new BigDecimal("1.2");
            BigDecimal a = new BigDecimal("1.2");
            a.add(b).intValue();
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("exceltest");
            Customer customer1 = new Customer("Frank");
            customer1.setAge(4);

            Customer customer2 = new Customer("John");
            customer2.setAge(1);


            kSession.insert(customer1);
            kSession.fireAllRules();
            kSession.dispose();
            System.out.println("Allowed discount John: " + customer1.getDiscount());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.out.println("COST:" + String.valueOf(System.currentTimeMillis() - start));
    }

    @Test
    public void test() {
        // 构建KieSession, 这部分写法是固定的
        KieServices kieServices = KieServices.Factory.get();
        Resource dt = ResourceFactory.newClassPathResource("rules/t.drl", getClass());
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieSession kieSession = kieServices.newKieContainer(kieModule.getReleaseId()).newKieSession();


        Customer customer1 = new Customer("Frank");
        customer1.setAge(4);
        Customer customer2 = new Customer("Frank222");
        customer2.setAge(4);


        // 将要过规则的类传进 kieSession 中
        kieSession.insert(customer1);
        kieSession.insert(customer2);
        // 调用 fireAllRules() 方法进行规则处理，同时可以得知走了几个规则
        int count = kieSession.fireAllRules();
        System.out.println("总共触发了: " + count + " 条规则");
        System.out.println(customer1);
        System.out.println(customer2);
        System.out.println("\'" + System.getProperty("line.separator") + "\'");
    }

    // 使用字符串来测试
    @Test
    public void drlSyntaxTest() {
        String str = "";
        str += "package rules\n";
        str += "global java.util.List list\n";
        str += "rule rule1\n";
        str += "when\n";
        str += "eval(true) && Integer(intValue > 2)\n";
        str += "then\n";
        str += "System.out.println(\"动态加载的规则被触发了\");list.add(1);\n ";
        str += "end\n";
        str += "\n";

        Map<String, String> drls = new HashMap<String, String>();
        drls.put("rule1", str);

        KieHelper kieHelper = new KieHelper();
        for (String ruleId : drls.keySet()) {
            kieHelper.addContent(drls.get(ruleId).replaceAll("\n", System.getProperty("line.separator")), ResourceType.DRL);
        }
        Results results = kieHelper.verify();
        for (Message message : results.getMessages()) {
            System.out.format(">> Message (%s): {%s}", message.getLevel(), message.getText());
        }

        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("There are errors in the KB.");
        }

        KieSession ksession = kieHelper.build().newKieSession();
        ksession.insert(1);
        ksession.insert(3);
        ksession.insert(4);
        List<Integer> list = new ArrayList<>();
        ksession.setGlobal("list", list);
        ksession.fireAllRules();
        for (Integer key : list) {
            System.out.println(key);
        }
    }

    //优惠券测试
    @Test
    public void couponTest() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:rules/1101/rule-01.drl");
        String ruleText = null;
        try {
            //读取文件规则，存储到字符串中进行规则的加载，同时可以把规则从数据库中提取出来
            ruleText = getFileAsText(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(ruleText, ResourceType.DRL);
        Order order1 = new Order();
        OrderItem orderItem1 = new OrderItem(1, 1101, 299d, 299d, "1101", "2201", 1);
        OrderItem orderItem2 = new OrderItem(2, 1101, 399d, 399d, "1102", "2202", 1);
        OrderItem orderItem3 = new OrderItem(3, 1101, 399d, 399d, "1103", "2203", 1);
        OrderItem orderItem4 = new OrderItem(4, 1101, 399d, 399d, "1104", "2204", 1);
        order1.getItemList().add(orderItem1);
        order1.getItemList().add(orderItem2);
        order1.getItemList().add(orderItem3);
        order1.getItemList().add(orderItem4);
        KieSession kSession = kieHelper.build().newKieSession();
        for (OrderItem item : order1.getItemList()) {
            kSession.insert(item);
        }
        kSession.insert(order1);
        int count = kSession.fireAllRules();
        System.out.println("总共触发了: " + count + " 条规则");
        System.out.println(order1);
//       kSession.dispose();
    }

    private static String getFileAsText(final File file) throws IOException {
        System.out.println("filepath: " + file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("filepath: " + file.getAbsolutePath());
            return null;
        }
        final BufferedReader in = new BufferedReader(new FileReader(file));
        String ret = "";
        String line = null;
        while ((line = in.readLine()) != null) {
            ret += line + System.getProperty("line.separator");
        }
        in.close();

        Integer t = new Integer(1);
        return ret;
    }
}
