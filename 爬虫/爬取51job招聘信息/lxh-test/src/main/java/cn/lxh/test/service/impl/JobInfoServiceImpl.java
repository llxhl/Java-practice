package cn.lxh.test.service.impl;

import cn.lxh.test.dao.JobInfoDao;
import cn.lxh.test.pojo.JobInfo;
import cn.lxh.test.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
//实现JobInfoService接口
public class JobInfoServiceImpl implements JobInfoService {
    @Autowired//注入Dao
    private JobInfoDao jobInfoDao;

    //保存
    @Override
    //save对数据库进行写操作，加事务注解
    @Transactional
    public void save(JobInfo jobInfo) {
        //根据url和发布时间查询数据
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        //执行查询，查询param
        List<JobInfo> list = this.findJobInfo(param);
        //判断查询结果是否为空
        if(list.size()==0) {
            //为空，说明招聘信息数据不存在数据库中，或者已经更新，需要新增或者更新数据库
            this.jobInfoDao.saveAndFlush(jobInfo);

        }
    }
    //根据条件查询
    @Override
    public List<JobInfo> findJobInfo(JobInfo jobInfo) {
        //设置查询条件
        Example example=Example.of(jobInfo);
        //根据example执行查询
        List list=this.jobInfoDao.findAll(example);
        return list;
    }
}
