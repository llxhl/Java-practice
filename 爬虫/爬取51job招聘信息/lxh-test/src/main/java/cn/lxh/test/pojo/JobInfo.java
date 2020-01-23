package cn.lxh.test.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JobInfo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;//自增主键
        private String companyName;
        private String companyAddr;
        private String companyInfo;
        private String jobName;//职位名称
        private String jobInfo;
        private String salary;//工资
        private String url;//职位详情的url地址
        private String time;//职位发布的时间

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyAddr() {
            return companyAddr;
        }

        public void setCompanyAddr(String companyAddr) {
            this.companyAddr = companyAddr;
        }

        public String getCompanyInfo() {
            return companyInfo;
        }

        public void setCompanyInfo(String companyInfo) {
            this.companyInfo = companyInfo;
        }

        public String getJobName() {
            return jobName;
        }

        public void setJobName(String jobName) {
            this.jobName = jobName;
        }

        public String getJobInfo() {
            return jobInfo;
        }

        public void setJobInfo(String jobInfo) {
            this.jobInfo = jobInfo;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "JobInfo{" +
                    "id=" + id +
                    ", companyName='" + companyName + '\'' +
                    ", companyAddr='" + companyAddr + '\'' +
                    ", companyInfo='" + companyInfo + '\'' +
                    ", jobName='" + jobName + '\'' +
                    ", jobInfo='" + jobInfo + '\'' +
                    ", salary='"+salary+'\''+
                    ", url='" + url + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

