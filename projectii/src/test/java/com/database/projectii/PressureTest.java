package com.database.projectii;

import com.database.projectii.model.Staff;
import com.database.projectii.service.impl.StaffServiceImpl;
import java.util.Random;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PressureTest {
    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Autowired
    private StaffServiceImpl staffServiceimpl;

    @Test
    /*
     * @PerfTest
     * invocations 执行次数
     * thread 线程数目
     * duration 执行测试时长
     * @Required
     * max 执行最大
     * average 执行平均时长
     * totalTime 执行总时长
     * throughput 每秒执行测试数目
     * percentile “66:  ,99:  ” 66%测试不超过多少秒, 99%的测试不超过多少秒
     */
    @PerfTest(invocations = 10000, threads = 100000)
    @Required(max = 100000, average = 20000, totalTime = 6000000)
    public void test() {
        Random random = new Random();
        Staff staff = new Staff(random.nextInt(999) + 1, null, null, null, null, null, null, null);
        System.out.println(staffServiceimpl.selectStaffByAny(staff));
    }
}
