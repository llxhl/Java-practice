package cn.lxh.test.task;

import cn.lxh.test.pojo.JobInfo;
import cn.lxh.test.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class SpringDataPipeline implements Pipeline {
@Autowired
private JobInfoService jobInfoService;
    @Override
    public void process(ResultItems resultItems, Task task) {
        JobInfo jobInfo=resultItems.get("jobinfo");
        if(jobInfo!=null){
            //保存到数据库中
            this.jobInfoService.save(jobInfo);
        }
    }
}

