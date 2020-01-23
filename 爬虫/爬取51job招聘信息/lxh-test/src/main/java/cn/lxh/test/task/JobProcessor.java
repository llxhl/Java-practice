package cn.lxh.test.task;

import cn.lxh.test.pojo.JobInfo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

//解析页面
@Component//普通pojo
public class JobProcessor implements PageProcessor {
    private String url = "https://search.51job.com/list/000000,000000,0000,32%252C01,9,99,java,2,1.html?lang=c&stype=" +
            "&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=" +
            "0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";

    //解析页面，抽取逻辑
    @Override
    public void process(Page page) {
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();
        if(list.size()==0) {
            this.saveJobInfo(page);
        }else {
            //如果不为空，表示这是列表页，解析出详情页的url地址，放到队列中
            for (Selectable selectable :list){
                //获取url地址
                String jobInfoUrl = selectable.links().toString();
                //System.out.println(jobInfoUrl);
                //把获取到的url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);

            }
            //获取下一页的url
            String bkurl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            // System.out.println(bkurl);
            //把url放到任务队列中
            page.addTargetRequest(bkurl);
        }


        String html=page.getHtml().toString();
        // System.out.println(123);
    }
    //解析页面，获取招聘详细信息，保存数据
    private void saveJobInfo(Page page) {
        //创建招聘详情对象
        JobInfo jobInfo=new JobInfo();
        //解析页面
        Html html=page.getHtml();
        //获取数据，封装到对象中
        jobInfo.setCompanyName(html.css("div.cn p.cname a","text").toString());
        jobInfo.setCompanyAddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text());
        jobInfo.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text());
        jobInfo.setJobName(html.css("div.cn h1","text").toString());
        jobInfo.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
        jobInfo.setUrl(page.getUrl().toString());
        jobInfo.setSalary(html.css("div.cn strong","text").toString());
        //获取发布时间
        String time = Jsoup.parse(html.css("div.cn p.msg").regex(".*发布").toString()).text();
        //这里应该有专门去特殊字符的功能 = =
        jobInfo.setTime(time.substring(time.length()-7,time.length()-2));
        //把结果保存起来(内存)到ItemResults里
        page.putField("jobinfo",jobInfo);



    }
    private Site site=Site.me()
            .setCharset("gbk")//设置编码
            .setTimeOut(10*1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3);//设置重试的次数
    @Override
    public Site getSite() {
        return site;
    }
    @Autowired
    private SpringDataPipeline springDataPipeline;
    //当任务启动后任务隔多久执行方法，每间隔多长时间执行方法
    //fixedDelay每隔多久执行方法
    @Scheduled(initialDelay = 1000,fixedDelay = 100*1000)
    public void process(){
        Spider.create(new JobProcessor())
                .addUrl(url)//传请求的地址 起始地址
                //放在内存队列中
                //用布隆过滤器去重 使用内存队列保存待抓取url 最大对10000条数据进行去重操作
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)))
                .thread(10)
                .addPipeline(this.springDataPipeline)//输出
                .run();
    }
}
