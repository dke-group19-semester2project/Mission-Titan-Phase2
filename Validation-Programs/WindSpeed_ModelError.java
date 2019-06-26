public class WindSpeed_ModelError{
    //Date starting on January 14,2005
    public static void main(String[] args){
        final double MISSION_DURATION=1;
        final double TIME_STEP=1;//In seconds
        //Wind
        WindSpeed titanWindSpeed= new WindSpeed(TIME_STEP);
        //For testing only. Initalized position.
        Vector2D position= new Vector2D(0, 0);
        Vector2D velocity= new Vector2D(0, 0);
        //For error relating to density values.
        //Measured density values
        double[][] densityValues=new double[10][2];
        densityValues[0][0]=128;
        densityValues[0][1]=0.01;
        densityValues[1][0]=105;
        densityValues[1][1]=0.02;
        densityValues[2][0]=77;
        densityValues[2][1]=0.06;
        densityValues[3][0]=61;
        densityValues[3][1]=0.18;
        densityValues[4][0]=51;
        densityValues[4][1]=0.36;
        densityValues[5][0]=43;
        densityValues[5][1]=0.60;
        densityValues[6][0]=37;
        densityValues[6][1]=0.87;
        densityValues[7][0]=33;
        densityValues[7][1]=1.20;
        densityValues[8][0]=17;
        densityValues[8][1]=2.70;
        densityValues[9][0]=0;
        densityValues[9][1]=5.3;
        
        double densityError=0;
        for(int i=0; i<densityValues.length;i++){
            position.setX(2.575E6+densityValues[i][0]*1000);
            Vector2D forces=titanWindSpeed.updateModelAndGetDrag(position, velocity);
            double density=titanWindSpeed.getAtmosphereDensity();
            densityError=densityError+Math.pow(density-densityValues[i][1],2);
        }
        System.out.println("Density: "+Math.sqrt(densityError/densityValues.length));
       
        //To Test for error in wind velocity values.

        double[][] windVelocity= new double[8][2];
        windVelocity[0][0]=5;//5km altitude
        windVelocity[0][1]=0;//0 m/s wind speed

        windVelocity[1][0]=13;//13km altitude
        windVelocity[1][1]=3;//3m/s wind speed

        windVelocity[2][0]=55;//55km altitude
        windVelocity[2][1]=30;//30m/s wind speed

        windVelocity[3][0]=30;//30km altitude
        windVelocity[3][1]=10;//10m/s wind speed

        windVelocity[4][0]=20;//20km altitude
        windVelocity[4][1]=4;//4m/s wind speed

        windVelocity[5][0]=7;//7km altitude
        windVelocity[5][1]=0;//0m/s wind speed

        windVelocity[6][0]=200;//200km altitude
        windVelocity[6][1]=200;//200 m/s wind speed

        windVelocity[7][0]=450;//450 km altitude
        windVelocity[7][1]=60;//60m/s wind speed

        double windError=0;//Error of wind
        for(int i=0; i<windVelocity.length;i++){
            position.setX(2.575E6+windVelocity[i][0]*1000);
            Vector2D forces=titanWindSpeed.updateModelAndGetDrag(position, velocity);
            Vector2D wind=titanWindSpeed.getCurrentWindVelocity();
            windError=windError+Math.pow(wind.getEuclideanLength()-windVelocity[i][1],2);
        }
        System.out.println("Wind Velocity: "+Math.sqrt(windError/windVelocity.length));
        }
    }
