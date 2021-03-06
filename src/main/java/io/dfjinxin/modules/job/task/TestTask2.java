/**
 * 2019 东方金信
 *
 *
 *
 *
 */

package io.dfjinxin.modules.job.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 *
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component("testTask0406")
public class TestTask2 implements ITask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(String params) {
		logger.info("new a job,just test0406");
	}
}
