package com.education.paper.service.impl;

import com.education.paper.entity.Paper;
import com.education.paper.entity.QuestionScore;
import com.education.paper.service.YoukeService;
import com.education.paper.util.ExcelUtil;
import com.education.paper.util.Jisuan;
import com.education.paper.util.ScoreSection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Service
public class YoukeServiceImpl implements YoukeService {

    /**导入Excel中学生成绩*/
    @Override
    public boolean excelInfoImport(String name, MultipartFile file,HttpServletRequest request) {
        boolean b = false;
        //创建处理EXCEL
        ExcelUtil excelUtil = new ExcelUtil();
        //解析excel，获取题目数目集合。
        List<QuestionScore> questionScoreList = excelUtil.getExcelInfo(name ,file,request);

        if (questionScoreList!=null){
            b = true;
        }
        //System.out.println(b);

        //对成绩进行计算
        HttpSession session = request.getSession();

        QuestionScore questionScore = questionScoreList.get(0);
        //String a11 = questionScore.getScore()[0];
        //session.setAttribute("a",a11);
        BigDecimal bd1 = null;//小数点位数限制
        BigDecimal bd2 = null;//小数点位数限制
        BigDecimal bd3 = null;//小数点位数限制
        Paper paper = new Paper();
        Jisuan jisuan = new Jisuan();
        ScoreSection scoreSection = new ScoreSection();
        float[] sum = jisuan.sumRow(questionScoreList,request);
        float high = jisuan.scoreHigh(sum);
        float low = jisuan.scoreLowest(sum);
        float rate_pass = jisuan.scoreRatePass(sum);
        bd3 = new BigDecimal(rate_pass);
        bd3 = bd3.setScale(2,4);//保留两位小数，四舍五入
        rate_pass = bd3.floatValue();
        float score_average = jisuan.scoreaverage(sum);
        bd1 = new BigDecimal(score_average);
        bd1 = bd1.setScale(2,4);//保留两位小数，四舍五入
        score_average = bd1.floatValue();
        float score_variance = jisuan.scoreAverVariance(sum,score_average);
        bd2 = new BigDecimal(score_variance);
        bd2 = bd2.setScale(2,4);//保留两位小数，四舍五入
        score_variance = bd2.floatValue();
        paper.setScore_high(high);
        paper.setScore_lowest(low);
        paper.setRate_pass(rate_pass);
        paper.setScore_average(score_average);
        paper.setScore_variance(score_variance);
        /*分数段的人数和百分比*/
        int people0to9 = (int)scoreSection.score0to9(sum)[0];
        float per0to9 = scoreSection.score0to9(sum)[1];
        BigDecimal bdp0 = new BigDecimal(per0to9);
        bdp0 = bdp0.setScale(2,4);//保留两位小数，四舍五入
        per0to9 = bdp0.floatValue();
        int people10to19 = (int)scoreSection.score10to19(sum)[0];
        float per10to19 = scoreSection.score10to19(sum)[1];
        BigDecimal bdp1 = new BigDecimal(per10to19);
        bdp1 = bdp1.setScale(2,4);//保留两位小数，四舍五入
        per10to19 = bdp1.floatValue();
        int people20to29 = (int)scoreSection.score20to29(sum)[0];
        float per20to29 = scoreSection.score20to29(sum)[1];
        BigDecimal bdp2 = new BigDecimal(per20to29);
        bdp2 = bdp2.setScale(2,4);//保留两位小数，四舍五入
        per20to29 = bdp2.floatValue();
        int people30to39 = (int)scoreSection.score30to39(sum)[0];
        float per30to39 = scoreSection.score30to39(sum)[1];
        BigDecimal bdp3 = new BigDecimal(per30to39);
        bdp3 = bdp3.setScale(2,4);//保留两位小数，四舍五入
        per30to39 = bdp3.floatValue();
        int people40to49 = (int)scoreSection.score40to49(sum)[0];
        float per40to49 = scoreSection.score40to49(sum)[1];
        BigDecimal bdp4 = new BigDecimal(per40to49);
        bdp4 = bdp4.setScale(2,4);//保留两位小数，四舍五入
        per40to49 = bdp4.floatValue();
        int people50to59 = (int)scoreSection.score50to59(sum)[0];
        float per50to59 = scoreSection.score50to59(sum)[1];
        BigDecimal bdp5 = new BigDecimal(per50to59);
        bdp5 = bdp5.setScale(2,4);//保留两位小数，四舍五入
        per50to59 = bdp5.floatValue();
        int people60to69 = (int)scoreSection.score60to69(sum)[0];
        float per60to69 = scoreSection.score60to69(sum)[1];
        BigDecimal bdp6 = new BigDecimal(per60to69);
        bdp6 = bdp6.setScale(2,4);//保留两位小数，四舍五入
        per60to69 = bdp6.floatValue();
        int people70to79 = (int)scoreSection.score70to79(sum)[0];
        float per70to79 = scoreSection.score70to79(sum)[1];
        BigDecimal bdp7 = new BigDecimal(per70to79);
        bdp7 = bdp7.setScale(2,4);//保留两位小数，四舍五入
        per70to79 = bdp7.floatValue();
        int people80to89 = (int)scoreSection.score80to89(sum)[0];
        float per80to89 = scoreSection.score80to89(sum)[1];
        BigDecimal bdp8 = new BigDecimal(per80to89);
        bdp8 = bdp8.setScale(2,4);//保留两位小数，四舍五入
        per80to89 = bdp8.floatValue();
        int people90 = (int)scoreSection.score90(sum)[0];
        float per90 = scoreSection.score90(sum)[1];
        BigDecimal bdp9 = new BigDecimal(per90);
        bdp9 = bdp9.setScale(2,4);//保留两位小数，四舍五入
        per90 = bdp9.floatValue();
        paper.setPeople0to9(people0to9);
        paper.setPer0to9(per0to9);
        paper.setPeople10to19(people10to19);
        paper.setPer10to19(per10to19);
        paper.setPeople20to29(people20to29);
        paper.setPer20to29(per20to29);
        paper.setPeople30to39(people30to39);
        paper.setPer30to39(per30to39);
        paper.setPeople40to49(people40to49);
        paper.setPer40to49(per40to49);
        paper.setPeople50to59(people50to59);
        paper.setPer50to59(per50to59);
        paper.setPeople60to69(people60to69);
        paper.setPer60to69(per60to69);
        paper.setPeople70to79(people70to79);
        paper.setPer70to79(per70to79);
        paper.setPeople80to89(people80to89);
        paper.setPer80to89(per80to89);
        paper.setPeople90(people90);
        paper.setPer90(per90);
        session.setAttribute("jisuanPaper",paper);
        return b;
    }
}
