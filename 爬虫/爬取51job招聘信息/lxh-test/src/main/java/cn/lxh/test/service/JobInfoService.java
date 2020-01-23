package cn.lxh.test.service;

import cn.lxh.test.pojo.JobInfo;

import java.util.List;

public interface JobInfoService {
    /**
     * 保存工作信息
     * @param jobInfo
     */
    public void save(JobInfo jobInfo);

    /**
     * 根据条件查询工作信息
     * @param jobInfo
     * @return
     */
    //     返回结果集                查询条件
    public List<JobInfo> findJobInfo(JobInfo jobInfo);
}
