package com.education.paper.util;

/**计算各分数段人数和百分比*/
public class ScoreSection {

    public float[] score0to9(float[] sum){
        float[] people0to9 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=0&&sum[i]<=9){
                people0to9[0]+=1;
            }
        }
        people0to9[1] = people0to9[0]/sum.length*100;
        return people0to9;
    }

    public float[] score10to19(float[] sum){
        float[] people10to19 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=10&&sum[i]<=19){
                people10to19[0]+=1;
            }
        }
        people10to19[1] = people10to19[0]/sum.length*100;
        return people10to19;
    }

    public float[] score20to29(float[] sum){
        float[] people20to29 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=20&&sum[i]<=29){
                people20to29[0]+=1;
            }
        }
        people20to29[1] = people20to29[0]/sum.length*100;
        return people20to29;
    }

    public float[] score30to39(float[] sum){
        float[] people30to39 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=30&&sum[i]<=39){
                people30to39[0]+=1;
            }
        }
        people30to39[1] = people30to39[0]/sum.length*100;
        return people30to39;
    }

    public float[] score40to49(float[] sum){
        float[] people40to49 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=40&&sum[i]<=49){
                people40to49[0]+=1;
            }
        }
        people40to49[1] = people40to49[0]/sum.length*100;
        return people40to49;
    }

    public float[] score50to59(float[] sum){
        float[] people50to59 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=50&&sum[i]<=59){
                people50to59[0]+=1;
            }
        }
        people50to59[1] = people50to59[0]/sum.length*100;
        return people50to59;
    }

    public float[] score60to69(float[] sum){
        float[] people60to69 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=60&&sum[i]<=69){
                people60to69[0]+=1;
            }
        }
        people60to69[1] = people60to69[0]/sum.length*100;
        return people60to69;
    }

    public float[] score70to79(float[] sum){
        float[] people70to79 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=70&&sum[i]<=79){
                people70to79[0]+=1;
            }
        }
        people70to79[1] = people70to79[0]/sum.length*100;
        return people70to79;
    }

    public float[] score80to89(float[] sum){
        float[] people80to89 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=80&&sum[i]<=89){
                people80to89[0]+=1;
            }
        }
        people80to89[1] = people80to89[0]/sum.length*100;
        return people80to89;
    }

    public float[] score90(float[] sum){
        float[] people90 = new float[]{0,0};
        for (int i = 0; i < sum.length; i++) {
            if (sum[i]>=90&&sum[i]<=100){
                people90[0]+=1;
            }
        }
        people90[1] = people90[0]/sum.length*100;
        return people90;
    }

}
