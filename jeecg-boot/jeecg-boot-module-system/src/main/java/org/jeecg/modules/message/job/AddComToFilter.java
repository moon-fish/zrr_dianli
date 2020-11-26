package org.jeecg.modules.message.job;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.dw.entity.ElectricityFilerB;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IElectricityFilerBService;
import org.jeecg.modules.dw.service.IUserInfoBService;
import org.jeecg.modules.system.controller.SysAnnouncementController;
import org.jeecg.modules.system.entity.SysAnnouncement;
import org.jeecg.modules.system.service.ISysAnnouncementService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 1.每月的1号，10号，20号 定时将公司信息添加到月申报表中
 * 2.定时发送信息
 */

@Slf4j
public class AddComToFilter implements Job {
    @Autowired
    private IUserInfoBService userInfoBService;
    @Autowired
    private IElectricityFilerBService electricityFilerBService;
    @Autowired
    private ISysAnnouncementService sysAnnouncementService;
    @Autowired
    private SysAnnouncementController sysAnnouncementController;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        log.info("定时任务开始-----------------------------");
        /*
         * 每月的1号，10号，20号 定时将公司信息添加到月申报表中
         */
        int date0 = 1;
        int date1 = 10;
        int date2 = 20;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar = Calendar.getInstance();
        String currentTime  = sdf.format(calendar.getTime()); //当前时间
        calendar.set(Calendar.DAY_OF_MONTH, date0);
        String oneTime = sdf2.format(calendar.getTime()); //本月1号的时间
        calendar.set(Calendar.DAY_OF_MONTH, date1);
        String tenTime = sdf2.format(calendar.getTime()); //本月10号的时间
        calendar.set(Calendar.DAY_OF_MONTH, date2);
        String twentyTime = sdf2.format(calendar.getTime()); //本月20号的时间
        if(currentTime.equals(oneTime) || currentTime.equals(tenTime) || currentTime.equals(twentyTime)){
            List<UserInfoB> list =  userInfoBService.list();
            List<ElectricityFilerB> efList = new ArrayList<ElectricityFilerB>();
            ElectricityFilerB efb = null;
            for(UserInfoB uib: list){
                efb = new ElectricityFilerB();
                efb.setFilerCompanyName(uib.getComAName());
                efList.add(efb);
            }
            electricityFilerBService.saveBatch(efList);
        }

        /*
         * 定时发送信息
         */
        int d1 = 9;
        int d2 = 19;
        int d3 = 29;
        calendar.set(Calendar.DAY_OF_MONTH, d1);
        String Time1 = sdf2.format(calendar.getTime()); //本月9号的时间
        calendar.set(Calendar.DAY_OF_MONTH, d2);
        String Time2 = sdf2.format(calendar.getTime()); //本月19号的时间
        calendar.set(Calendar.DAY_OF_MONTH, d3);
        String Time3 = sdf2.format(calendar.getTime()); //本月29号的时间
        if(currentTime.equals(Time1) || currentTime.equals(Time2) || currentTime.equals(Time3)){
            SysAnnouncement sa = new SysAnnouncement();
            sa.setTitile("-电量申报-");
            sa.setPriority("M");
            sa.setMsgCategory("1");
            sa.setMsgType("ALL");
//            sa.setCreateBy("admin");
//            sa.setUserIds("e9ca23d68d884d4ebb19d07889727dae,");
            sysAnnouncementController.add(sa);

            Result<List<SysAnnouncement>> r = sysAnnouncementController.queryall("","","","");
            String id = r.getResult().get(0).getId();
            sysAnnouncementController.doReleaseData(id);
        }


    }
}
