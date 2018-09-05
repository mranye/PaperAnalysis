package com.education.paper.util;

import com.education.paper.entity.Paper;
import com.education.paper.entity.QuestionScore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**计算各种试卷分析指标*/
public class Jisuan {

    /**游客 求Excel每一行和*/
    public float[] sumRow(List<QuestionScore> questionScoreList,HttpServletRequest request){
        HttpSession session = request.getSession();
        Paper paper = (Paper)session.getAttribute("currentPaper");
        //有多少个学生，即多少行
        int num = (int)session.getAttribute("numjoin");
        float[] sum = new float[num];
        //有多少道大题，即有多少列
        int question_num = (int)session.getAttribute("questionnum");

        QuestionScore questionScore = null;
        for (int i=0;i<num;i++){
            float sumRow = 0;
            questionScore = questionScoreList.get(i);
            for (int j=0;j<question_num;j++){
                sumRow += Float.parseFloat(questionScore.getScore()[j]);
            }
            sum[i] = sumRow;
        }

        return sum;
    }

    /**登录后  求Excel每一行和*/
    public float[] sumRowUser(List<QuestionScore> questionScoreList,HttpServletRequest request){
        HttpSession session = request.getSession();
        Paper paper = (Paper)session.getAttribute("currentPaper");
        //有多少个学生，即多少行
        int num = paper.getNumber_join();

        float[] sum = new float[num];
        //有多少道大题，即有多少列
        int question_num = paper.getQuestion_num();

        QuestionScore questionScore = null;
        for (int i=0;i<num;i++){
            float sumRow = 0;
            questionScore = questionScoreList.get(i);
            for (int j=0;j<question_num;j++){
                sumRow += Float.parseFloat(questionScore.getScore()[j]);
            }
            sum[i] = sumRow;
        }

        return sum;
    }

    /**计算最高分*/
    public float scoreHigh(float[] sum){
        float high = sum[0];//定义最大值为该数组的第一个数
        for (int i = 0; i < sum.length; i++) {
            if(high < sum[i]){
                high = sum[i];
            }
        }
        return high;
    }

    /**计算最低分*/
    public float scoreLowest(float[] sum){
        float low = sum[0];//定义最小值为该数组的第一个数
        for (int i = 0; i < sum.length; i++) {
            if(low > sum[i]){
                low = sum[i];
            }
        }
        return low;
    }

