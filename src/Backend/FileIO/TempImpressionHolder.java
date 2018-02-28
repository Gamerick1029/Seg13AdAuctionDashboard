package Backend.FileIO;

import Backend.Model.Interfaces.Gender;

import java.util.Date;

public class TempImpressionHolder {

        Date date;
        String id;
        String ageRange;
        Gender gender;
        String income;
        String context;
        float impressionCost;

        public TempImpressionHolder(){}

        public TempImpressionHolder(Date date, String id, String ageRange, Gender gender, String income, String context, float impressionCost){
            this.date = date;
            this.id = id;
            this.ageRange = ageRange;
            this.gender = gender;
            this.income = income;
            this.context = context;
            this.impressionCost = impressionCost;
        }
}