    /**计算及格率*/
    public float scoreRatePass(float[] sum){
        float pass = 0;
        float ratepass = 0;
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=60){
                pass+=1;
            }
        }
        ratepass = pass/sum.length*100;
        return ratepass;
    }

    /**计算平均分*/
    public float scoreaverage(float[] sum){
        float average = 0;
        float qiuhe = 0;
        for (int i = 0; i < sum.length; i++) {
            qiuhe+=sum[i];
        }
        average = qiuhe/sum.length;
        return average;
    }

    /**计算均方差*/
    public float scoreAverVariance(float[] sum, float average){
        float averVariacve = 0;//方差
        float ariance = 0;//均方差
        float cha = 0;//差
        float pingfang = 0;//平方
        float qiuhe = 0;//和

        for (int i = 0; i < sum.length; i++) {
            cha = sum[i] - average;
            pingfang = cha * cha;
            qiuhe += pingfang;
        }
        averVariacve = qiuhe/(float)sum.length;
        ariance = (float)Math.sqrt(averVariacve);

        return ariance;
    }

    /**计算试题难度分布率
     * 数值越小说明试题难度分布越合理*/
    public float rateDifficulty(float[] questionSum,String[] scoreRate,HttpServletRequest request){
        float rate_difficulty = 0;
        HttpSession session = request.getSession();
        Paper paper = (Paper)session.getAttribute("currentPaper");
        //有多少个学生，即多少行
        int num = paper.getNumber_join();

        //有多少道大题，即有多少列
        int question_num = paper.getQuestion_num();

        float[] scoreRatefloat = new float[question_num];//每道题的满分

        float[] px= new float[question_num];//每道题的难度系数
        for (int i=0;i<question_num;i++){
            scoreRatefloat[i] = Float.parseFloat(scoreRate[i]);
            float average = questionSum[i]/num;
            px[i] = average/scoreRatefloat[i];
        }

        float[] x = new float[]{0,0,0,0,0};//表示难度系数在0～0.19、0.2～0.39、0.4～0.59、0.6～0.79、0.8～1之间的试题满分之和
        for (int j=0;j<question_num;j++){
            if (px[j]<0.2&&px[j]>=0){
                x[0] += scoreRatefloat[j];
            }else if(px[j]<0.4&&px[j]>=0.2){
                x[1] += scoreRatefloat[j];
            }else if(px[j]<0.6&&px[j]>=0.4){
                x[2] += scoreRatefloat[j];
            }else if(px[j]<0.8&&px[j]>=0.6){
                x[3] += scoreRatefloat[j];
            }else if(px[j]<1&&px[j]>=0.8){
                x[4] += scoreRatefloat[j];
            }
        }
        float b1 = x[0]-10;//默认满分100，100*0.1
        float b2 = x[1]-20;
        float b3 = x[2]-30;
        float b4 = x[3]-25;
        float b5 = x[4]-15;
        float sum = Math.abs(b1)+Math.abs(b2)+Math.abs(b3)+Math.abs(b4)+Math.abs(b5);
        rate_difficulty = sum/100;

        BigDecimal bd1 = null;//小数点位数限制
        bd1 = new BigDecimal(rate_difficulty);
        bd1 = bd1.setScale(2,4);//保留0位小数，四舍五入
        rate_difficulty = bd1.floatValue();

        return  rate_difficulty;
    }

    /**计算区分度分布率
     * 数值越小说明试卷区分度分布越合理*/
    public float rateDistribution(List<QuestionScore> questionScoreList,String[] scoreRate,HttpServletRequest request){
        float rate_distribution = 0;
        HttpSession session = request.getSession();
        Paper paper = (Paper)session.getAttribute("currentPaper");
        //有多少个学生，即多少行
        int num = paper.getNumber_join();

        //有多少道大题，即有多少列
        int question_num = paper.getQuestion_num();
        float[] scoreRatefloat = new float[question_num];//每道题的满分
        float[][] score = new float[question_num][num];//Excel中所有成绩

        QuestionScore questionScore = null;
        for (int i=0;i<num;i++){
            questionScore = questionScoreList.get(i);
            for (int j=0;j<question_num;j++){
                score[j][i] += Float.parseFloat(questionScore.getScore()[j]);
            }
        }
        //给成绩排序
        for (int m=0;m<question_num;m++){
            for(int i=0;i<num;i++) {//从小到大排序
                for(int j=i+1;j<num;j++) {
                    if(score[m][i]>score[m][j]) {
                        float temp = score[m][i];
                        score[m][i] = score[m][j];
                        score[m][j] = temp;
                    }
                }
                System.out.println("score "+m+"    "+score[m][i]);
            }
        }
        BigDecimal bd1 = null;//小数点位数限制
        float highorlownum = num * 27 / 100;//高分组或低分组学生个数，取27%个
        bd1 = new BigDecimal(highorlownum);
        bd1 = bd1.setScale(0,4);//保留0位小数，四舍五入
        highorlownum = bd1.floatValue();
        System.out.println("highorlownum     "+highorlownum);

        float[] highscore = new float[question_num];//高分组平均得分
        float[] lowscore = new float[question_num];//低分组平均得分
        float sumlow = 0;
        float sumhigh = 0;
        for (int m=0;m<question_num;m++){
            highscore[m] = 0;
            lowscore[m] = 0;
            for (int i=0;i<highorlownum;i++){
                lowscore[m] += score[m][i];
            }
            sumlow = lowscore[m];
            lowscore[m] = sumlow / highorlownum;
            for (int j=(int)(num-highorlownum);j<num;j++){//此处应为num，取num个成绩中后两个
                highscore[m] += score[m][j];
            }
            sumhigh = highscore[m];
            highscore[m] = sumhigh / highorlownum;
            scoreRatefloat[m] = Float.parseFloat(scoreRate[m]);
        }
        for (int i=0;i<question_num;i++){
            System.out.println("highscore "+i+"    "+highscore[i]);
            System.out.println("lowscore "+i+"    "+lowscore[i]);
        }
        //System.out.println(sumlow+"    "+sumhigh);
        //System.out.println(lowscore[0]+"    "+highscore[0]);
        float[] dx = new float[question_num];//每种题型的区分度
        for (int i=0;i<question_num;i++){
            float cha = highscore[i] - lowscore[i];
            dx[i] = cha / scoreRatefloat[i];
            System.out.println("dx "+i+"    "+dx[i]);
        }

        /*0-3镖师区分度在0～0.19、0.2～0.29、0.3～0.39之间、大于0.4的试题满分之和
        * 4表示区分度是负数的试题满分*/
        float[] x = new float[]{0,0,0,0,0};
        for (int j=0;j<question_num;j++){
            if (dx[j]<0.2&&dx[j]>=0){
                x[0] += scoreRatefloat[j];
            }else if(dx[j]<0.3&&dx[j]>=0.2){
                x[1] += scoreRatefloat[j];
            }else if(dx[j]<0.4&&dx[j]>=0.3){
                x[2] += scoreRatefloat[j];
            }else if(dx[j]>=0.4){
                x[3] += scoreRatefloat[j];
            }else if(dx[j]<0){
                x[4] += scoreRatefloat[j];
            }
        }
        float e1 = x[0]-20;//默认满分100，100*0.2
        float e2 = x[1]-30;
        float e3 = x[2]-30;
        float e4 = x[3]-20;
        float e5 = x[4]/2;
        float sum = Math.abs(e1)+Math.abs(e2)+Math.abs(e3)+Math.abs(e4)+e5;
        rate_distribution = sum/100;

        BigDecimal bd2 = null;//小数点位数限制
        bd2 = new BigDecimal(rate_distribution);
        bd2 = bd2.setScale(2,4);//保留0位小数，四舍五入
        rate_distribution = bd2.floatValue();
        return rate_distribution;
    }

}
